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
package fr.paris.lutece.plugins.dansmarue.modules.rest.service.formatters;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.business.entities.Adresse;
import fr.paris.lutece.plugins.dansmarue.business.entities.PhotoDMR;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signalement;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signaleur;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.date.DateUtils;
import fr.paris.lutece.plugins.dansmarue.service.IPhotoService;
import fr.paris.lutece.plugins.dansmarue.service.ISignalementSuiviService;
import fr.paris.lutece.plugins.dansmarue.service.ITypeSignalementService;
import fr.paris.lutece.plugins.dansmarue.service.IWorkflowService;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.plugins.rest.util.json.JSONUtil;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * SignalementFormatterJson.
 */
public class SignalementFormatterJson implements IFormatter<Signalement>
{

    /** The Constant PHOTO_SERVICE_BEAN_NAME. */
    public static final String PHOTO_SERVICE_BEAN_NAME = "photoService";

    /**
     * guid transmit par les applications mobiles Permet d'identifier l'utilisateur de l'application.
     */
    private String _formatWithGuid = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public String format( Signalement signalement )
    {
        JSONObject jsonObject = formatSignalement( signalement );

        if ( ( _formatWithGuid != null ) && ( signalement != null ) )
        {
            ISignalementSuiviService signalementSuiviService = SpringContextService.getBean( "signalementSuiviService" );

            boolean isFallowByUser = signalementSuiviService.findByIdSignalementAndGuid( signalement.getId( ), _formatWithGuid ) != -1;
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_IS_INCIDENT_FOLLOWED_BY_USER, isFallowByUser );
        }

