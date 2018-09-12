/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dansmarue.modules.rest.service.formatters;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.business.entities.TypeSignalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants;
import fr.paris.lutece.plugins.dansmarue.service.ITypeSignalementService;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.plugins.rest.util.json.JSONUtil;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *
 *CategoriesFormatterJson
 *
 */
public class CategoriesFormatterJson implements IFormatter<TypeSignalement>
{
    // SERVICES
    private ITypeSignalementService _typeSignalementService = SpringContextService.getBean(
            "typeSignalementService" );

    /**
     * {@inheritDoc}
     */
    @Override
    public String format( TypeSignalement typeSignalement )
    {
        JSONObject jsonObject = new JSONObject(  );

        if ( typeSignalement != null )
        {
            if ( typeSignalement.getTypeSignalementParent(  ) != null )
            {
                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_CATEGORIES_PARENT_ID,
                        typeSignalement.getTypeSignalementParent(  ).getId(  ) );
            }

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_CATEGORIES_NAME, typeSignalement.getLibelle(  ) );
            if(StringUtils.isNotBlank(typeSignalement.getAlias())){
                jsonObject.accumulate(SignalementRestConstants.JSON_TAG_CATEGORIES_ALIAS, typeSignalement.getAliasMobileDefault());
            }
            List<TypeSignalement> listeTypeSignalement = _typeSignalementService.getAllSousTypeSignalement( typeSignalement.getId(  ) );

            if ( ( listeTypeSignalement != null ) && !listeTypeSignalement.isEmpty(  ) )
            {
                JSONArray jsonArray = new JSONArray(  );

                for ( TypeSignalement ts : listeTypeSignalement )
                {
                    jsonArray.element( ts.getId(  ) );
                }

                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_CATEGORIES_CHILDREN_ID, jsonArray );
            }
        }

        return jsonObject.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format( List<TypeSignalement> listTypeSignalement )
    {
        JSONObject jsonObject = new JSONObject(  );

        List<TypeSignalement> listeTypeSignalement = _typeSignalementService.getAllTypeSignalementWithoutParent(  );
        JSONArray jsonArray = new JSONArray(  );

        for ( TypeSignalement ts : listeTypeSignalement )
        {
            if(ts.getActif())
            {
                jsonArray.element( ts.getId(  ) );
            }
        }

        JSONObject jsonObjectWithoutParent = new JSONObject(  );
        jsonObjectWithoutParent.accumulate( SignalementRestConstants.JSON_TAG_CATEGORIES_CHILDREN_ID, jsonArray );
        jsonObject.accumulate( "0", jsonObjectWithoutParent );

        for ( TypeSignalement typeSignalement : listTypeSignalement )
        {
            if(typeSignalement.getActif())
            {
                jsonObject.accumulate( String.valueOf( typeSignalement.getId(  ) ), format( typeSignalement ) );
            }
        }

        return jsonObject.toString( 4 );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String formatError( String strCode, String strMessage )
    {
        if ( StringUtils.isNumeric( strCode ) )
        {
            return JSONUtil.formatError( strMessage, Integer.parseInt( strCode ) );
        }
        else
        {
            return JSONUtil.formatError( strMessage, -1 );
        }
    }
}
