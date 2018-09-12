/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants;

import java.util.List;

import fr.paris.lutece.plugins.dansmarue.utils.SignalementUtils;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 *
 * SignalementRestConstants
 */
public final class SignalementRestConstants
{
    // CONSTANTS
    public static final String       PATH_WADL                                                           = "wadl";
    public static final String       PATH_API                                                            = "api";
    public static final String       PATH_TEST_GET                                                       = "test_get";
    public static final String       PATH_PHOTO                                                          = "photo";
    public static final String       PATH_CHANGE_STATUS                                                  = "changeStatus";
    public static final String       PATH_BLACK_BERRY                                                    = "bb";
    public static final String       PATH_BLACK_BERRY_CAT                                                = "bb_cat";
    public static final String       PATH_BLACK_BERRY_PICTURE                                            = "bb_picture";
    public static final String       PATH_SIGNALEMENT_A_ARCHIVER_REJETE                                  = "signalementAArchiverRejete";
    public static final String       PATH_SIGNALEMENT_A_ARCHIVER_SERVICE_FAIT                            = "signalementAArchiverServiceFait";
    public static final String       SLASH                                                               = "/";
    public static final String       EQUAL                                                               = "=";
    public static final String       AMPERSAND                                                           = "&";
    public static final String       QUESTION_MARK                                                       = "?";
    public static final String       SIGNALEMENT_PREFIX_IOS                                              = AppPropertiesService.getProperty( "signalement.prefix.origin.ios" );
    public static final String       SIGNALEMENT_PREFIX_ANDROID                                          = AppPropertiesService.getProperty( "signalement.prefix.origin.android" );
    public static final String       SIGNALEMENT_PREFIX_TELESERVICE                                      = AppPropertiesService.getProperty( "signalement.prefix.origin.android" );
    public static final String       SIGNALEMENT_PREFIX_KEY                                              = "signalement.prefix.origin";
    public static final List<String> SIGNALEMENT_PREFIXES                                                = SignalementUtils.getProperties( SIGNALEMENT_PREFIX_KEY );

    // JSON TAG
    public static final String       JSON_TAG_REQUEST                                                    = "request";
    public static final String       JSON_TAG_ANSWER                                                     = "answer";
    public static final String       JSON_TAG_STATUS                                                     = "status";
    public static final String       JSON_TAG_REFERENCE                                                  = "reference";
    public static final String       JSON_TAG_TOKEN                                                      = "token";
    public static final String       JSON_TAG_CLOSEST_INCIDENTS                                          = "closest_incidents";
    public static final String       JSON_TAG_INCIDENT                                                   = "incident";
    public static final String       JSON_TAG_INCIDENTS                                                  = "incidents";
    public static final String       JSON_TAG_POSITION                                                   = "position";
    public static final String       JSON_TAG_LATITUDE                                                   = "latitude";
    public static final String       JSON_TAG_LONGITUDE                                                  = "longitude";
    public static final String       JSON_TAG_INCIDENT_LOG                                               = "incidentLog";
    public static final String       JSON_TAG_RESOLVED_INCIDENTS                                         = "resolved_incidents";
    public static final String       JSON_TAG_ONGOING_INCIDENTS                                          = "ongoing_incidents";
    public static final String       JSON_TAG_UPDATED_INCIDENTS                                          = "updated_incidents";
    public static final String       JSON_TAG_DECLARED_INCIDENTS                                         = "declared_incidents";
    public static final String       JSON_TAG_PHOTOS                                                     = "photos";
    public static final String       JSON_TAG_PHOTO_URL                                                  = "photo_url";
    public static final String       JSON_TAG_NUMERO_MESSAGE                                             = "numero_message";
    public static final String       JSON_TAG_MESSAGE                                                    = "message";
    public static final String       JSON_TAG_MESSAGES                                                   = "messages";
    public static final String       JSON_TAG_COMMENTARY                                                 = "commentary";
    public static final String       JSON_TAG_UDID                                                       = "udid";
    public static final String       JSON_TAG_EMAIL                                                      = "email";
    public static final String       JSON_TAG_GUID                                                       = "guid";
    public static final String       JSON_TAG_ANOMALY_DONE                                               = "service fait";
    public static final String       JSON_TAG_ANOMALY_REJECTED                                           = "rejeter";
    public static final String       JSON_TAG_ANOMALY_PROGRAMMED                                         = "programmer";
    public static final String       JSON_TAG_ANOMALY_REQUALIFIED                                        = "requalifier";
    public static final String       JSON_TAG_ANOMALY_A_REQUALIFIED                                      = "a requalifier";
    public static final String       JSON_TAG_DEVICE                                                     = "device";
    public static final String       JSON_TAG_USER_TOKEN                                                 = "userToken";
    public static final String       JSON_TAG_USER                                                       = "user";
    public static final String       JSON_TAG_RESOLVED_AUTHORIZATION                                     = "resolved_authorization";
    
