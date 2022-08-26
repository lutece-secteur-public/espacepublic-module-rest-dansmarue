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
package fr.paris.lutece.plugins.dansmarue.modules.rest.rs;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.MultiPart;
import fr.paris.lutece.plugins.dansmarue.business.entities.*;
import fr.paris.lutece.plugins.dansmarue.business.exceptions.AlreadyFollowedException;
import fr.paris.lutece.plugins.dansmarue.business.exceptions.InvalidStateActionException;
import fr.paris.lutece.plugins.dansmarue.modules.rest.pojo.ErrorSignalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.pojo.SignalementsPOJO;
import fr.paris.lutece.plugins.dansmarue.modules.rest.service.SignRequestService;
import fr.paris.lutece.plugins.dansmarue.modules.rest.service.SignalementRestService;
import fr.paris.lutece.plugins.dansmarue.modules.rest.service.formatters.ErrorSignalementFormatterJson;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.exception.ParseSignalementFromJSONException;
import fr.paris.lutece.plugins.dansmarue.service.SignalementPlugin;
import fr.paris.lutece.plugins.dansmarue.service.dto.SignalementDossierDTO;
import fr.paris.lutece.plugins.dansmarue.util.constants.LibrarySiraConstants;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.signrequest.RequestAuthenticator;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * SignalementRest.
 */
@Path( RestConstants.BASE_PATH + SignalementPlugin.PLUGIN_NAME )
public class SignalementRest
{

    /** The signalement rest service. */
    private SignalementRestService _signalementRestService;

    /** The sign request service. */
    private SignRequestService _signRequestService;

    /** The new ws authenticator. */
    @Inject
    @Named( "rest.requestAuthenticator" )
    private RequestAuthenticator _newWsAuthenticator;

    /**
     * Set the signalement rest service.
     *
     * @param signalementRestService
     *            signalementRestService
     */
    public void setSignalementRestService( SignalementRestService signalementRestService )
    {
        _signalementRestService = signalementRestService;
    }

    /**
     * Set the signrequest service.
     *
     * @param signRequestService
     *            report request service
     */
    public void setSignRequestService( SignRequestService signRequestService )
    {
        _signRequestService = signRequestService;
    }

    /**
     * getWADL.
     *
     * @param request
     *            request
     * @return answer
     */
    @GET
    @Path( SignalementRestConstants.PATH_WADL )
    @Produces( MediaType.APPLICATION_XML )
    public String getWADL( @Context HttpServletRequest request )
    {
        StringBuilder sbBase = new StringBuilder( AppPathService.getBaseUrl( request ) );

        if ( sbBase.toString( ).endsWith( SignalementRestConstants.SLASH ) )
        {
            sbBase.deleteCharAt( sbBase.length( ) - 1 );
        }

        sbBase.append( RestConstants.BASE_PATH + SignalementPlugin.PLUGIN_NAME );

        Map<String, Object> model = new HashMap<>( );
        model.put( SignalementRestConstants.MARK_BASE_URL, sbBase.toString( ) );

        HtmlTemplate t = AppTemplateService.getTemplate( SignalementRestConstants.TEMPLATE_WADL, request.getLocale( ), model );

        return t.getHtml( );
    }

    /**
     * Get JSON Stream and answer.
     *
     * @param strJSONStream
     *            JSON Stream
     * @param request
     *            request
     * @return answer
     */
    @POST
    @Path( SignalementRestConstants.PATH_API )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    @Produces( MediaType.APPLICATION_JSON )
    public String processResquestAnswer( @FormParam( SignalementRestConstants.PARAMETERS_JSON_STREAM ) String strJSONStream,
            @Context HttpServletRequest request )
    {
        IFormatter<ErrorSignalement> formatterJson = new ErrorSignalementFormatterJson( );

        if ( StringUtils.isNotBlank( strJSONStream ) )
        {
            // legacy, should be use the library-signrequest
            if ( _signRequestService.isRequestAuthenticated( request, strJSONStream ) || _newWsAuthenticator.isRequestAuthenticated( request ) )
            {
                try
                {
                    AppLogService.debug( strJSONStream );
                    return _signalementRestService.processResquestAnswer( strJSONStream, request );
                }
                catch( ParseSignalementFromJSONException e )
                {
                    AppLogService.error( e );
                    return e.getParseError( );
                }
                catch( Exception e )
                {
                    AppLogService.error( e.getMessage( ), e );

                    ErrorSignalement error = new ErrorSignalement( );
                    error.setErrorCode( SignalementRestConstants.ERROR_API_REST );
                    error.setErrorMessage( e.getMessage( ) );

                    return formatterJson.format( error );
                }
            }
        }
        else
        {
            ErrorSignalement error = new ErrorSignalement( );
            error.setErrorCode( SignalementRestConstants.ERROR_EMPTY_JSON_REQUEST );
            error.setErrorMessage( StringUtils.EMPTY );

            return formatterJson.format( error );
        }

        return null;
    }

