/*
 * Copyright (c) 2002-2022, City of Paris
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
package fr.paris.lutece.plugins.dansmarue.modules.rest.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import net.sf.json.JSONObject;

/**
 * The Class RamenClientService.
 */
public class RamenClientService
{

    /**
     * Gets the dossiers courrants by geom.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @return the dossiers courrants by geom
     */
    public String getDossiersCourrantsByGeom( Double lng, Double lat )
    {
        StringBuilder strUrl = new StringBuilder( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_RAMEN_REST ) );
        strUrl.append( SignalementRestConstants.QUESTION_MARK );

        strUrl.append( SignalementRestConstants.PARAMETER_LATITUDE );
        strUrl.append( SignalementRestConstants.EQUAL );
        strUrl.append( lat );

        strUrl.append( SignalementRestConstants.AMPERSAND );

        strUrl.append( SignalementRestConstants.PARAMETER_LONGITUDE );
        strUrl.append( SignalementRestConstants.EQUAL );
        strUrl.append( lng );

        strUrl.append( SignalementRestConstants.AMPERSAND );

        strUrl.append( SignalementRestConstants.PARAMETER_RADIUS );
        strUrl.append( SignalementRestConstants.EQUAL );
        strUrl.append( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_RADIUS ) );

        String strReponseXml = StringUtils.EMPTY;
        HttpAccess httpAccess = new HttpAccess( );

        try
        {
            strReponseXml = httpAccess.doGet( strUrl.toString( ) );
        }
        catch( HttpAccessException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }

        return strReponseXml;
    }

    /**
     * Gets the dossiers courrants by geomm with limit.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @return the dossiers courrants by geomm with limit
     */
    public String getDossiersCourrantsByGeommWithLimit( Double lng, Double lat )
    {

        String strReponse = null;

        StringBuilder strUrl = new StringBuilder( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_RAMEN_REST_GEO_WITH_LIMIT ) );

        HttpAccess httpAccess = new HttpAccess( );

        JSONObject jsonObj = new JSONObject( );
        jsonObj.accumulate( SignalementRestConstants.PARAMETER_RADIUS,
                Integer.valueOf( ( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_RADIUS ) ) ) );
        jsonObj.accumulate( SignalementRestConstants.PARAMETER_LONGITUDE, lng );
        jsonObj.accumulate( SignalementRestConstants.PARAMETER_LATITUDE, lat );

        Map<String, String> headersRequest = new HashMap<>( );
        Map<String, String> headersResponse = new HashMap<>( );

        try
        {

            strReponse = httpAccess.doPostJSON( strUrl.toString( ), jsonObj.toString( ), headersRequest, headersResponse );

        }
        catch( HttpAccessException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }

        return strReponse;
    }
}