    // Pour le choix des messages prestataires
    public static final String       JSON_TAG_MESSAGE_TRANSFERE                                          = "message si état transféré à un tiers";
    public static final String       JSON_TAG_MESSAGE_PROGRAMME                                          = "message si état service programmé tiers";

    // JSON TAG FOR INCIDENT OBJECT
    public static final String       JSON_TAG_INCIDENT_ID                                                = "id";
    public static final String       JSON_TAG_SIGNALEMENT_ID_N                                           = "nIdSignalement";
    public static final String       JSON_TAG_SIGNALEMENT_ID                                             = "idSignalement";
    public static final String       JSON_TAG_INCIDENT_CATEGORIE_ID                                      = "categoryId";
    public static final String       JSON_TAG_INCIDENT_STATE                                             = "state";
    public static final String       JSON_TAG_INCIDENT_ADDRESS                                           = "address";
    public static final String       JSON_TAG_INCIDENT_DESCRIPTIVE                                       = "descriptive";
    public static final String       JSON_TAG_INCIDENT_PRIORITE_ID                                       = "priorityId";
    public static final String       JSON_TAG_INCIDENT_DATE                                              = "date";
    public static final String       JSON_TAG_INCIDENT_HOUR                                              = "hour";
    public static final String       JSON_TAG_INCIDENT_LAT                                               = "lat";
    public static final String       JSON_TAG_INCIDENT_LNG                                               = "lng";
    public static final String       JSON_TAG_INCIDENT_PICTURES                                          = "pictures";
    public static final String       JSON_TAG_INCIDENT_CLOSE                                             = "close";
    public static final String       JSON_TAG_INCIDENT_FAR                                               = "far";
    public static final String       JSON_TAG_INCIDENT_DONE                                              = "done";
    public static final String       JSON_TAG_INCIDENT_CONFIRMS                                          = "confirms";
    public static final String       JSON_TAG_INCIDENT_PRIORITY                                          = "priorityId";
    public static final String       JSON_TAG_INCIDENT_INVALIDATIONS                                     = "invalidations";
    public static final String       JSON_TAG_INCIDENT_COMMENT                                           = "comment";
    public static final String       JSON_TAG_INCIDENT_CHOSEN_MESSAGE                                    = "chosenMessage";
    public static final String       JSON_TAG_INCIDENT_DATE_REEL_ACTION                                  = "action_date";
    public static final String       JSON_TAG_INCIDENT_ID_REJET                                          = "rejection_reason";
    public static final String       JSON_TAG_INCIDENT_DATE_PROGRAMMATION                                = "programming_date";
    public static final String       JSON_TAG_INCIDENT_TYPE_ANOMALIE                                     = "id_type_anomalie";
    public static final String       JSON_TAG_INCIDENT_PHOTO                                             = "photo";
    public static final String       JSON_TAG_INCIDENT_CONGRATULATIONS                                   = "congratulations";
    public static final String       JSON_TAG_INCIDENT_ALIAS                                             = "alias";
    public static final String       JSON_TAG_INCIDENT_ORIGIN                                            = "origin";
    public static final String       JSON_TAG_INCIDENT_SOURCE                                            = "source";
    public static final String       JSON_TAG_INCIDENT_REPORTER_GUID                                     = "reporterGuid";
    public static final String       JSON_TAG_INCIDENT_FOLLOWERS                                         = "followers";
    public static final String       JSON_TAG_IS_INCIDENT_FOLLOWED_BY_USER                               = "isIncidentFollowedByUser";