        return jsonObject.toString( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format( List<Signalement> listSignalement )
    {
        JSONArray jsonArray = new JSONArray( );

        for ( Signalement signalement : listSignalement )
        {
            jsonArray.element( format( signalement ) );
        }

        // ajouter les dossiers
        return jsonArray.toString( );
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

    /**
     * Transform a signalement object to a signalement JSONObject.
     *
     * @param signalement
     *            The signalement to format to JSON object
     * @return The signalement as JSON Object for mobile devices
     */
    public static JSONObject formatSignalement( Signalement signalement )
    {
        IPhotoService photoService = SpringContextService.getBean( PHOTO_SERVICE_BEAN_NAME );
        IWorkflowService signalementWorkflowService = SpringContextService.getBean( "signalement.workflowService" );
        ITypeSignalementService typeSignalementService = SpringContextService.getBean( "typeSignalementService" );
        ISignalementSuiviService signalementSuiviService = SpringContextService.getBean( "signalementSuiviService" );

        JSONObject jsonObject = new JSONObject( );

        if ( signalement != null )
        {
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ID, signalement.getId( ) );

            int idCategory = typeSignalementService.getCategoryFromTypeId( signalement.getTypeSignalement( ).getId( ) );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_CATEGORIE_ID, idCategory );

            int nbFollowers = signalementSuiviService.getNbFollowersByIdSignalement( signalement.getId( ).intValue( ) );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_FOLLOWERS, nbFollowers );

            if ( CollectionUtils.isNotEmpty( signalement.getSignaleurs( ) ) )
            {
                Signaleur signaleur = signalement.getSignaleurs( ).get( 0 );
                if ( StringUtils.isNotBlank( signaleur.getGuid( ) ) )
                {
                    jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_REPORTER_GUID, signaleur.getGuid( ) );
                }

                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_IS_INCIDENT_ANONYME, StringUtils.isBlank( signaleur.getMail( ) )
                        && signalementSuiviService.findUsersMailByIdSignalement( signalement.getId( ) ).isEmpty( ) );
            }

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_CONGRATULATIONS, signalement.getFelicitations( ) );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ALIAS, signalement.getTypeSignalement( ).getAliasMobileDefault( ) );
            String strStatus = "O";

            // set the state of the signalement with the workflow
            WorkflowService workflowService = WorkflowService.getInstance( );

            if ( workflowService.isAvailable( ) )
            {
                // récupération de l'identifiant du workflow
                Integer workflowId = signalementWorkflowService.getSignalementWorkflowId( );

                if ( workflowId != null )
                {
                    State state = workflowService.getState( signalement.getId( ).intValue( ), Signalement.WORKFLOW_RESOURCE_TYPE, workflowId, null );

                    int nStateId = state.getId( );

                    // strStatus = O (Ouvert)
                    // strStatus = R (Résolu)
                    // strStatus = U (Update traitement différé)
                    // strStatus = ONR (Ouver mais le statut ne permet pas la résolution)
                    // strStatus = T (Anomalie chez un tiers avec Webservice)
                    if ( ( nStateId == 7 ) || ( nStateId == 13 ) || ( ( nStateId == 18 ) && !signalement.getIsSendWS( ) ) )
                    {
                        strStatus = "O";
                    }
                    else
                        if ( ( ( nStateId == 8 ) || ( nStateId == 9 ) ) || ( ( nStateId == 21 ) && !signalement.getIsSendWS( ) ) )
                        {
                            strStatus = "U";
                        }
                        else
                            if ( ( nStateId == 10 ) || ( nStateId == 11 ) || ( nStateId == 12 ) || ( nStateId == 22 ) )
                            {
                                strStatus = "R";
                            }
                            else
                                if ( ( nStateId == 15 ) || ( nStateId == 20 ) || ( nStateId == 12 ) )
                                {
                                    strStatus = "ONR";
                                }
                                else
                                    if ( ( ( nStateId == 18 ) || ( nStateId == 21 ) ) && signalement.getIsSendWS( ) )
                                    {
                                        strStatus = "T";
                                    }
                }
            }

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_STATE, strStatus );
            for ( Adresse adresse : signalement.getAdresses( ) )
            {
                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ADDRESS, adresse.getAdresse( ) );
                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_LAT, adresse.getLat( ) );
                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_LNG, adresse.getLng( ) );
            }

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_DESCRIPTIVE, signalement.getCommentaire( ) );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_COMMENTAIRE_AGENT, signalement.getCommentaireAgentTerrain( ) );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_DATE, DateUtils.convertDate( signalement.getDateCreation( ) ) );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_HOUR, DateUtils.convertHour( signalement.getHeureCreation( ) ) );

            JSONObject jsonObjectPictures = new JSONObject( );

            List<PhotoDMR> listPhotos = photoService.findBySignalementId( signalement.getId( ) );
            JSONArray jsonPictureFar = new JSONArray( );
            JSONArray jsonPictureClose = new JSONArray( );
            JSONArray jsonPictureDone = new JSONArray( );

            for ( PhotoDMR photo : listPhotos )
            {
                if ( photo.getVue( ).equals( SignalementRestConstants.VUE_PRES ) )
                {
                    jsonPictureClose.element( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                }
                else
                    if ( photo.getVue( ).equals( SignalementRestConstants.VUE_ENSEMBLE ) )
                    {
                        jsonPictureFar.element( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                    }
                    else
                        if ( photo.getVue( ).equals( SignalementRestConstants.VUE_SERVICE_FAIT ) )
                        {
                            jsonPictureDone.element( AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                        }
            }

            jsonObjectPictures.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_CLOSE, jsonPictureClose );
            jsonObjectPictures.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_FAR, jsonPictureFar );
            jsonObjectPictures.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_DONE, jsonPictureDone );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_PICTURES, jsonObjectPictures );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_CONFIRMS, signalement.getSuivi( ) );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_PRIORITE_ID, signalement.getPriorite( ).getId( ) );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_INVALIDATIONS, 0 );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_SOURCE,
                    AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_INCIDENT_SOURCE_DMR ) );

            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_NUEMERO, signalement.getNumeroSignalement( ) );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_TOKEN, signalement.getToken( ) );

        }

        return jsonObject;
    }

    /**
     * Sets the format with guid.
     *
     * @param formatWithGuid
     *            the new format with guid
     */
    public void setFormatWithGuid( String formatWithGuid )
    {
        _formatWithGuid = formatWithGuid;
    }

}