    /**
     * Test method.
     *
     * @param strJSONStream
     *            JSON Stream
     * @param request
     *            request
     * @return answer
     */
    @GET
    @Path( SignalementRestConstants.PATH_TEST_GET )
    @Produces( MediaType.TEXT_PLAIN )
    public String processResquestAnswerTest( @PathParam( SignalementRestConstants.PARAMETERS_JSON_STREAM ) String strJSONStream,
            @Context HttpServletRequest request )
    {
        IFormatter<ErrorSignalement> formatterJson = new ErrorSignalementFormatterJson( );

        if ( StringUtils.isNotBlank( request.getParameter( SignalementRestConstants.PARAMETERS_JSON_STREAM ) ) )
        {
            try
            {
                return _signalementRestService.processResquestAnswer( request.getParameter( SignalementRestConstants.PARAMETERS_JSON_STREAM ), request );
            }
            catch( ParseSignalementFromJSONException e )
            {
                AppLogService.error( e );
                return e.getParseError( );
            }
            catch( JSONException e )
            {
                AppLogService.error( e.getMessage( ), e );

                ErrorSignalement error = new ErrorSignalement( );
                error.setErrorCode( SignalementRestConstants.ERROR_BAD_JSON_REQUEST );
                error.setErrorMessage( e.getMessage( ) );

                return formatterJson.format( error );
            }
        }
        else
        {
            ErrorSignalement error = new ErrorSignalement( );
            error.setErrorCode( SignalementRestConstants.ERROR_EMPTY_JSON_REQUEST );
            error.setErrorMessage( StringUtils.EMPTY );

            return formatterJson.format( error );
        }
    }