    // JSON TAG FOR LOG
    public static final String       JSON_TAG_LOG_INCIDENT_ID                                            = "incidentId";
    public static final String       JSON_TAG_LOG_UDID                                                   = "udid";
    public static final String       JSON_TAG_LOG_DATE                                                   = "date";
    public static final String       JSON_TAG_LOG_STATUS                                                 = "status";

    // JSON TAG FOR CATEGORIES
    public static final String       JSON_TAG_CATEGORIES_CHILDREN_ID                                     = "children_id";
    public static final String       JSON_TAG_CATEGORIES_NAME                                            = "name";
    public static final String       JSON_TAG_CATEGORIES_PARENT_ID                                       = "parent_id";
    public static final String       JSON_TAG_CATEGORIES                                                 = "categories";
    public static final String       JSON_TAG_CATEGORIES_VERSION                                         = "version";
    public static final String       JSON_TAG_CATEGORIES_CURVERSION                                      = "curVersion";
    public static final String       JSON_TAG_CATEGORIES_ALIAS                                           = "alias";

    // JSON TAG FOR ERROR SIGNALEMENT
    public static final String       JSON_TAG_ERROR_ERROR                                                = "error";
    public static final String       JSON_TAG_ERROR_MESSAGE                                              = "error_message";

    // REQUEST TYPES
    public static final String       REQUEST_TYPE_INCIDENT_STATS                                         = "getIncidentStats";
    public static final String       REQUEST_TYPE_INCIDENTS_BY_ID                                        = "getIncidentById";
    public static final String       REQUEST_TYPE_INCIDENT_BY_POSITION                                   = "getIncidentsByPosition";
    public static final String       REQUEST_TYPE_SAVE_INCIDENT                                          = "saveIncident";
    public static final String       REQUEST_TYPE_REPORTS                                                = "getReports";
    public static final String       REQUEST_TYPE_UPDATE_INCIDENT                                        = "updateIncident";
    public static final String       REQUEST_TYPE_USER_ACTIVITIES                                        = "getUsersActivities";
    public static final String       REQUEST_TYPE_CHANGE_INCIDENT                                        = "changeIncident";
    public static final String       REQUEST_TYPE_INCIDENT_PHOTOS                                        = "getIncidentPhotos";
    public static final String       REQUEST_TYPE_CATEGORIES_LIST                                        = "getCategories";
    public static final String       REQUEST_TYPE_CHANGE_STATUS                                          = "changeStatus";
    public static final String       REQUEST_TYPE_ADD_ANOMALIE                                           = "addAnomalie";
    public static final String       REQUEST_TYPE_CONGRATULATE_ANOMALIE                                  = "congratulateAnomalie";
    public static final String       REQUEST_TYPE_SUBSCRIBE_ANOMALIE                                     = "subscribeToAnomalie";
    public static final String       REQUEST_TYPE_INCIDENT_RESOLVED                                      = "incidentResolved";
    public static final String       REQUEST_TYPE_FOLLOW                                                 = "follow";
    public static final String       REQUEST_TYPE_UNFOLLOW                                               = "unfollow";
    public static final String       REQUEST_TYPE_GET_INCIDENTS_BY_USER                                  = "getIncidentsByUser";
    public static final String       REQUEST_TYPE_PROCESS_WORKFLOW                                       = "processWorkflow";

    // UPDATING AN INCIDENT
    public static final String       UPDATE_STATUS_RESOLVED                                              = "Resolved";
    public static final String       UPDATE_STATUS_CONFIRMED                                             = "Confirmed";
    public static final String       UPDATE_STATUS_INVALID                                               = "Invalid";

