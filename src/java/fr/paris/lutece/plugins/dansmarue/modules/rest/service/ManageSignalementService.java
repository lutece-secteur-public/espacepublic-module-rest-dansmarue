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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.business.entities.ObservationRejet;
import fr.paris.lutece.plugins.dansmarue.business.entities.PhotoDMR;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants;
import fr.paris.lutece.plugins.dansmarue.service.IObservationRejetService;
import fr.paris.lutece.plugins.dansmarue.service.IObservationRejetSignalementService;
import fr.paris.lutece.plugins.dansmarue.service.IPhotoService;
import fr.paris.lutece.plugins.dansmarue.service.ISignalementService;
import fr.paris.lutece.plugins.dansmarue.service.IWorkflowService;
import fr.paris.lutece.plugins.dansmarue.util.constants.DateConstants;
import fr.paris.lutece.plugins.dansmarue.util.constants.SignalementConstants;
import fr.paris.lutece.plugins.dansmarue.utils.ImgUtils;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskService;
import fr.paris.lutece.portal.service.fileupload.FileUploadService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.image.ImageResource;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.image.ImageUtil;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;
import net.sf.json.JSONObject;

/**
 * The Class ManageSignalementService.
 */
public class ManageSignalementService
{

    /** The Constant ACTION_SERVICE_FAIT. */
    public static final String ACTION_SERVICE_FAIT = "serviceFait";

    /** The Constant ACTION_REJETER. */
    public static final String ACTION_REJETER = "rejeter";

    /** The Constant ACTION_PROGRAMMER. */
    public static final String ACTION_PROGRAMMER = "programmer";

    /** The Constant ACTION_A_REQUALIFIER. */
    public static final String ACTION_A_REQUALIFIER = "a requalifier";

    /** The Constant ACTION_REQUALIFIER. */
    public static final String ACTION_REQUALIFIER = "requalifier";

    /** The Constant ACTION_VALIDER. */
    public static final String ACTION_VALIDER = "Valider";

    /** The Constant PARAMETER_WEBSERVICE_COMMENT_VALUE. */
    private static final String PARAMETER_WEBSERVICE_COMMENT_VALUE = "webservice_comment_value";

    /** The Constant PARAMETER_WEBSERVICE_SERVICE_FAIT_PRESTA. */
    private static final String PARAMETER_WEBSERVICE_SERVICE_FAIT_PRESTA = "webservice_service_fait_presta";

    /** The Constant PARAMETER_WEBSERVICE_DATE_PREVUE_TRAITEMENT. */
    private static final String PARAMETER_WEBSERVICE_DATE_PREVUE_TRAITEMENT = "DatePrevueTraitement";

    /** The Constant PARAMETER_WEBSERVICE_ID_TYPE_ANOMALIE. */
    private static final String PARAMETER_WEBSERVICE_ID_TYPE_ANOMALIE = "idTypeAnomalie";

    /** The Constant PARAMETER_WEBSERVICE_EMAIL_ACTEUR. */
    private static final String PARAMETER_WEBSERVICE_EMAIL_ACTEUR = "emailActeur";

    /** The Constant actionStatus. */
    private static final Map<String, String> actionStatus = new HashMap<>( );
    static
    {
        actionStatus.put( ACTION_REJETER, SignalementRestConstants.JSON_TAG_ANOMALY_REJECTED );
        actionStatus.put( ACTION_PROGRAMMER, SignalementRestConstants.JSON_TAG_ANOMALY_PROGRAMMED );
        actionStatus.put( ACTION_A_REQUALIFIER, SignalementRestConstants.JSON_TAG_ANOMALY_A_REQUALIFIED );
        actionStatus.put( ACTION_REQUALIFIER, SignalementRestConstants.JSON_TAG_ANOMALY_REQUALIFIED );
        actionStatus.put( ACTION_SERVICE_FAIT, SignalementRestConstants.JSON_TAG_ANOMALY_DONE );
    }

    /** The Constant JSON_TAG_ANOMALIE. */
    public static final String JSON_TAG_ANOMALIE = "anomalie";

    /** The Constant JSON_TAG_REQUEST. */
    public static final String JSON_TAG_REQUEST = "request";

    /** The signalement workflow service. */
    @Inject
    private IWorkflowService _signalementWorkflowService;

    /** The observation rejet service. */
    @Inject
    private IObservationRejetService _observationRejetService;

    /** The photo service. */
    @Inject
    private IPhotoService _photoService;

    /** The task service. */
    @Inject
    private ITaskService _taskService;

    /** The authenticator. */
    @Inject
    @Named( "rest.requestAuthenticator" )
    private RequestAuthenticator _authenticator;

