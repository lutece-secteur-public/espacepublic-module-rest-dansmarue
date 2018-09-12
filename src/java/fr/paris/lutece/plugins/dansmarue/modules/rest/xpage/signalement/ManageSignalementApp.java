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
package fr.paris.lutece.plugins.dansmarue.modules.rest.xpage.signalement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.business.entities.NotificationSignalementUserMultiContents;
import fr.paris.lutece.plugins.dansmarue.business.entities.ObservationRejet;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signalement;
import fr.paris.lutece.plugins.dansmarue.business.entities.TypeSignalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.service.ManageSignalementService;
import fr.paris.lutece.plugins.dansmarue.modules.rest.service.upload.handler.DansMaRueUploadHandler;
import fr.paris.lutece.plugins.dansmarue.service.IObservationRejetService;
import fr.paris.lutece.plugins.dansmarue.service.ISignalementService;
import fr.paris.lutece.plugins.dansmarue.service.ITypeSignalementService;
import fr.paris.lutece.plugins.dansmarue.service.IWorkflowService;
import fr.paris.lutece.plugins.dansmarue.utils.ListUtils;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

/**
 * the XPage app to manage signalement without Web Service
 */
public class ManageSignalementApp implements XPageApplication
{

    // PROPERTIES
    private static final String      PROPERTY_BASE_URL              = "lutece.prod.url";

    // JSP
    private static final String      JSP_PORTAL                     = "jsp/site/Portal.jsp";

    private static final String      TEMPLATE_ACTIONS               = "skin/plugins/signalement/modules/rest/managewithoutws/manage_actions.html";

    private static final String      MARK_LOCALE                    = "locale";
    private static final String      MARK_MOTIFS                    = "motifs";
    private static final String      MARK_ID                        = "id";
    private static final String      MARK_ACTION                    = "action";
    private static final String      MARK_TYPE_LIST                 = "type_list";
    private static final String      MARK_TYPE                      = "type";
    private static final String      MARK_ERROR                     = "error";

    private static final String      PARAMETER_PAGE                 = "page";
    private static final String      PARAMETER_SUIVI                = "suivi";
    private static final String      PARAMETER_TOKEN                = "token";
    private static final String      PARAMETER_MOTIF_REJET          = "motif_rejet";
    private static final String      PARAMETER_MOTIF_AUTRE_CHECKBOX = "motif_autre_checkbox";
    private static final String      PARAMETER_MOTIF_AUTRE          = "motif_autre";
    private static final String      PARAMETER_ID_TYPE_ANOMALIE     = "typeSignalementSelect";
    private static final String      PARAMETER_PHOTO_DONE           = "photoDone";
    private static final String      PARAMETER_CHOSEN_MESSAGE       = "chosenMessage";

    private static final String      I18N_ERROR_MOTIF_REJET         = "module.dansmarue.rest.manage.signalement.error.motif.rejet";
    private static final String      I18N_ERROR_PROGRAMMATION_DATE  = "module.dansmarue.rest.manage.signalement.error.programmation.date";
    private static final String      I18N_ERROR_TYPE_ANOMALIE       = "module.dansmarue.rest.manage.signalement.error.type.anomalie";
    private static final String      I18N_ERROR_NO_SIGNALEMENT      = "module.dansmarue.rest.manage.signalement.error.no.signalement";
    private static final String      I18N_ERROR_MANDATORY_ACTION    = "module.dansmarue.rest.manage.signalement.actions.aide";

    private static final String      I18N_TITLE                     = "module.dansmarue.rest.manage.signalement.gestion.title";

    private static final String      I18N_SUCCESS                   = "module.dansmarue.rest.manage.signalement.success";

    private ISignalementService      _signalementService            = SpringContextService.getBean( "signalementService" );

    private IObservationRejetService _observationRejetService       = SpringContextService.getBean( "observationRejetService" );

    private ManageSignalementService _manageSignalementService      = SpringContextService.getBean( "signalement-rest.manageSignalementService" );

    private ITypeSignalementService  _typeSignalementService        = SpringContextService.getBean( "typeSignalementService" );

    private IWorkflowService         _signalementWorkflowService    = SpringContextService.getBean( "signalement.workflowService" );

    private DansMaRueUploadHandler   _dansmarueUploadHandler        = SpringContextService.getBean( "signalement-rest.dansmarueUploadHandler" );