    // PARAMETERS
    public static final String       PARAMETERS_JSON_STREAM                                              = "jsonStream";
    public static final String       PARAMETER_LATITUDE                                                  = "latitude";
    public static final String       PARAMETER_LONGITUDE                                                 = "longitude";
    public static final String       PARAMETER_RADIUS                                                    = "radius";
    public static final String       PARAMETER_TYPE_SIGNALEMENT_BEAN                                     = "typeSignalementService";
    public static final String       PARAMETER_SIGNALEMENT_SERVICE_BEAN                                  = "signalementService";
    public static final String       PARAMETER_SIGNALEMENT_WORKFLOW_BEAN                                 = "signalement.workflowService";
    public static final String       PARAMETER_PHOTO_SERVICE_BEAN                                        = "photoService";
    public static final String       PARAMETER_ADRESSE_SERVICE_BEAN                                      = "adresseSignalementService";

    // HTTP headers names
    public static final String       PARAMETERS_HEADER_UDID                                              = "udid";
    public static final String       PARAMETERS_HEADER_AUTHENTTOKEN                                      = "authentToken";
    public static final String       PARAMETERS_HEADER_IMG_COMMENT                                       = "img_comment";
    public static final String       PARAMETERS_HEADER_INCIDENT_CREATION                                 = "incident_creation";
    public static final String       PARAMETERS_HEADER_INCIDENT_ID                                       = "incident_id";
    public static final String       PARAMETERS_HEADER_TYPE                                              = "type";

    // PROPERTIES
    public static final String       PROPERTY_MULTIPART_SIZE_THRESHOLD                                   = "signalement-rest.multipart.sizeThreshold";
    public static final String       PROPERTY_MULTIPART_NORMALIZE_FILE_NAME                              = "signalement-rest.multipart.activateNormalizeFileName";
    public static final String       PROPERTY_MULTIPART_REQUEST_SIZE_MAX                                 = "signalement-rest.multipart.requestSizeMax";
    public static final String       PROPERTY_URL_PICTURE                                                = "signalement-rest.url_picture";
    public static final String       PROPERTY_PRIVATE_KEY_ANDROID_API                                    = "signalement-rest.private.key.android_api";
    public static final String       PROPERTY_ACTIVATION_SIGNREQUEST                                     = "signalement-rest.signrequest.activation";
    public static final String       PROPERTY_RADIUS                                                     = "signalement-rest.radius.parameter";
    public static final String       PROPERTY_URL_RAMEN_REST                                             = "signalement-rest.url.ramen.rest";
    public static final String       PROPERTY_URL_RAMEN_REST_GEO_WITH_LIMIT                              = "signalement-rest.url.ramen.rest.geo.withLimit";
    public static final String       PROPERTY_DEFAULT_EMAIL                                              = "signalement-rest.default.email";
    public static final String       PROPERTY_LIST_DOMAIN_EMAIL                                          = "signalement-rest.list.domain";
    public static final String       PROPERTY_INCIDENT_SOURCE_DMR                                        = "signalement-rest.source.dansmarue";
    public static final String       PROPERTY_INCIDENT_SOURCE_RAMEN                                      = "signalement-rest.source.ramen";

    // MARK
    public static final String       MARK_BASE_URL                                                       = "base_url";

    // TEMPLATES
    public static final String       TEMPLATE_WADL                                                       = "admin/plugins/signalement/modules/rest/wadl.xml";

    // HTTP Headers
    public static final String       HEADER_X_APP_VERSION                                                = "x-app-version";
    public static final String       HEADER_X_APP_PLATFORM                                               = "x-app-platform";
    public static final String       HEADER_X_APP_DEVICE_MODEL                                           = "x-app-device-model";
    public static final String       HEADER_X_APP_REQUEST_SIGNATURE                                      = "x-app-request-signature";