    /** The observation rejet signalement service. */
    // SERVICES
    @Inject
    private IObservationRejetSignalementService _observationRejetSignalementService;

    /** The signalement service. */
    @Inject
    private ISignalementService _signalementService;

    /**
     * process the change status.
     *
     * @param request
     *            the http request.
     * @param action
     *            the action.
     * @param signalement
     *            the report.
     * @param commentaire
     *            the comment.
     * @param motifsRejetIds
     *            reject reason.
     * @param motifRejetAutre
     *            reject reason other.
     * @param strDateProgrammation
     *            strDateProgrammation
     * @param idTypeAnomalie
     *            id type report.
     * @param imageFile
     *            the image file.
     * @return empty if no errors, error message otherwise
     */
    public String processAction( HttpServletRequest request, String action, Signalement signalement, String commentaire, String [ ] motifsRejetIds,
            String motifRejetAutre, String strDateProgrammation, long idTypeAnomalie, FileItem imageFile )
    {
        String error = StringUtils.EMPTY;

        if ( actionStatus.containsKey( action ) )
        {
            JSONObject jsonAnswer = new JSONObject( );
            manageStatusWithWorkflow( request, new JSONObject( ), jsonAnswer, signalement.getId( ).intValue( ), signalement.getSignalementReference( ),
                    actionStatus.get( action ), motifsRejetIds, motifRejetAutre, strDateProgrammation, idTypeAnomalie, commentaire, null );
            if ( jsonAnswer.containsKey( SignalementRestConstants.JSON_TAG_ERROR_ERROR ) )
            {
                error = jsonAnswer.getString( SignalementRestConstants.JSON_TAG_ERROR_ERROR );
            }
            else
            {
                String strImageName = FileUploadService.getFileNameOnly( imageFile );
                if ( StringUtils.isNotBlank( strImageName ) )
                {
                    ImageResource image = new ImageResource( );
                    String width = AppPropertiesService.getProperty( SignalementConstants.IMAGE_THUMBNAIL_RESIZE_WIDTH );
                    String height = AppPropertiesService.getProperty( SignalementConstants.IMAGE_THUMBNAIL_RESIZE_HEIGHT );
                    byte [ ] resizeImage = ImageUtil.resizeImage( imageFile.get( ), width, height, 1 );
                    PhotoDMR photoSignalement = new PhotoDMR( );
                    image.setImage( imageFile.get( ) );
                    String mimeType = imageFile.getContentType( ).replace( "pjpeg", "jpeg" );
                    mimeType = mimeType.replace( "x-png", "png" );
                    image.setMimeType( mimeType );
                    photoSignalement.setImage( image );
                    photoSignalement.setImageContent( ImgUtils.checkQuality( image.getImage( ) ) );
                    photoSignalement.setImageThumbnailWithBytes( resizeImage );
                    photoSignalement.setSignalement( signalement );
                    photoSignalement.setVue( 2 );
                    photoSignalement.setDate( new SimpleDateFormat( DateConstants.DATE_FR ).format( Calendar.getInstance( ).getTime( ) ) );

                    // creation of the image in the db linked to the signalement
                    _photoService.insert( photoSignalement );
                }
            }
        }
        else
        {
            AppLogService.error( "Error with action : " + action );
        }
        return error;
    }