    /**
     * Method to change defect (report) status.
     *
     * @param request
     *            the http request
     * @param id
     *            the report id
     * @param reference
     *            the report number
     * @param token
     *            the report token
     * @param status
     *            the report new status
     * @param chosenMessage
     *            the message choose for service done
     * @param date
     *            the date
     * @param comment
     *            the comment
     * @param motifRejet
     *            the reject reason
     * @param dateDeProgrammation
     *            programmation date
     * @param idTypeAnomalie
     *            report type
     * @param photo
     *            picture
     * @return answer
     */
    @POST
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + SignalementRestConstants.PATH_CHANGE_STATUS + "/{id}/{reference}/{token}" )
    @Produces( MediaType.APPLICATION_JSON )
    public String changeStatus( @Context HttpServletRequest request, @PathParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_ID ) String id,
            @PathParam( value = SignalementRestConstants.JSON_TAG_REFERENCE ) String reference,
            @PathParam( value = SignalementRestConstants.JSON_TAG_TOKEN ) String token,
            @FormParam( value = SignalementRestConstants.JSON_TAG_STATUS ) String status,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_CHOSEN_MESSAGE ) String chosenMessage,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_DATE_REEL_ACTION ) String date,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_COMMENT ) String comment,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET ) String motifRejet,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_DATE_PROGRAMMATION ) String dateDeProgrammation,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_TYPE_ANOMALIE ) String idTypeAnomalie,
            @FormParam( value = SignalementRestConstants.JSON_TAG_INCIDENT_PHOTO ) String photo )
    {
        AppLogService.info( "changeStatus call from client : " + request.getRemoteHost( ) + " ( " + request.getRemoteAddr( ) + " ) " );
        AppLogService.info( "call parameters : id=" + id + " reference=" + reference + " token=" + token + " status=" + status + " chosenMessage="
                + chosenMessage + " date=" + date + " comment=" + comment + " motifRejet=" + motifRejet + " dateDeProgrammation=" + dateDeProgrammation
                + " idTypeAnomalie=" + idTypeAnomalie );

        JSONObject jsonObject = createJSONStatusObject( id, reference, token, status, chosenMessage, date, comment, motifRejet, dateDeProgrammation,
                idTypeAnomalie, photo );

        AppLogService.info( "parameters to json : "
                + createJSONStatusObject( id, reference, token, status, chosenMessage, date, comment, motifRejet, dateDeProgrammation, idTypeAnomalie, null ) );
        String jsonResponse = _signalementRestService.changeStatus( jsonObject, request );
        AppLogService.info( "response send  : " + jsonResponse );

        return jsonResponse;
    }

    /**
     * Method to create changeStatus JSON.
     *
     * @param id
     *            the report id
     * @param reference
     *            the report number
     * @param token
     *            the token
     * @param status
     *            the report status
     * @param chosenMessage
     *            the service done message
     * @param date
     *            the reschedue date
     * @param comment
     *            the comment
     * @param motifRejet
     *            the reject reason
     * @param dateDeProgrammation
     *            the programming date
     * @param idTypeAnomalie
     *            the type
     * @param photo
     *            the report picture
     * @return JsonObjet of the report
     */
    public JSONObject createJSONStatusObject( String id, String reference, String token, String status, String chosenMessage, String date, String comment,
            String motifRejet, String dateDeProgrammation, String idTypeAnomalie, String photo )
    {
        JSONObject jsonObject = new JSONObject( );
        jsonObject.accumulate( SignalementRestConstants.JSON_TAG_REQUEST, SignalementRestConstants.REQUEST_TYPE_CHANGE_STATUS );

        JSONObject jsonAnswer = new JSONObject( );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ID, id );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_REFERENCE, reference );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_TOKEN, token );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_STATUS, status );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_CHOSEN_MESSAGE, chosenMessage );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_DATE_REEL_ACTION, date );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_COMMENT, comment );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ID_REJET, motifRejet );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_DATE_PROGRAMMATION, dateDeProgrammation );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_TYPE_ANOMALIE, idTypeAnomalie );
        jsonAnswer.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_PHOTO, photo );

        jsonObject.accumulate( SignalementRestConstants.JSON_TAG_ANSWER, jsonAnswer );
        return jsonObject;
    }

    /**
     * Method to get picture and update signalement.
     *
     * @param request
     *            the http request
     * @param requestBodyStream
     *            the request body
     * @return json reponse
     */
    @POST
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + SignalementRestConstants.PATH_PHOTO )
    @Produces( MediaType.APPLICATION_JSON )
    public String updatePictureSignalement( @Context HttpServletRequest request, InputStream requestBodyStream )
    {
        return _signalementRestService.updatePictureIncident( request, requestBodyStream );
    }

    /**
     * Method to get picture and update signalement.
     *
     * @param request
     *            the http request
     * @param uploadedInputStream
     *            the picture stream
     * @param fileDetail
     *            the format file
     * @param incidentId
     *            the report id
     * @return json reponse
     */
    @POST
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + "photo2" )
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.MULTIPART_FORM_DATA )
    public String updatePictureSignalementCordova( @Context HttpServletRequest request, @FormDataParam( "imgField" ) InputStream uploadedInputStream,
            @FormDataParam( "imgField" ) FormDataContentDisposition fileDetail, @FormDataParam( "incident_id" ) String incidentId )
    {
        return _signalementRestService.updatePictureIncidentCordova( request, uploadedInputStream, fileDetail, incidentId );
    }

    /**
     * Black berry get nomenclature.
     *
     * @param request
     *            the http request
     * @return list category
     * @deprecated Gets the nomenclature for blackberry
     */
    @Deprecated
    @GET
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + SignalementRestConstants.PATH_BLACK_BERRY_CAT )
    @Produces( MediaType.TEXT_XML )
    public String blackBerryGetNomenclature( @Context HttpServletRequest request )
    {
        return _signalementRestService.getCategoriesListXml( );
    }

    /**
     * /**.
     *
     * @param strPriorite
     *            strPriorite
     * @param strCategorie
     *            strCategorie
     * @param photoId
     *            photoId
     * @param strIdNomenclature
     *            strIdNomenclature
     * @param strDirection
     *            strDirection
     * @param strLatitude
     *            strLatitude
     * @param strLongitude
     *            strLongitude
     * @param strAdresse
     *            strAdresse
     * @param strKey
     *            strKey
     * @param strEmail
     *            strEmail
     * @param strCommentaire
     *            strCommentaire
     * @param request
     *            request
     * @return answer
     * @deprecated Method for BlackBerry
     */
    @Deprecated
    @POST
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + SignalementRestConstants.PATH_BLACK_BERRY )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    @Produces( MediaType.TEXT_XML )
    public String blackBerryPostSignalement( @FormParam( "1" ) String strPriorite, @FormParam( "2" ) String strCategorie, @FormParam( "recordId" ) Long photoId,
            @FormParam( "10" ) String strIdNomenclature, @FormParam( "12" ) String strDirection, @FormParam( "11_x" ) String strLatitude,
            @FormParam( "11_y" ) String strLongitude, @FormParam( "11_address" ) String strAdresse, @FormParam( "key" ) String strKey,
            @FormParam( "6" ) String strEmail, @FormParam( "8" ) String strCommentaire, @Context HttpServletRequest request )
    {
        Signalement signalement = new Signalement( );

        Priorite priorite = new Priorite( );
        priorite.setId( Long.valueOf( strPriorite ) );
        signalement.setPriorite( priorite );

        TypeSignalement typeSignalement = new TypeSignalement( );
        typeSignalement.setId( 4 );
        signalement.setTypeSignalement( typeSignalement );

        List<Adresse> pAdresses = new ArrayList<>( );
        Adresse adresse = new Adresse( );
        adresse.setLat( Double.parseDouble( strLatitude ) );
        adresse.setLng( Double.parseDouble( strLongitude ) );
        adresse.setAdresse( strAdresse );
        pAdresses.add( adresse );
        signalement.setAdresses( pAdresses );

        Signaleur signaleur = new Signaleur( );
        signaleur.setMail( strEmail );

        List<Signaleur> pSignaleurs = new ArrayList<>( );
        pSignaleurs.add( signaleur );
        signalement.setSignaleurs( pSignaleurs );

        signalement.setCommentaire( strCommentaire );

        List<PhotoDMR> pPhotos = new ArrayList<>( );
        pPhotos.add( _signalementRestService.getPictureForBlackBerry( photoId ) );
        signalement.setPhotos( pPhotos );

        return _signalementRestService.doSaveIncidentForBlackBerry( signalement );
    }

    /**
     * Black berry post picture.
     *
     * @param multiPart
     *            the multipart request (containing picture)
     * @return XML format answer
     * @deprecated Method to post picture from BlackBerry
     */
    @Deprecated
    @POST
    @Path( SignalementRestConstants.PATH_API + SignalementRestConstants.SLASH + SignalementRestConstants.PATH_BLACK_BERRY_PICTURE )
    public String blackBerryPostPicture( MultiPart multiPart )
    {

        BodyPartEntity bpe = (BodyPartEntity) multiPart.getBodyParts( ).get( 1 ).getEntity( );
        try
        {
            InputStream source = bpe.getInputStream( );
            _signalementRestService.updatePictureIncidentBlackberry( source );
        }
        catch( Exception e )
        {
            AppLogService.error( e );
            return "ko";
        }
        return "ok";
    }

    /**
     * archive report rejected.
     *
     * @param request
     *            the http request
     * @return the report object
     */
    @GET
    @Path( SignalementRestConstants.PATH_SIGNALEMENT_A_ARCHIVER_REJETE )
    @Produces( MediaType.APPLICATION_JSON )
    public SignalementsPOJO serviceAArchiverRejete( @Context HttpServletRequest request )
    {
        return _signalementRestService.signalementAArchiverRejete( );
    }

    /**
     * Service A archiver service fait.
     *
     * @param request
     *            the http request
     * @return the report object
     */
    @GET
    @Path( SignalementRestConstants.PATH_SIGNALEMENT_A_ARCHIVER_SERVICE_FAIT )
    @Produces( MediaType.APPLICATION_JSON )
    public SignalementsPOJO serviceAArchiverServiceFait( @Context HttpServletRequest request )
    {
        return _signalementRestService.signalementAArchiverServiceFait( );
    }

    /**
     * Gets the all sous type signalement cascade.
     *
     * @param parameters
     *            the request parameter
     * @return all type of report
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( LibrarySiraConstants.REST_TYPE_SIGNALEMENT_SERVICE )
    public String getAllSousTypeSignalementCascade( String parameters )
    {

        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );

            JSONArray array = (JSONArray) object.get( "listTypeSignalement" );
            List<TypeSignalement> typeSignalement = mapper.readValue( array.toString( ),
                    mapper.getTypeFactory( ).constructCollectionType( List.class, TypeSignalement.class ) );
            _signalementRestService.getAllSousTypeSignalementCascade( (Integer) object.get( "typeSignalementId" ), typeSignalement );
            return mapper.writeValueAsString( typeSignalement );

        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_ALL_SOUS_TYPE_SIGNALEMENT_CASCADE, e.getMessage( ) );

        }
    }

    /**
     * Gets the signalement by id.
     *
     * @param parameters
     *            the request parameter
     * @return the report
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( LibrarySiraConstants.REST_GET_SIGNALEMENT_BY_ID )
    public String getSignalementById( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = mapper.readValue( parameters, JSONObject.class );
            Long lIdSignalement = Long.valueOf( (String) object.get( SignalementRestConstants.JSON_TAG_SIGNALEMENT_ID ) );

            SignalementDossierDTO dto = new SignalementDossierDTO( );

            Signalement signalement = _signalementRestService.getSignalementByID( lIdSignalement );
            dto.setNumRessource( signalement.getNumeroSignalement( ) );
            dto.setPhotos( _signalementRestService.getPhotosBySignalementId( lIdSignalement ) );
            dto.setAdresse( _signalementRestService.getAdresseBySignalementId( lIdSignalement ) );
            dto.setDateCreation( signalement.getDateCreation( ) );
            dto.setHeureCreation( signalement.getHeureCreation( ) );
            dto.setId( signalement.getId( ).intValue( ) );
            dto.setType( signalement.getPrefix( ) );

            return mapper.writeValueAsString( dto );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_SIGNALEMENT_BY_ID, e.getMessage( ) );
        }
    }

    /**
     * Give a address.
     *
     * @param parameters
     *            the request parameter
     * @return the address
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getAddressItem" )
    public String getAddressItem( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            return mapper.writeValueAsString( _signalementRestService.getAddressItem( parameters ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_SIGNALEMENT_BY_ID, e.getMessage( ) );
        }
    }

    /**
     * Get Geom coordinate.
     *
     * @param parameters
     *            the request parameter
     * @return the geom
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getGeomFromLambertToWgs84" )
    public String getGeomFromLambertToWgs84( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double dLatLambert = (Double) object.get( "dLatLambert" );
            Double dLngLambert = (Double) object.get( "dLngLambert" );

            return mapper.writeValueAsString( _signalementRestService.getGeomFromLambertToWgs84( dLatLambert, dLngLambert ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_SIGNALEMENT_BY_ID, e.getMessage( ) );

        }
    }

    /**
     * Gets the geom from lambert 93 to wgs 84.
     *
     * @param parameters
     *            the parameters
     * @return the geom from lambert 93 to wgs 84
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getGeomFromLambert93ToWgs84" )
    public String getGeomFromLambert93ToWgs84( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double dLatLambert = (Double) object.get( "dLatLambert" );
            Double dLngLambert = (Double) object.get( "dLngLambert" );

            return mapper.writeValueAsString( _signalementRestService.getGeomFromLambert93ToWgs84( dLatLambert, dLngLambert ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_GEOM_FROM_LAMBERT_TO_WQ84, e.getMessage( ) );
        }
    }

    /**
     * Find all report in the perimeter.
     *
     * @param parameters
     *            the request parameter
     * @return list of report in json format.
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "findAllSignalementInPerimeterWithDTO" )
    public String findAllSignalementInPerimeterWithDTO( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double lat = (Double) object.get( "lat" );
            Double lng = (Double) object.get( "lng" );
            Integer radius = (Integer) object.get( "radius" );

            return mapper.writeValueAsString( _signalementRestService.findAllSignalementInPerimeterWithDTO( lat, lng, radius ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_FIND_ALL_SIGNALLEMENT_IN_PERIMETER_WITH_DTO, e.getMessage( ) );

        }
    }

    /**
     * Give distance between report.
     *
     * @param parameters
     *            the request parameter
     * @return the distance
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getDistanceBetweenSignalement" )
    public String getDistanceBetweenSignalement( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double lat1 = (Double) object.get( "lat1" );
            Double lng1 = (Double) object.get( "lng1" );
            Double lat2 = (Double) object.get( "lat2" );
            Double lng2 = (Double) object.get( "lng2" );

            return mapper.writeValueAsString( _signalementRestService.getDistanceBetweenSignalement( lat1, lng1, lat2, lng2 ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_DISTANCE_BETWEEN_SIGNALEMENT, e.getMessage( ) );

        }
    }

    /**
     * Check if the report is follow by user.
     *
     * @param parameters
     *            the request parameter
     * @return true if the report is follow by user
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "isSignalementFollowable" )
    public String isSignalementFollowableAndisSignalementFollowedByUser( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Integer nIdSignalement = (Integer) object.get( SignalementRestConstants.JSON_TAG_SIGNALEMENT_ID_N );
            String userGuid = (String) object.get( "userGuid" );

            return mapper.writeValueAsString( _signalementRestService.isSignalementFollowableAndisSignalementFollowedByUser( nIdSignalement, userGuid ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_IS_SIGNALEMENT_FOLLOWABLE_AND_IS_SIGNALEMENT_FOLLOWED_BY_USER, e.getMessage( ) );

        }
    }

    /**
     * Get all priority.
     *
     * @return the list of report priority.
     */
    @GET
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getAllPriorite" )
    @Encoded
    public String getAllPriorite( )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            return mapper.writeValueAsString( _signalementRestService.getAllPriorite( ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_ALL_PRIORITE, e.getMessage( ) );

        }
    }

    /**
     * Get priority find by Id.
     *
     * @param parameters
     *            the request parameter
     *
     * @return the priority.
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "loadPrioriteById" )
    public String loadPrioriteById( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            long lId = object.getLong( "lId" );

            return mapper.writeValueAsString( _signalementRestService.loadPrioriteById( lId ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_LOAD_PRIORITE_BY_ID, e.getMessage( ) );

        }
    }

    /**
     * Give the district corresponding to the geom coordinate.
     *
     * @param parameters
     *            the request parameter
     *
     * @return the priority.
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getArrondissementByGeom" )
    public String getArrondissementByGeom( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double lat = ( (Number) object.get( "lat" ) ).doubleValue( );
            Double lng = ( (Number) object.get( "lng" ) ).doubleValue( );

            return mapper.writeValueAsString( _signalementRestService.getArrondissementByGeom( lng, lat ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_ARRONDISSEMENT_BY_GEOM, e.getMessage( ) );

        }
    }

    /**
     * Give all the report type.
     *
     * @param request
     *            the request parameter
     *
     * @return all the report type.
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getTypeSignalementTree" )
    public String getTypeSignalementTree( @Context HttpServletRequest request )
    {
        return _signalementRestService.getCategoriesListJson( );

    }

    /**
     * Gets the type signalement tree for source.
     *
     * @param idSource
     *            the id source
     * @param request
     *            the request
     * @return the type signalement tree for source
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getTypeSignalementTreeForSource/{idSource}" )
    public String getTypeSignalementTreeForSource( @PathParam( SignalementRestConstants.PARAMETERS_ID_SOURCE ) Integer idSource,
            @Context HttpServletRequest request )
    {
        return _signalementRestService.getCategoriesListJsonForSource( idSource );
    }

    /**
     * Gets the actualite.
     *
     * @param versionActualite
     *            the actualite version
     * @param request
     *            the request
     * @return the actualite
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getActualite/{versionActualite}" )
    public String getActualite( @PathParam( SignalementRestConstants.PARAMETERS_VERSION_ACTUALITE ) int versionActualite,
            @Context HttpServletRequest request )
    {
        return _signalementRestService.getActualiteListJson( versionActualite );
    }

    /**
     * Gets the aide.
     *
     * @param versionAide
     *            the aide version
     * @param request
     *            the request
     * @return the aide
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getAide/{versionAide}" )
    public String getAide( @PathParam( SignalementRestConstants.PARAMETERS_VERSION_AIDE ) int versionAide,
            @Context HttpServletRequest request )
    {
        return _signalementRestService.getAideListJson( versionAide );
    }

    /**
     * Gets the infos for source.
     *
     * @param idSource
     *            the id source
     * @param request
     *            the request
     * @return the infos for source
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getInfosForSource/{idSource}" )
    public String getInfosForSource( @PathParam( SignalementRestConstants.PARAMETERS_ID_SOURCE ) Integer idSource, @Context HttpServletRequest request )
    {
        ObjectMapper mapper = new ObjectMapper( );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

        Source source = _signalementRestService.getInfosForSource( idSource );

        try
        {
            return mapper.writeValueAsString( source );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );
        }
    }

    /**
     * Type of report.
     *
     * @param parameters
     *            the request parameter
     * @return type of report
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getTypeSignalement" )
    public String getTypeSignalement( String parameters )
    {
        JSONObject object;

        try
        {
            object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Integer nIdSignalement = (Integer) object.get( "nIdSignalement" );
            TypeSignalement typeSignalement = _signalementRestService.getTypeSignalement( nIdSignalement );

            return formatJsonTypeSignalement( typeSignalement );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );

        }
    }

    /**
     * Check if DMR is online.
     *
     * @return true if DMR is up
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "isDmrOnline" )
    public String isDmrOnline( )
    {
        JSONObject jObject = new JSONObject( );
        jObject.put( "online", true );

        String messageInformation = DatastoreService.getDataValue( "sitelabels.site_property.signalement.mobile.message.information", null );
        if ( StringUtils.isNotBlank( messageInformation ) )
        {
            jObject.accumulate( "message_information", messageInformation );
        }

        return jObject.toString( );
    }

    /**
     * find a type of report.
     *
     * @param parameters
     *            the request parameter.
     * @return type of report.
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "findByIdTypeSignalement" )
    public String findByIdTypeSignalement( String parameters )
    {
        JSONObject object;

        try
        {
            object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Integer nIdSignalement = (Integer) object.get( "nIdSignalement" );
            TypeSignalement typeSignalement = _signalementRestService.findByIdTypeSignalement( nIdSignalement );

            return formatJsonTypeSignalement( typeSignalement );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );

        }
    }

    /**
     * Add follower for a report.
     *
     * @param parameters
     *            the request parameter.
     * @return true if is OK
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "addFollower" )
    public String addFollower( String parameters )
    {
        JSONObject jObject = new JSONObject( );
        try
        {
            JSONObject object;
            object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Long signalementId = object.getLong( "signalementId" );
            String guid = object.getString( "guid" );
            String strUDID = object.getString( "strUDID" );
            String email = object.getString( "email" );
            String device = object.getString( "device" );
            String userToken = object.getString( "userToken" );
            boolean createUser = object.getBoolean( "createUser" );

            _signalementRestService.addFollower( signalementId, guid, strUDID, email, device, userToken, createUser );
            jObject.put( "isAddOk", true );

        }
        catch( InvalidStateActionException | AlreadyFollowedException | IOException e )
        {
            AppLogService.error( e );
            jObject.put( "isAddOk", false );
        }
        return jObject.toString( );
    }

    /**
     * Gets the dossiers courrants by geom with limit.
     *
     * @param parameters
     *            the request parameter
     * @return current folder.
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getDossiersCourrantsByGeomWithLimit" )
    public String getDossiersCourrantsByGeomWithLimit( String parameters )
    {

        JSONObject object;
        try
        {
            object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            Double longitude = object.getDouble( "longitude" );
            Double latitude = object.getDouble( "latitude" );

            return _signalementRestService.getDossiersCourrantsByGeomWithLimit( longitude, latitude );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );
        }

    }

    /**
     * Format a error.
     *
     * @param errorCode
     *            error Code
     * @param errorMessage
     *            error Message
     * @return json in error format
     */
    private String formatJsonError( int errorCode, String errorMessage )
    {
        IFormatter<ErrorSignalement> formatterJson = new ErrorSignalementFormatterJson( );
        ErrorSignalement error = new ErrorSignalement( );
        error.setErrorCode( errorCode );
        error.setErrorMessage( errorMessage );
        return formatterJson.format( error );
    }

    /**
     * Format report type.
     *
     * @param typeSignalement
     *            type of report
     * @return type of report
     */
    private String formatJsonTypeSignalement( TypeSignalement typeSignalement )
    {

        if ( typeSignalement == null )
        {
            return null;
        }

        JSONObject json = new JSONObject( );
        json.accumulate( "id", typeSignalement.getId( ) );
        json.accumulate( "libelle", typeSignalement.getLibelle( ).replace( "\"", "" ) );
        json.accumulate( "actif", typeSignalement.getActif( ) );
        json.accumulate( "unit", typeSignalement.getUnit( ) );
        json.accumulate( "idTypeSignalementParent", typeSignalement.getIdTypeSignalementParent( ) );
        json.accumulate( "image", typeSignalement.getImage( ) );
        json.accumulate( "ordre", typeSignalement.getOrdre( ) );
        json.accumulate( "imageUrl", typeSignalement.getImageUrl( ) );
        json.accumulate( "idCategory", typeSignalement.getIdCategory( ) );
        json.accumulate( "alias", typeSignalement.getAlias( ) );
        json.accumulate( "aliasMobile", typeSignalement.getAliasMobile( ) );
        if ( typeSignalement.isHorsDMR( ) )
        {
            json.accumulate( SignalementRestConstants.JSON_TAG_HORS_DMR, typeSignalement.isHorsDMR( ) );
            json.accumulate( SignalementRestConstants.JSON_TAG_MESAGE_HORS_DMR, typeSignalement.getMessageHorsDMR( ) );
        }

        if ( typeSignalement.getTypeSignalementParent( ) != null )
        {
            json.accumulate( "typeSignalementParent", formatJsonTypeSignalement( typeSignalement.getTypeSignalementParent( ) ) );
        }

        return json.toString( );
    }

    /**
     * Give a list of action workflow.
     *
     * @param parameters
     *            the request parameters
     * @return actions workflow
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( LibrarySiraConstants.REST_GET_LIST_WORKFLOW_ACTION )
    public String getWorkflowActions( String parameters )
    {
        try
        {
            JSONObject object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY ).configure( Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            int idSignalement = object.getInt( SignalementRestConstants.JSON_TAG_SIGNALEMENT_ID_N );
            AdminUser user = mapper.readValue( object.get( "user" ).toString( ), AdminUser.class );

            Collection<Action> listActionsPossibles = _signalementRestService.getListActionsByIdSignalementAndUser( idSignalement, user );

            return mapper.writeValueAsString( listActionsPossibles );

        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_WORKFLOW_ACTION, e.getMessage( ) );
        }

    }

    /**
     * save a report.
     *
     * @param parameters
     *            the request parameters
     * @return true if is ok
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "sauvegarderSignalement" )
    public String sauvegarderSignalement( String parameters )
    {
        JSONObject object;
        try
        {
            object = new ObjectMapper( ).readValue( parameters, JSONObject.class );
            ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
            mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

            Signalement demandeSignalement = mapper.readValue( object.get( "demandeSignalement" ).toString( ), Signalement.class );

            String userName = object.containsKey( "userName" ) ? object.getString( "userName" ) : null;
            String userMail = object.containsKey( "userMail" ) ? object.getString( "userMail" ) : null;

            return _signalementRestService.sauvegarderSignalement( demandeSignalement, userName, userMail ).toString( );

        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_SAVE_SIGNALEMENT, e.getMessage( ) );
        }

    }

    /**
     * Push for service done.
     *
     * @param parameters
     *            the request parameters
     * @param request
     *            the http request
     * @return true if is service done
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "validateServiceFaitSignalement" )
    public String validateServiceFaitSignalement( String parameters, @Context HttpServletRequest request )
    {
        JSONObject jObject = new JSONObject( );
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        JSONObject object;
        try
        {
            object = mapper.readValue( parameters, JSONObject.class );
            String token = object.containsKey( SignalementRestConstants.JSON_TAG_TOKEN ) ? object.getString( SignalementRestConstants.JSON_TAG_TOKEN ) : null;
            Signalement signalement = _signalementRestService.getSignalementByToken( token );

            jObject.put( "isServiceFait", _signalementRestService.validateServiceFaitSignalement( signalement, request ) );

            return jObject.toString( );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_SIGNALEMENT_BY_TOKEN, e.getMessage( ) );
        }

    }

    /**
     * Find report by token.
     *
     * @param parameters
     *            the request parameters
     * @return the report
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getSignalementByToken" )
    public String getSignalementByToken( String parameters )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        JSONObject object;
        try
        {
            object = mapper.readValue( parameters, JSONObject.class );
            String token = object.containsKey( SignalementRestConstants.JSON_TAG_TOKEN ) ? object.getString( SignalementRestConstants.JSON_TAG_TOKEN ) : null;
            Signalement signalement = _signalementRestService.getSignalementByToken( token );

            return formatJsonSignalement( signalement );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_SIGNALEMENT_BY_TOKEN, e.getMessage( ) );
        }

    }

    /**
     * Give the report in json format.
     *
     * @param signalement
     *            the report.
     * @return the report in json format
     */
    private String formatJsonSignalement( Signalement signalement )
    {

        if ( signalement == null )
        {
            return null;
        }

        JSONObject json = new JSONObject( );
        JSONObject adresse = new JSONObject( );

        for ( Adresse adr : signalement.getAdresses( ) )
        {
            adresse.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ID, adr.getId( ) );
            adresse.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_ADDRESS, adr.getAdresse( ) );
            adresse.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_LAT, adr.getLat( ) );
            adresse.accumulate( SignalementRestConstants.JSON_TAG_INCIDENT_LNG, adr.getLng( ) );
        }

        json.accumulate( "id", signalement.getId( ) );
        json.accumulate( "adresses", adresse );
        json.accumulate( "prefix", signalement.getPrefix( ) );
        json.accumulate( "annee", signalement.getAnnee( ) );
        json.accumulate( "mois", signalement.getMois( ) );
        json.accumulate( "numero", signalement.getNumero( ) );
        json.accumulate( "typeSignalement", formatJsonTypeSignalement( signalement.getTypeSignalement( ) ) );

        if ( signalement.getPhotos( ) != null )
        {
            for ( PhotoDMR photo : signalement.getPhotos( ) )
            {
                if ( photo.getVue( ).equals( SignalementRestConstants.VUE_PRES ) )
                {
                    json.accumulate( "pictureClose", AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                }
                else if ( photo.getVue( ).equals( SignalementRestConstants.VUE_ENSEMBLE ) )
                {
                    json.accumulate( "pictureFar", AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                }
                else if ( photo.getVue( ).equals( SignalementRestConstants.VUE_SERVICE_FAIT ) )
                {
                    json.accumulate( "pictureServiceFait", AppPropertiesService.getProperty( SignalementRestConstants.PROPERTY_URL_PICTURE ) + photo.getId( ) );
                }
            }
        }

        return json.toString( );
    }

    /**
     * Give the history for a report.
     *
     * @param parameters
     *            the parameters
     * @param request
     *            the http request
     * @return the history report
     */
    @POST
    @Consumes( {
        MediaType.APPLICATION_JSON
    } )
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getHistorySignalement" )
    public String getHistorySignalement( String parameters, @Context HttpServletRequest request )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        JSONObject object;
        try
        {
            object = mapper.readValue( parameters, JSONObject.class );
            String id = object.containsKey( SignalementRestConstants.JSON_TAG_SIGNALEMENT_ID )
                    ? object.getString( SignalementRestConstants.JSON_TAG_SIGNALEMENT_ID )
                            : null;

                    Integer idSignalement = mapper.readValue( id, Integer.class );

                    JSONObject response = _signalementRestService.getHistorySignalement( idSignalement, request );

                    return mapper.writeValueAsString( response );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_GET_HISTORY_SIGNALEMENT, e.getMessage( ) );
        }

    }

    /**
     * Give all messages for service done.
     *
     * @return messages
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getTypeMessageServiceFait" )
    public String getTypeMessageServiceFait( )
    {
        ObjectMapper mapper = new ObjectMapper( ).setVisibility( JsonMethod.FIELD, Visibility.ANY );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

        try
        {
            JSONObject response = _signalementRestService.selectMessageServiceFaitPresta( );

            return mapper.writeValueAsString( response );

        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );

        }
    }

    /**
     * Give all messages for service done.
     *
     * @return messages
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getRaisonRejet" )
    public String getRaisonRejet( )
    {
        ObjectMapper mapper = new ObjectMapper( );
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );

        try
        {
            return mapper.writeValueAsString( _signalementRestService.getRaisonRejet( ) );
        }
        catch( IOException e )
        {
            AppLogService.error( e );
            return formatJsonError( SignalementRestConstants.ERROR_BAD_JSON_REQUEST, e.getMessage( ) );

        }
    }

    /*
     * Gets the anomalie by number.
     *
     * Ancienne méthode de récupération sans le guid. Permet aux users n'ayant pas fait la MAJ de l'appli de pouvoir utiliser la recherche sans guid (mais avec
     * le bug de suivi: DMR-2092)
     *
     * @param number the number
     *
     * @param request the request
     *
     * @return the anomalie by number
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getAnomalieByNumber/{number}" )
    public String getAnomalieByNumber( @PathParam( SignalementRestConstants.PARAMETER_NUMBER ) String number, @Context HttpServletRequest request )
    {
        return _signalementRestService.getAnomalieByNumber( number, "-1" );
    }

    /**
     * Gets the anomalie by number.
     *
     * @param number
     *            the number
     * @param guid
     *            the guid
     * @param request
     *            the request
     * @return the anomalie by number
     */
    @GET
    @Produces( {
        MediaType.APPLICATION_JSON + ";charset=utf-8"
    } )
    @Path( "getAnomalieByNumber/{number}/{guid}" )
    public String getAnomalieByNumber( @PathParam( SignalementRestConstants.PARAMETER_NUMBER ) String number,
            @PathParam( SignalementRestConstants.PARAMETER_GUID ) String guid, @Context HttpServletRequest request )
    {
        return _signalementRestService.getAnomalieByNumber( number, guid );
    }

    /**
     * Process identity store.
     *
     * @param strJSONStream
     *            the str JSON stream
     * @param request
     *            the request
     * @return the string
     */
    @POST
    @Path( "identitystore" )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    @Produces( MediaType.APPLICATION_JSON )
    public String processIdentityStore( @FormParam( SignalementRestConstants.PARAMETERS_JSON_STREAM ) String strJSONStream,
            @Context HttpServletRequest request )
    {
        IFormatter<ErrorSignalement> formatterJson = new ErrorSignalementFormatterJson( );

        if ( StringUtils.isNotBlank( strJSONStream ) )
        {

            try
            {
                AppLogService.debug( strJSONStream );
                return _signalementRestService.processIdentityStore( strJSONStream );
            }
            catch( Exception e )
            {
                AppLogService.error( e.getMessage( ), e );

                ErrorSignalement error = new ErrorSignalement( );
                error.setErrorCode( SignalementRestConstants.ERROR_API_REST );
                error.setErrorMessage( e.getMessage( ) );

                return formatterJson.format( error );
            }
        }
        else
        {
            ErrorSignalement error = new ErrorSignalement( );
            error.setErrorCode( SignalementRestConstants.ERROR_EMPTY_JSON_REQUEST );
            error.setErrorMessage( StringUtils.EMPTY );

            return formatterJson.format( error );
        }
    }
}