    // ERROR CODES
    public static final int          ERROR_EMPTY_JSON_REQUEST                                            = 1;
    public static final int          ERROR_BAD_JSON_REQUEST                                              = 2;
    public static final int          ERROR_BAD_REQUEST_SUB_ELEMENT                                       = 3;
    public static final int          ERROR_EMPTY_DEVICE_ID                                               = 4;
    public static final int          ERROR_BAD_DEVICE_ID                                                 = 5;
    public static final int          ERROR_EMPTY_POSITION_PARAMETER                                      = 6;
    public static final int          ERROR_BAD_POSITION_PARAMETER                                        = 7;
    public static final int          ERROR_BAD_CATEGORY_ID_PARAMETER                                     = 8;
    public static final int          ERROR_EMPTY_CATEGORY_ID_PARAMETER                                   = 9;
    public static final int          ERROR_BAD_ADDRESS_PARAMETER                                         = 90;
    public static final int          ERROR_ANY_INCIDENT_FOR_USER                                         = 10;
    public static final int          ERROR_EMPTY_INCIDENT_ID                                             = 11;
    public static final int          ERROR_BAD_INCIDENT_ID                                               = 12;
    public static final int          ERROR_BAD_USER_ID                                                   = 13;
    public static final int          ERROR_EMPTY_USER_ID                                                 = 14;
    public static final int          ERROR_EMPTY_INCIDENT_ID_BIS                                         = 15;
    public static final int          ERROR_BAD_INCIDENT_ID_BIS                                           = 16;
    public static final int          ERROR_ALREADY_RESOLVED                                              = 17;
    public static final int          ERROR_ALREADY_CONFIRM                                               = 18;
    public static final int          ERROR_ALREADY_INVALID                                               = 19;
    public static final int          ERROR_BAD_PICTURE                                                   = 29;
    public static final int          ERROR_IMPOSSIBLE_READ_PICTURE                                       = 30;
    public static final int          ERROR_API_REST                                                      = 32;
    public static final int          ERROR_SIGN_RESTQUEST                                                = 33;
    public static final int          ERROR_BAD_ORIGIN                                                    = 34;
    public static final int          ERROR_BAD_SOURCE                                                    = 35;
    public static final int          ERROR_BAD_OWNER                                                     = 36;
    public static final int          ERROR_BAD_USER_EMAIL                                                = 37;
    public static final int          ERROR_BAD_DEVICE                                                    = 38;
    public static final int          ERROR_BAD_ACTION_TAG                                                = 39;
    public static final int          ERROR_BAD_USER_TOKEN                                                = 40;
    public static final int          ERROR_ALREADY_FOLLOWED                                              = 41;
    public static final int          ERROR_INVALID_STATE_ACTION                                          = 42;
    public static final int          ERROR_NON_EXISTENT_FOLLOW_ITEM                                      = 43;
    public static final int          ERROR_NO_WORKFLOW_AVAILABLE                                         = 44;
    public static final int          ERROR_NO_WORKFLOW_SELECTED                                          = 45;
    public static final int          ERROR_GET_ALL_SOUS_TYPE_SIGNALEMENT_CASCADE                         = 46;
    public static final int          ERROR_GET_SIGNALEMENT_BY_ID                                         = 47;
    public static final int          ERROR_GET_GEOM_FROM_LAMBERT_TO_WQ84                                 = 48;
    public static final int          ERROR_GET_DISTANCE_BETWEEN_SIGNALEMENT                              = 49;
    public static final int          ERROR_FIND_ALL_SIGNALLEMENT_IN_PERIMETER_WITH_DTO                   = 50;
    public static final int          ERROR_IS_SIGNALEMENT_FOLLOWABLE_AND_IS_SIGNALEMENT_FOLLOWED_BY_USER = 51;
    public static final int          ERROR_GET_ALL_PRIORITE                                              = 56;
    public static final int          ERROR_LOAD_PRIORITE_BY_ID                                           = 57;
    public static final int          ERROR_GET_ARRONDISSEMENT_BY_GEOM                                    = 58;
    public static final int          ERROR_ADD_FOLLOWER                                                  = 59;
    public static final int          ERROR_SAVE_SIGNALEMENT                                              = 60;
    public static final int          ERROR_WORKFLOW_ACTION                                               = 61;
    public static final int          ERROR_GET_SIGNALEMENT_BY_TOKEN                                      = 65;
    public static final int          ERROR_GET_HISTORY_SIGNALEMENT                                       = 66;

    // CONSTANTS PICTURE
    public static final Integer      VUE_ENSEMBLE                                                        = 1;
    public static final Integer      VUE_PRES                                                            = 0;
    public static final Integer      VUE_SERVICE_FAIT                                                    = 2;
    public static final String       PICTURE_FAR                                                         = "far";
    public static final String       PICTURE_CLOSE                                                       = "close";
    public static final String       PICTURE_DONE                                                        = "done";

