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

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.dansmarue.business.entities.Arrondissement;
import fr.paris.lutece.plugins.dansmarue.business.entities.Priorite;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.pojo.SignalementsPOJO;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.exception.ParseSignalementFromJSONException;
import fr.paris.lutece.plugins.dansmarue.service.dto.DossierSignalementDTO;
import fr.paris.lutece.plugins.dansmarue.web.SignalementJspBean;
import fr.paris.lutece.plugins.leaflet.modules.dansmarue.entities.Address;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.portal.business.user.AdminUser;
import net.sf.json.JSONObject;

/**
 * ISignalementRestService.
 */
public interface ISignalementRestService
{
    /**
     * Method to check the request and use specific function to answer to request.
     *
     * @param strRequest
     *            the json stream sended by request
     * @param request
     *            the http request
     * @return the answer to request
     * @throws ParseSignalementFromJSONException
     *             parse exception
     */
    String processResquestAnswer( String strRequest, HttpServletRequest request ) throws ParseSignalementFromJSONException;

    /**
     * This function send incident statistics, it is used at application launch.
     *
     * @param jsonSrc
     *            json stream
     * @param request
     *            the http request
     * @return the answer
     */
    String getIncidentStats( JSONObject jsonSrc, HttpServletRequest request );

    /**
     * This request is used to get an incident by its identifier.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String getIncidentsById( JSONObject jsonSrc );

    /**
     * This request is sent to make a list of the closest incidents to the user's geographical position.
     *
     * @param jsonSrc
     *            json stream
     * @return json respons
     */
    String getIncidentsByPosition( JSONObject jsonSrc );

    /**
     * Save a report.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     * @throws ParseSignalementFromJSONException
     *             {@link SignalementJspBean}
     */
    String saveIncident( JSONObject jsonSrc ) throws ParseSignalementFromJSONException;

    /**
     * This request is sent by the application to get the list of all the ongoing incidents that user has created, updated and all the incidents the user
     * declared as resolved.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String getReports( JSONObject jsonSrc );

    /**
     * This request is sent to update an existing incident. This update of the incident means to change the state of the incident. Addnd a new picture is done
     * by an other interface and request.
     *
     * @param jsonSrc
     *            json stream
     * @param request
     *            the http request
     * @return json response
     */
    String updateIncident( JSONObject jsonSrc, HttpServletRequest request );

    /**
     * This request is sent to update an existing report. This update of the report means to change the precision terrain of the report.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String savePrecisionsTerrain( JSONObject jsonSrc );

    /**
     * This request is sent to update a feuille de tournee. This update of the report means to change the infos apres tournee of the incident
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String saveInfoApresTournee( JSONObject jsonSrc );

    /**
     * This request is sent to get all activities around an user. An activity can be: add a picture to one incident, confirm an incident... And an incident can
     * have many activities.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String getUsersActivities( JSONObject jsonSrc );

    /**
     * This request allows to change status for a report.
     *
     * @param jsonSrc
     *            json stream
     * @param request
     *            the http request
     * @return json response
     */
    String changeStatus( JSONObject jsonSrc, HttpServletRequest request );

    /**
     * This request is sent if the user need to change the incident category or the indent address.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String changeIncident( JSONObject jsonSrc );

    /**
     * This request allows to get the pictures of an incident and the commentary of each picture and the date of the picture.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String getIncidentPhotos( JSONObject jsonSrc );

    /**
     * Get list of categorie.
     *
     * @param jsonSrc
     *            json stream
     * @return json response
     */
    String getCategoriesList( JSONObject jsonSrc );

    /**
     * Send incident pictures.
     *
     * @param request
     *            the http request
     * @param requestBodyStream
     *            the input stream
     * @return json response
     */
    String updatePictureIncident( HttpServletRequest request, InputStream requestBodyStream );

    /**
     * get reject report.
     *
     * @return a report Pojo
     */
    SignalementsPOJO signalementAArchiverRejete( );

    /**
     * get report in status done.
     *
     * @return a report Pojo
     */
    SignalementsPOJO signalementAArchiverServiceFait( );

    /**
     * get list of address.
     *
     * @param address
     *            an address
     * @return list of address
     */
    List<Address> getAddressItem( String address );

    /**
     * Get the coordinate.
     *
     * @param dLatLambert
     *            latitude lambert.
     * @param dLngLambert
     *            longitude lambert.
     * @return geom coordinate
     */
    Double [ ] getGeomFromLambertToWgs84( Double dLatLambert, Double dLngLambert );

    /**
     * Gets the geom from lambert 93 to wgs 84.
     *
     * @param dLatLambert
     *            the d lat lambert
     * @param dLngLambert
     *            the d lng lambert
     * @return the geom from lambert 93 to wgs 84
     */
    Double [ ] getGeomFromLambert93ToWgs84( Double dLatLambert, Double dLngLambert );

    /**
     * Find all reports in perimeter.
     *
     * @param lat
     *            latitude
     * @param lng
     *            longitude
     * @param radius
     *            radius
     * @return list of report
     */
    List<DossierSignalementDTO> findAllSignalementInPerimeterWithDTO( Double lat, Double lng, Integer radius );

    /**
     * Find distance between two reports.
     *
     * @param lat1
     *            latitude reports 1
     * @param lng1
     *            longitude reports 1
     * @param lat2
     *            latitude reports 2
     * @param lng2
     *            longitude reports 2
     * @return the distance
     */
    Integer getDistanceBetweenSignalement( double lat1, double lng1, double lat2, double lng2 );

    /**
     * Check if the report is follow by the user.
     *
     * @param nIdSignalement
     *            report id
     * @param userGuid
     *            user guid
     * @return true if is follow
     */
    boolean isSignalementFollowableAndisSignalementFollowedByUser( int nIdSignalement, String userGuid );

    /**
     * Get all priority.
     *
     * @return list of priority
     */
    List<Priorite> getAllPriorite( );

    /**
     * Find prority by id.
     *
     * @param lId
     *            id of the priority
     * @return a priority
     */
    Priorite loadPrioriteById( long lId );

    /**
     * Find district with geom coordinate.
     *
     * @param lng
     *            longitude
     * @param lat
     *            latitude
     * @return district
     */
    Arrondissement getArrondissementByGeom( double lng, double lat );

    /**
     * add a follower to a report.
     *
     * @param signalementId
     *            report id
     * @param guid
     *            user guid
     * @param strUDID
     *            phone udid
     * @param email
     *            user email
     * @param device
     *            phone type
     * @param userToken
     *            token
     * @param createUser
     *            user
     */
    void addFollower( Long signalementId, String guid, String strUDID, String email, String device, String userToken, boolean createUser );

    /**
     * Get current folder.
     *
     * @param longitude
     *            longitude
     * @param latitude
     *            latitude
     * @return current folder
     */
    String getDossiersCourrantsByGeomWithLimit( Double longitude, Double latitude );

    /**
     * Save a report.
     *
     * @param demandeSignalement
     *            a report.
     * @param userName
     *            user name
     * @param userMail
     *            user mail
     * @return true if is ok
     */
    JSONObject sauvegarderSignalement( Signalement demandeSignalement, String userName, String userMail );

    /**
     * list of workflow action.
     *
     * @param nIdSignalement
     *            id report
     * @param user
     *            user
     * @return lis of action
     */
    Collection<Action> getListActionsByIdSignalementAndUser( int nIdSignalement, AdminUser user );

}