    @Inject
    private ITaskService             _taskService;
    @Inject
    private IActionService           _actionService;
    @Inject
    private IStateService            _stateService;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "deprecation" )
    @Override
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
    {
        XPage page = new XPage( );
        Map<String, Object> model = new HashMap<>( );
        model.put( MARK_LOCALE, request.getLocale( ) );

        WorkflowService workflowService = WorkflowService.getInstance( );
        String action = getAction( request );
        Signalement bean = getSignalement( request );
        Integer workflowId = _signalementWorkflowService.getSignalementWorkflowId( );
        State state = workflowService.getState( bean.getId( ).intValue( ), Signalement.WORKFLOW_RESOURCE_TYPE, workflowId, null );
        model.put( "state", state );

        // if blank, only display the choice page
        if ( StringUtils.isBlank( action ) && ( bean != null ) )
        {
            addMotifs( model );
            addTypeAnomalie( model, bean );
            model.put( "signalement", bean );

        }
        // else, validate or not the action
        else if ( bean != null )
        {
            String error = validateForm( request );
            if ( StringUtils.isBlank( error ) )
            {
                // Vidage du motif autre si checkbox non cochée
                boolean motifAutreCheckbox = StringUtils.isNotBlank( request.getParameter( PARAMETER_MOTIF_AUTRE_CHECKBOX ) );
                String motifAutre = StringUtils.EMPTY;
                if ( motifAutreCheckbox )
                {
                    motifAutre = request.getParameter( PARAMETER_MOTIF_AUTRE );
                }

                String stridTypeAnomalie = request.getParameter( PARAMETER_ID_TYPE_ANOMALIE );
                long idTypeAnomalie = StringUtils.isNotBlank( stridTypeAnomalie ) ? Long.parseLong( stridTypeAnomalie ) : -1;

                FileItem imageFile = _dansmarueUploadHandler.getFile( request, PARAMETER_PHOTO_DONE );

                request.getSession( ).setAttribute( PARAMETER_CHOSEN_MESSAGE, request.getParameter( PARAMETER_CHOSEN_MESSAGE ) );

                error = _manageSignalementService.processAction( request, action, bean, request.getParameter( "commentaires" ), request.getParameterValues( PARAMETER_MOTIF_REJET ), motifAutre,
                        request.getParameter( "dateProgrammation" ), idTypeAnomalie, imageFile );
                if ( StringUtils.isNotBlank( error ) )
                {
                    model.put( MARK_ERROR, error );
                } else
                {
                    if ( _dansmarueUploadHandler.hasFile( request, PARAMETER_PHOTO_DONE ) )
                    {
                        _dansmarueUploadHandler.removeFileItem( PARAMETER_PHOTO_DONE, request.getSession( ), 0 );
                    }

                    UrlItem urlItem;

                    urlItem = new UrlItem( AppPropertiesService.getProperty( PROPERTY_BASE_URL ) + JSP_PORTAL );

                    urlItem.addParameter( PARAMETER_PAGE, PARAMETER_SUIVI );
                    urlItem.addParameter( PARAMETER_TOKEN, bean.getToken( ) );

                    String link = "<a href=\"" + urlItem.getUrl( ) + "\" >LIEN_CONSULTATION</a>";
                    model.put( "success", I18nService.getLocalizedString( I18N_SUCCESS, new String[] { link }, request.getLocale( ) ) );
                }
            } else
            {
                model.put( MARK_ERROR, error );
            }
        } else
        {
            model.put( MARK_ERROR, I18nService.getLocalizedString( I18N_ERROR_NO_SIGNALEMENT, request.getLocale( ) ) );
        }

        // Récupération des messages de service fait
        Map<Integer, List<NotificationSignalementUserMultiContents>> allMessagesServiceFait = _signalementWorkflowService.selectMessageServiceFaitPresta( action );

        if ( state.getId( ) == AppPropertiesService.getPropertyInt( "signalement.idStateTransferePrestataire", -1 ) )
        {
            model.put( "messagesServiceFait", allMessagesServiceFait.get( AppPropertiesService.getPropertyInt( "signalement.idTaskTransferePrestataireNotifMultiContents", -1 ) ) );
        } else
        {
            model.put( "messagesServiceFait", allMessagesServiceFait.get( AppPropertiesService.getPropertyInt( "signalement.idTaskServiceProgrammePrestataireNotifMultiContents", -1 ) ) );
        }

        model.put( _dansmarueUploadHandler.getHandlerName( ), _dansmarueUploadHandler );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ACTIONS, request.getLocale( ), model );

        page.setContent( template.getHtml( ) );
        page.setPathLabel( I18nService.getLocalizedString( I18N_TITLE, request.getLocale( ) ) );
        page.setTitle( I18nService.getLocalizedString( I18N_TITLE, request.getLocale( ) ) );

        return page;
    }

    /**
     * Validate the form and return the errors if exists
     *
     * @param request
     *            the http request
     *
     * @return the errors or null
     */
    private String validateForm( HttpServletRequest request )
    {
        String error = null;

        String action = getAction( request );

        if ( !StringUtils.isBlank( action ) && ManageSignalementService.ACTION_VALIDER.equals( action ) )
        {
            error = I18nService.getLocalizedString( I18N_ERROR_MANDATORY_ACTION, request.getLocale( ) );
        } else if ( ManageSignalementService.ACTION_REJETER.equals( action ) )
        {
            String[] motifsRejetIds = request.getParameterValues( PARAMETER_MOTIF_REJET );
            boolean motifAutreCheckbox = StringUtils.isNotBlank( request.getParameter( PARAMETER_MOTIF_AUTRE_CHECKBOX ) );

            boolean emptyMotif = ArrayUtils.isEmpty( motifsRejetIds );

            if ( emptyMotif && !motifAutreCheckbox )
            {
                return I18nService.getLocalizedString( I18N_ERROR_MOTIF_REJET, request.getLocale( ) );
            }

            // Vérification si les motifs sont valides
            if ( !emptyMotif )
            {
                List<ObservationRejet> observationList = _observationRejetService.getAllObservationRejetActif( );
                List<ObservationRejet> motifsRejet = new ArrayList<>( );
                for ( ObservationRejet observation : observationList )
                {
                    for ( String motifRejetId : motifsRejetIds )
                    {
                        Integer motifRejetInt = Integer.parseInt( motifRejetId );
                        if ( observation.getActif( ) && observation.getId( ).equals( motifRejetInt ) )
                        {
                            motifsRejet.add( observation );
                        }
                    }
                }

                if ( CollectionUtils.isEmpty( motifsRejet ) )
                {
                    return I18nService.getLocalizedString( I18N_ERROR_MOTIF_REJET, request.getLocale( ) );
                }
            }
            String motifAutre = request.getParameter( PARAMETER_MOTIF_AUTRE );
            if ( motifAutreCheckbox && StringUtils.isBlank( motifAutre ) )
            {
                return I18nService.getLocalizedString( I18N_ERROR_MOTIF_REJET, request.getLocale( ) );
            }

        } else if ( ManageSignalementService.ACTION_PROGRAMMER.equals( action ) && StringUtils.isBlank( request.getParameter( "dateProgrammation" ) ) )
        {
            error = I18nService.getLocalizedString( I18N_ERROR_PROGRAMMATION_DATE, request.getLocale( ) );
        } else if ( ManageSignalementService.ACTION_REQUALIFIER.equals( action ) && StringUtils.isBlank( request.getParameter( PARAMETER_ID_TYPE_ANOMALIE ) ) )
        {

            error = I18nService.getLocalizedString( I18N_ERROR_TYPE_ANOMALIE, request.getLocale( ) );
        }

        return error;
    }

    /**
     * Add motifs to the data model
     *
     * @param model
     *            the model
     */
    private void addMotifs( Map<String, Object> model )
    {
        List<ObservationRejet> motifsActifs = _observationRejetService.getAllObservationRejetActif( );
        model.put( MARK_MOTIFS, motifsActifs );
    }

    /**
     * Add all type Anomalie to the model
     *
     * @param model
     *          the model
     * @param signalement
     *          the report
     *
     */
    private void addTypeAnomalie( Map<String, Object> model, Signalement signalement )
    {

        // get the type signalement
        TypeSignalement typeSignalement = _typeSignalementService.getTypeSignalement( signalement.getTypeSignalement( ).getId( ) );
        model.put( MARK_TYPE, typeSignalement );

        // get all the type signalement
        List<TypeSignalement> types = _typeSignalementService.getAllTypeSignalementActif( );
        ReferenceList listeTypes = ListUtils.toReferenceList( types, "id", "formatTypeSignalement", "", false );
        model.put( MARK_TYPE_LIST, listeTypes );

    }

    /**
     * Get report if parameters are correct
     *
     * @param request
     *            the http request
     * @return the report
     */
    private Signalement getSignalement( HttpServletRequest request )
    {
        Signalement bean = null;
        String strIdSignalement = request.getParameter( MARK_ID );
        String token = request.getParameter( PARAMETER_TOKEN );
        if ( StringUtils.isNotBlank( strIdSignalement ) && StringUtils.isNumeric( strIdSignalement ) )
        {
            Integer id = Integer.valueOf( strIdSignalement );
            bean = _signalementService.getSignalementWithFullPhoto( id );
            if ( ( bean != null ) && ( StringUtils.isBlank( token ) || !token.equals( bean.getToken( ) ) ) )
            {
                bean = null;
                AppLogService.error( "Cannot get signalement bean with id " + strIdSignalement + " and token " + token );
            }
        }
        return bean;
    }

    /**
     * Get the action asked by user.
     *
     * @param request
     *            the http request
     * @return the action
     */
    private String getAction( HttpServletRequest request )
    {
        return request.getParameter( MARK_ACTION );
    }
}