    // CONSTANTS ACTION
    public static final Integer      ACTION_TRANSFERED_STATE_WEBSERVICE_ACCEPTED                         = 62;
    public static final Integer      ACTION_TRANSFERED_STATE_WEBSERVICE_REJECTED                         = 64;
    public static final Integer      ACTION_TRANSFERED_STATE_WEBSERVICE_PROGRAMMED                       = 68;
    public static final Integer      ACTION_TRANSFERED_STATE_WEBSERVICE_REQUALIFIED                      = 78;
    public static final Integer      ACTION_TRANSFERED_STATE_WEBSERVICE_A_REQUALIFIED                    = 63;
    public static final Integer      ACTION_PROGRAMMED_STATE_WEBSERVICE_ACCEPTED                         = 70;
    public static final Integer      ACTION_PROGRAMMED_STATE_WEBSERVICE_REJECTED                         = 71;
    public static final Integer      ACTION_PROGRAMMED_STATE_WEBSERVICE_PROGRAMMED                       = 72;
    public static final Integer      ACTION_PROGRAMMED_STATE_WEBSERVICE_REQUALIFIED                      = 79;
    public static final Integer      ACTION_PROGRAMMED_STATE_WEBSERVICE_A_REQUALIFIED                    = 73;

    // CONSTANTS STATE
    public static final Integer      TRANFERED_PROVIDER_STATUS_ID                                        = 18;
    public static final Integer      PROGRAMMED_STATUS_ID                                                = 21;

    // I18N MESSAGES
    public static final String       ERROR_MESSAGE_NO_ANOMALY_FOUND                                      = "module.dansmarue.rest.webservice_listener.error.no_anomaly_found";
    public static final String       ERROR_MESSAGE_NO_TOKEN_FOUND                                        = "module.dansmarue.rest.webservice_listener.error.no_token_found";
    public static final String       ERROR_MESSAGE_DIFFERENT_STATUS                                      = "module.dansmarue.rest.webservice_listener.error.different_status";
    public static final String       ERROR_MESSAGE_DONE_WRONG_STATUS                                     = "module.dansmarue.rest.webservice_listener.error.done.wrong_status";
    public static final String       ERROR_MESSAGE_REJECTED_WRONG_STATUS                                 = "module.dansmarue.rest.webservice_listener.error.rejected.wrong_status";
    public static final String       ERROR_MESSAGE_REJECTED_WRONG_REJECT_CAUSE_ID                        = "module.dansmarue.rest.webservice_listener.error.rejected.wrong_reject_cause_id";
    public static final String       ERROR_MESSAGE_REQUALIFIED_WRONG_TYPE_ANOMALIE                       = "module.dansmarue.rest.webservice_listener.error.requalified.wrong_type_anomalie";
    public static final String       ERROR_MESSAGE_PROGRAMMED_WRONG_STATUS                               = "module.dansmarue.rest.webservice_listener.error.programmed.wrong_status";
    public static final String       ERROR_MESSAGE_REQUALIFIED_WRONG_STATUS                              = "module.dansmarue.rest.webservice_listener.error.requalified.wrong_status";
    public static final String       ERROR_MESSAGE_ACTION_WRONG_DATE_FORMAT                              = "module.dansmarue.rest.webservice_listener.error.action.wrong_date_format";
    public static final String       ERROR_MESSAGE_PROGRAMMED_WRONG_DATE_FORMAT                          = "module.dansmarue.rest.webservice_listener.error.programmed.wrong_date_format";
    public static final String       ERROR_MESSAGE_WRONG_FORMAT                                          = "module.dansmarue.rest.webservice_listener.error.wrong_format";
    public static final String       ERROR_MESSAGE_PROGRAMMED_DATE_BEFORE_TODAY                          = "module.dansmarue.rest.webservice_listener.error.programmed.date.before.today";

    // STATUS
    public static final String       PROPERTY_ID_STATE_SERVICE_FAIT                                      = "signalement.idStateServiceFait";

    /**
     * Constructor
     */
    private SignalementRestConstants( )
    {
    }
}