    /**
     * Manage status with workflow.
     *
     * @param request
     *            the http request
     * @param jsonObject
     *            the json object
     * @param jsonAnswer
     *            the json answer
     * @param id
     *            the signalment id
     * @param reference
     *            the reference
     * @param status
     *            the signalement status
     * @param motifsRejetIds
     *            the reject cause ids
     * @param motifRejetAutre
     *            the reject cause autre
     * @param strDateProgramme
     *            the str date programme
     * @param idTypeAnomalie
     *            the id type anomalie
     * @param comment
     *            the comment
     * @param emailActeur
     *            email de l'agent ayant effectue l'action
     */
    public void manageStatusWithWorkflow( HttpServletRequest request, JSONObject jsonObject, JSONObject jsonAnswer, int id, String reference, String status,
            String [ ] motifsRejetIds, String motifRejetAutre, String strDateProgramme, long idTypeAnomalie, String comment, String emailActeur )
    {
        // récupération de l'identifiant du workflow
        Integer workflowId = _signalementWorkflowService.getSignalementWorkflowId( );

        WorkflowService workflowService = WorkflowService.getInstance( );

        int stateid = workflowService.getState( id, Signalement.WORKFLOW_RESOURCE_TYPE, workflowId, null ).getId( );

        if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_DONE ) || status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_REJECTED )
                || status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_PROGRAMMED )
                || status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_A_REQUALIFIED )
                || status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_REQUALIFIED ) )
        {

            jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ID, reference );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );

            request.getSession( ).setAttribute( PARAMETER_WEBSERVICE_COMMENT_VALUE, comment );

            // Status = 'service fait'
            if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_DONE ) )
            {
                // Check if signalement's state is "Transféré à un prestataire"
                if ( stateid == SignalementRestConstants.TRANFERED_PROVIDER_STATUS_ID )
                {

                    request.getSession( ).setAttribute( PARAMETER_WEBSERVICE_SERVICE_FAIT_PRESTA, true );

                    workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                            SignalementRestConstants.ACTION_TRANSFERED_STATE_WEBSERVICE_ACCEPTED, null, request, request.getLocale( ), true );
                }
                else
                {
                    // Check if signalement's state is "Service programmé par un prestataire"
                    if ( stateid == SignalementRestConstants.PROGRAMMED_STATUS_ID )
                    {

                        request.getSession( ).setAttribute( PARAMETER_WEBSERVICE_SERVICE_FAIT_PRESTA, true );

                        workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                SignalementRestConstants.ACTION_PROGRAMMED_STATE_WEBSERVICE_ACCEPTED, null, request, request.getLocale( ), true );
                    }
                    else
                        if ( stateid != SignalementRestConstants.SERVICE_FAIT_STATUS_ID )
                        {
                            jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR,
                                    I18nService.getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_DONE_WRONG_STATUS, request.getLocale( ) ) );
                            jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
                            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
                        }
                }

                String nomPrestataire = _signalementService.findPrestataireSignalement( id );
                if ( StringUtils.isNotBlank( nomPrestataire ) )
                {
                    Integer nIdWorkflow = _signalementWorkflowService.getSignalementWorkflowId( );
                    ResourceHistory resourceHistory = _signalementWorkflowService.getLastHistoryResource( id, Signalement.WORKFLOW_RESOURCE_TYPE, nIdWorkflow );
                    _signalementWorkflowService.setUserAccessCodeHistoryResource( nomPrestataire, resourceHistory.getId( ) );
                }
            }
            // Status = 'rejet'
            else
                if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_REJECTED ) )
                {
                    // Check if signalement's state is "Transféré à un prestataire"
                    if ( stateid == SignalementRestConstants.TRANFERED_PROVIDER_STATUS_ID )
                    {
                        request.getSession( ).setAttribute( SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET, motifRejetAutre );
                        workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                SignalementRestConstants.ACTION_TRANSFERED_STATE_WEBSERVICE_REJECTED, null, request, request.getLocale( ), true );
                        request.getSession( ).removeAttribute( SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET );
                    }
                    else
                    {
                        // Check if signalement's state is "Service programmé par un prestataire"
                        if ( stateid == SignalementRestConstants.PROGRAMMED_STATUS_ID )
                        {
                            request.getSession( ).setAttribute( SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET, motifRejetAutre );
                            workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                    SignalementRestConstants.ACTION_PROGRAMMED_STATE_WEBSERVICE_REJECTED, null, request, request.getLocale( ), true );
                            request.getSession( ).removeAttribute( SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET );
                        }
                        else
                        {
                            jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR,
                                    I18nService.getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_REJECTED_WRONG_STATUS, request.getLocale( ) ) );
                            jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
                            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
                        }
                    }
                }
            // Status = 'service programme'
                else
                    if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_PROGRAMMED ) )
                    {

                        request.getSession( ).setAttribute( PARAMETER_WEBSERVICE_DATE_PREVUE_TRAITEMENT, strDateProgramme );
                        // Check if signalement's state is "Transféré à un prestataire"
                        if ( stateid == SignalementRestConstants.TRANFERED_PROVIDER_STATUS_ID )
                        {

                            workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                    SignalementRestConstants.ACTION_TRANSFERED_STATE_WEBSERVICE_PROGRAMMED, null, request, request.getLocale( ), true );
                        }
                        else
                        {
                            // Check if signalement's state is "Service programmé par un prestataire"
                            if ( stateid == SignalementRestConstants.PROGRAMMED_STATUS_ID )
                            {

                                workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                        SignalementRestConstants.ACTION_PROGRAMMED_STATE_WEBSERVICE_PROGRAMMED, null, request, request.getLocale( ), true );
                            }
                            else
                            {
                                jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR, I18nService
                                        .getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_PROGRAMMED_WRONG_STATUS, request.getLocale( ) ) );
                                jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
                                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
                            }
                        }

                    }
            // Status = 'requalification'
                    else
                        if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_REQUALIFIED ) )
                        {
                            request.getSession( ).setAttribute( PARAMETER_WEBSERVICE_ID_TYPE_ANOMALIE, idTypeAnomalie );
                            request.getSession( ).setAttribute(PARAMETER_WEBSERVICE_EMAIL_ACTEUR, emailActeur);

                            Signalement signalement = _signalementService.getSignalement( id );

                            // Select idAction Workflow depends on state report
                            int idActionRequalifier = -1;
                            if ( stateid == SignalementRestConstants.TRANFERED_PROVIDER_STATUS_ID )
                            {
                                idActionRequalifier = SignalementRestConstants.ACTION_TRANSFERED_STATE_WEBSERVICE_REQUALIFIED;
                            }
                            else
                                if ( stateid == SignalementRestConstants.PROGRAMMED_STATUS_ID )
                                {
                                    idActionRequalifier = SignalementRestConstants.ACTION_PROGRAMMED_STATE_WEBSERVICE_REQUALIFIED;
                                }
                                else
                                    if ( stateid == SignalementConstants.ID_STATE_A_TRAITER )
                                    {
                                        idActionRequalifier = SignalementRestConstants.ACTION_TODO_STATE_WEBSERVICE_A_REQUALIFIED;
                                    }
                                    else
                                        if ( stateid == SignalementConstants.ID_STATE_NOUVEAU )
                                        {
                                            idActionRequalifier = SignalementRestConstants.ACTION_NEW_STATE_WEBSERVICE_A_REQUALIFIED;
                                        }
                                        else
                                            if ( stateid == SignalementConstants.ID_STATE_PROGRAMME )
                                            {
                                                idActionRequalifier = SignalementRestConstants.ACTION_PROGRAMME_STATE_WEBSERVICE_A_REQUALIFIED;
                                            }
                                            else
                                                if ( stateid == SignalementConstants.ID_STATE_A_REQUALIFIER )
                                                {
                                                    idActionRequalifier = SignalementRestConstants.ACTION_TO_REQUALIFIE_STATE_WEBSERVICE_A_REQUALIFIED;
                                                }

                            if ( idActionRequalifier != -1 )
                            {
                                // call workflow
                                workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE, idActionRequalifier, null, request,
                                        request.getLocale( ), true );
                            }
                            else
                            {
                                jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR, I18nService
                                        .getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_REQUALIFIED_WRONG_STATUS, request.getLocale( ) ) );
                                jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
                                jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
                            }


                        }
            // Status = a rqualifier
                        else
                            if ( status.equals( SignalementRestConstants.JSON_TAG_ANOMALY_A_REQUALIFIED ) )
                            {

                                if ( stateid == SignalementRestConstants.TRANFERED_PROVIDER_STATUS_ID )
                                {

                                    workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                            SignalementRestConstants.ACTION_TRANSFERED_STATE_WEBSERVICE_A_REQUALIFIED, null, request, request.getLocale( ),
                                            true );
                                }
                                else
                                {
                                    // Check if signalement's state is "Service programmé par un prestataire"
                                    if ( stateid == SignalementRestConstants.PROGRAMMED_STATUS_ID )
                                    {

                                        workflowService.doProcessAction( id, Signalement.WORKFLOW_RESOURCE_TYPE,
                                                SignalementRestConstants.ACTION_PROGRAMMED_STATE_WEBSERVICE_A_REQUALIFIED, null, request, request.getLocale( ),
                                                true );
                                    }
                                    else
                                    {
                                        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR, I18nService
                                                .getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_REQUALIFIED_WRONG_STATUS, request.getLocale( ) ) );
                                        jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
                                        jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
                                    }
                                }
                            }
        }
        else
        {
            jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_ERROR_ERROR,
                    I18nService.getLocalizedString( SignalementRestConstants.ERROR_MESSAGE_DIFFERENT_STATUS, request.getLocale( ) ) );
            jsonObject.remove( SignalementRestConstants.JSON_TAG_ANSWER );
            jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
        }
    }

    /**
     * Check if the id of the reject cause id exists.
     *
     * @param rejectcauseid
     *            the reject cause id given by WS
     * @return true if it exists, false if it doesn't
     */
    public boolean checkRejectCauseIdExist( int rejectcauseid )
    {
        boolean result = false;
        // The list of all the reject causes
        List<ObservationRejet> listeNatureObjet = _observationRejetService.getAllObservationRejet( );

        for ( ObservationRejet rejet : listeNatureObjet )
        {
            if ( !result && rejet.getActif( ) && ( rejet.getId( ) == rejectcauseid ) )
            {
                result = true;
            }
        }
        return result;
    }

}
