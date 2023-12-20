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
package fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants;

import java.util.List;

import fr.paris.lutece.plugins.dansmarue.utils.ISignalementUtils;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * SignalementRestConstants.
 */
public final class SignalementRestConstants
{

    /** The Constant PATH_WADL. */
    // CONSTANTS
    public static final String PATH_WADL = "wadl";

    /** The Constant PATH_API. */
    public static final String PATH_API = "api";

    /** The Constant PATH_TEST_GET. */
    public static final String PATH_TEST_GET = "test_get";

    /** The Constant PATH_PHOTO. */
    public static final String PATH_PHOTO = "photo";

    /** The Constant PATH_CHANGE_STATUS. */
    public static final String PATH_CHANGE_STATUS = "changeStatus";

    /** The Constant PATH_BLACK_BERRY. */
    public static final String PATH_BLACK_BERRY = "bb";

    /** The Constant PATH_BLACK_BERRY_CAT. */
    public static final String PATH_BLACK_BERRY_CAT = "bb_cat";

    /** The Constant PATH_BLACK_BERRY_PICTURE. */
    public static final String PATH_BLACK_BERRY_PICTURE = "bb_picture";

    /** The Constant PATH_SIGNALEMENT_A_ARCHIVER_REJETE. */
    public static final String PATH_SIGNALEMENT_A_ARCHIVER_REJETE = "signalementAArchiverRejete";

    /** The Constant PATH_SIGNALEMENT_A_ARCHIVER_SERVICE_FAIT. */
    public static final String PATH_SIGNALEMENT_A_ARCHIVER_SERVICE_FAIT = "signalementAArchiverServiceFait";

    /** The Constant SLASH. */
    public static final String SLASH = "/";

    /** The Constant EQUAL. */
    public static final String EQUAL = "=";

    /** The Constant AMPERSAND. */
    public static final String AMPERSAND = "&";

    /** The Constant QUESTION_MARK. */
    public static final String QUESTION_MARK = "?";

    /** The Constant SIGNALEMENT_PREFIX_IOS. */
    public static final String SIGNALEMENT_PREFIX_IOS = AppPropertiesService.getProperty( "signalement.prefix.origin.ios" );

    /** The Constant SIGNALEMENT_PREFIX_ANDROID. */
    public static final String SIGNALEMENT_PREFIX_ANDROID = AppPropertiesService.getProperty( "signalement.prefix.origin.android" );

    /** The Constant SIGNALEMENT_PREFIX_TELESERVICE. */
    public static final String SIGNALEMENT_PREFIX_TELESERVICE = AppPropertiesService.getProperty( "signalement.prefix.origin.android" );

    /** The Constant SIGNALEMENT_PREFIX_KEY. */
    public static final String SIGNALEMENT_PREFIX_KEY = "signalement.prefix.origin";

    /** The Constant SIGNALEMENT_PREFIXES. */
    public static final List<String> SIGNALEMENT_PREFIXES = ( ( ISignalementUtils ) SpringContextService.getBean( "signalement.signalementUtils" ) ).getProperties( SIGNALEMENT_PREFIX_KEY );

    /** The Constant MOBILE_STATE_RESOLVED. */
    public static final String MOBILE_STATE_RESOLVED = "R";

    /** The Constant JSON_TAG_REQUEST. */
    // JSON TAG
    public static final String JSON_TAG_REQUEST = "request";

    /** The Constant JSON_TAG_ANSWER. */
    public static final String JSON_TAG_ANSWER = "answer";

    /** The Constant JSON_TAG_STATUS. */
    public static final String JSON_TAG_STATUS = "status";

    /** The Constant JSON_TAG_REFERENCE. */
    public static final String JSON_TAG_REFERENCE = "reference";

    /** The Constant JSON_TAG_TOKEN. */
    public static final String JSON_TAG_TOKEN = "token";

    /** The Constant JSON_TAG_CLOSEST_INCIDENTS. */
    public static final String JSON_TAG_CLOSEST_INCIDENTS = "closest_incidents";

    /** The Constant JSON_TAG_INCIDENT. */
    public static final String JSON_TAG_INCIDENT = "incident";

    /** The Constant JSON_TAG_INCIDENTS. */
    public static final String JSON_TAG_INCIDENTS = "incidents";

    /** The Constant JSON_TAG_ID_FDT_SERCH. */
    public static final String JSON_TAG_ID_FDT_SERCH = "idFDTSearch";

    /** The Constant JSON_TAG_INFO_AVANT_TOURNEE. */
    public static final String JSON_TAG_INFO_AVANT_TOURNEE = "infosAvantTournee";

    /** The Constant JSON_TAG_INFO_APRES_TOURNEE. */
    public static final String JSON_TAG_INFO_APRES_TOURNEE = "infosApresTournee";

    /** The Constant JSON_TAG_POSITION. */
    public static final String JSON_TAG_POSITION = "position";

    /** The Constant JSON_TAG_LATITUDE. */
    public static final String JSON_TAG_LATITUDE = "latitude";

    /** The Constant JSON_TAG_COMMENTAIRE_AGENT_TERRAIN. */
    public static final String JSON_TAG_COMMENTAIRE_AGENT_TERRAIN = "commentaireAgent";

    /** The Constant JSON_TAG_LONGITUDE. */
    public static final String JSON_TAG_LONGITUDE = "longitude";

    /** The Constant JSON_TAG_INCIDENT_LOG. */
    public static final String JSON_TAG_INCIDENT_LOG = "incidentLog";

    /** The Constant JSON_TAG_RESOLVED_INCIDENTS. */
    public static final String JSON_TAG_RESOLVED_INCIDENTS = "resolved_incidents";

    /** The Constant JSON_TAG_ONGOING_INCIDENTS. */
    public static final String JSON_TAG_ONGOING_INCIDENTS = "ongoing_incidents";

    /** The Constant JSON_TAG_UPDATED_INCIDENTS. */
    public static final String JSON_TAG_UPDATED_INCIDENTS = "updated_incidents";

    /** The Constant JSON_TAG_DECLARED_INCIDENTS. */
    public static final String JSON_TAG_DECLARED_INCIDENTS = "declared_incidents";

    /** The Constant JSON_TAG_PHOTOS. */
    public static final String JSON_TAG_PHOTOS = "photos";

    /** The Constant JSON_TAG_PHOTO_URL. */
    public static final String JSON_TAG_PHOTO_URL = "photo_url";

    /** The Constant JSON_TAG_NUMERO_MESSAGE. */
    public static final String JSON_TAG_NUMERO_MESSAGE = "numero_message";

    /** The Constant JSON_TAG_MESSAGE. */
    public static final String JSON_TAG_MESSAGE = "message";

    /** The Constant JSON_TAG_MESSAGES. */
    public static final String JSON_TAG_MESSAGES = "messages";

    /** The Constant JSON_TAG_COMMENTARY. */
    public static final String JSON_TAG_COMMENTARY = "commentary";

    /** The Constant JSON_TAG_UDID. */
    public static final String JSON_TAG_UDID = "udid";

    /** The Constant JSON_TAG_EMAIL. */
    public static final String JSON_TAG_EMAIL = "email";

    /** The Constant JSON_TAG_GUID. */
    public static final String JSON_TAG_GUID = "guid";

    /** The Constant JSON_TAG_SEARCH_BY_NUMBER. */
    public static final String JSON_TAG_SEARCH_BY_NUMBER = "searchByNumber";

    /** The Constant JSON_TAG_ANOMALY_DONE. */
    public static final String JSON_TAG_ANOMALY_DONE = "service fait";

    /** The Constant JSON_TAG_ANOMALY_REJECTED. */
    public static final String JSON_TAG_ANOMALY_REJECTED = "rejeter";

    /** The Constant JSON_TAG_ANOMALY_PROGRAMMED. */
    public static final String JSON_TAG_ANOMALY_PROGRAMMED = "programmer";

    /** The Constant JSON_TAG_ANOMALY_REQUALIFIED. */
    public static final String JSON_TAG_ANOMALY_REQUALIFIED = "requalifier";

    /** The Constant JSON_TAG_ANOMALY_A_REQUALIFIED. */
    public static final String JSON_TAG_ANOMALY_A_REQUALIFIED = "a requalifier";

    /** The Constant JSON_TAG_DEVICE. */
    public static final String JSON_TAG_DEVICE = "device";

    /** The Constant JSON_TAG_USER_TOKEN. */
    public static final String JSON_TAG_USER_TOKEN = "userToken";

    /** The Constant JSON_TAG_USER. */
    public static final String JSON_TAG_USER = "user";

    /** The Constant JSON_TAG_RESOLVED_AUTHORIZATION. */
    public static final String JSON_TAG_RESOLVED_AUTHORIZATION = "resolved_authorization";

    /** The Constant JSON_TAG_NUMERO_MESSAGE_SERVICE_FAIT. */
    public static final String JSON_TAG_NUMERO_MESSAGE_SERVICE_FAIT = "numeroMessage";

    /** The Constant JSON_TAG_TYPE_MESSAGE_SERVICE_FAIT. */
    public static final String JSON_TAG_TYPE_MESSAGE_SERVICE_FAIT = "isGeneric";

    /** The Constant JSON_TAG_FILTER_INCIDENT_STATUS. */
    public static final String JSON_TAG_FILTER_INCIDENT_STATUS = "filterIncidentStatus";

    /** The Constant JSON_TAG_NAME. */
    // JSON TAG FOR MON COMPTE
    public static final String JSON_TAG_NAME = "name";

    /** The Constant JSON_TAG_FIRSTNAME. */
    public static final String JSON_TAG_FIRSTNAME = "firstname";

    /** The Constant JSON_TAG_MAIL. */
    public static final String JSON_TAG_MAIL = "mail";

    /** The Constant JSON_TAG_MESSAGE_TRANSFERE. */
    // Pour le choix des messages prestataires
    public static final String JSON_TAG_MESSAGE_TRANSFERE = "message si état transféré à un tiers";

    /** The Constant JSON_TAG_MESSAGE_PROGRAMME. */
    public static final String JSON_TAG_MESSAGE_PROGRAMME = "message si état service programmé tiers";

    /** The Constant JSON_TAG_INCIDENT_ID. */
    // JSON TAG FOR INCIDENT OBJECT
    public static final String JSON_TAG_INCIDENT_ID = "id";

    /** The Constant JSON_TAG_SIGNALEMENT_ID_N. */
    public static final String JSON_TAG_SIGNALEMENT_ID_N = "nIdSignalement";

    /** The Constant JSON_TAG_SIGNALEMENT_ID. */
    public static final String JSON_TAG_SIGNALEMENT_ID = "idSignalement";

    /** The Constant JSON_TAG_FDT_ID. */
    public static final String JSON_TAG_FDT_ID = "idFDT";

    /** The Constant JSON_TAG_INCIDENT_CATEGORIE_ID. */
    public static final String JSON_TAG_INCIDENT_CATEGORIE_ID = "categoryId";

    /** The Constant JSON_TAG_INCIDENT_STATE. */
    public static final String JSON_TAG_INCIDENT_STATE = "state";

    /** The Constant JSON_TAG_INCIDENT_STATE_ID. */
    public static final String JSON_TAG_INCIDENT_STATE_ID = "stateId";

    /** The Constant JSON_TAG_INCIDENT_STATE_NAME. */
    public static final String JSON_TAG_INCIDENT_STATE_NAME = "stateName";

    /** The Constant JSON_TAG_INCIDENT_ADDRESS. */
    public static final String JSON_TAG_INCIDENT_ADDRESS = "address";

    /** The Constant JSON_TAG_INCIDENT_DESCRIPTIVE. */
    public static final String JSON_TAG_INCIDENT_DESCRIPTIVE = "descriptive";

    /** The Constant JSON_TAG_INCIDENT_COMMENTAIRE_AGENT. */
    public static final String JSON_TAG_INCIDENT_COMMENTAIRE_AGENT = "commentaireAgent";

    /** The Constant JSON_TAG_INCIDENT_PRECISION_TERRAIN. */
    public static final String JSON_TAG_INCIDENT_PRECISION_TERRAIN = "precisionsTerrain";

    /** The Constant JSON_TAG_FDT_INFO_APRES_TOURNEE. */
    public static final String JSON_TAG_FDT_INFO_APRES_TOURNEE = "infosApresTournee";

    /** The Constant JSON_TAG_INCIDENT_PRIORITE_ID. */
    public static final String JSON_TAG_INCIDENT_PRIORITE_ID = "priorityId";

    /** The Constant JSON_TAG_INCIDENT_DATE. */
    public static final String JSON_TAG_INCIDENT_DATE = "date";

    /** The Constant JSON_TAG_INCIDENT_HOUR. */
    public static final String JSON_TAG_INCIDENT_HOUR = "hour";

    /** The Constant JSON_TAG_INCIDENT_LAT. */
    public static final String JSON_TAG_INCIDENT_LAT = "lat";

    /** The Constant JSON_TAG_INCIDENT_LNG. */
    public static final String JSON_TAG_INCIDENT_LNG = "lng";

    /** The Constant JSON_TAG_INCIDENT_PICTURES. */
    public static final String JSON_TAG_INCIDENT_PICTURES = "pictures";

    /** The Constant JSON_TAG_INCIDENT_CLOSE. */
    public static final String JSON_TAG_INCIDENT_CLOSE = "close";

    /** The Constant JSON_TAG_INCIDENT_FAR. */
    public static final String JSON_TAG_INCIDENT_FAR = "far";

    /** The Constant JSON_TAG_INCIDENT_DONE. */
    public static final String JSON_TAG_INCIDENT_DONE = "done";

    /** The Constant JSON_TAG_INCIDENT_CONFIRMS. */
    public static final String JSON_TAG_INCIDENT_CONFIRMS = "confirms";

    /** The Constant JSON_TAG_INCIDENT_PRIORITY. */
    public static final String JSON_TAG_INCIDENT_PRIORITY = "priorityId";

    /** The Constant JSON_TAG_INCIDENT_INVALIDATIONS. */
    public static final String JSON_TAG_INCIDENT_INVALIDATIONS = "invalidations";

    /** The Constant JSON_TAG_INCIDENT_COMMENT. */
    public static final String JSON_TAG_INCIDENT_COMMENT = "comment";

    /** The Constant JSON_TAG_INCIDENT_CHOSEN_MESSAGE. */
    public static final String JSON_TAG_INCIDENT_CHOSEN_MESSAGE = "chosenMessage";

    /** The Constant JSON_TAG_INCIDENT_DATE_REEL_ACTION. */
    public static final String JSON_TAG_INCIDENT_DATE_REEL_ACTION = "action_date";

    /** The Constant JSON_TAG_INCIDENT_ID_REJET. */
    public static final String JSON_TAG_INCIDENT_ID_REJET = "rejection_reason";

    /** The Constant JSON_TAG_INCIDENT_DATE_PROGRAMMATION. */
    public static final String JSON_TAG_INCIDENT_DATE_PROGRAMMATION = "programming_date";

    /** The Constant JSON_TAG_INCIDENT_TYPE_ANOMALIE. */
    public static final String JSON_TAG_INCIDENT_TYPE_ANOMALIE = "id_type_anomalie";

    /** The Constant JSON_TAG_INCIDENT_PHOTO. */
    public static final String JSON_TAG_INCIDENT_PHOTO = "photo";

    /** The Constant JSON_TAG_INCIDENT_CONGRATULATIONS. */
    public static final String JSON_TAG_INCIDENT_CONGRATULATIONS = "congratulations";

    /** The Constant JSON_TAG_INCIDENT_ALIAS. */
    public static final String JSON_TAG_INCIDENT_ALIAS = "alias";

    /** The Constant JSON_TAG_INCIDENT_ORIGIN. */
    public static final String JSON_TAG_INCIDENT_ORIGIN = "origin";

    /** The Constant JSON_TAG_INCIDENT_SOURCE. */
    public static final String JSON_TAG_INCIDENT_SOURCE = "source";

    /** The Constant JSON_TAG_INCIDENT_NUEMERO. */
    public static final String JSON_TAG_INCIDENT_NUEMERO = "numero";

    /** The Constant JSON_TAG_INCIDENT_TOKEN. */
    public static final String JSON_TAG_INCIDENT_TOKEN = "token";

    /** The Constant JSON_TAG_INCIDENT_REPORTER_GUID. */
    public static final String JSON_TAG_INCIDENT_REPORTER_GUID = "reporterGuid";

    /** The Constant JSON_TAG_INCIDENT_FOLLOWERS. */
    public static final String JSON_TAG_INCIDENT_FOLLOWERS = "followers";

    /** The Constant JSON_TAG_IS_INCIDENT_FOLLOWED_BY_USER. */
    public static final String JSON_TAG_IS_INCIDENT_FOLLOWED_BY_USER = "isIncidentFollowedByUser";

    /** The Constant JSON_TAG_IS_INCIDENT_ANONYME. */
    public static final String JSON_TAG_IS_INCIDENT_ANONYME = "isIncidentAnonyme";

    /** The Constant JSON_TAG_MESSAGE_SF_GENERIC. */
    public static final String JSON_TAG_MESSAGE_SF_GENERIC = "messages_sf_generic";

    /** The Constant JSON_TAG_MESSAGE_SF_TYPOLOGIE. */
    public static final String JSON_TAG_MESSAGE_SF_TYPOLOGIE = "messages_sf_typologie";

    /** The Constant JSON_TAG_LOG_INCIDENT_ID. */
    // JSON TAG FOR LOG
    public static final String JSON_TAG_LOG_INCIDENT_ID = "incidentId";

    /** The Constant JSON_TAG_LOG_UDID. */
    public static final String JSON_TAG_LOG_UDID = "udid";

    /** The Constant JSON_TAG_LOG_DATE. */
    public static final String JSON_TAG_LOG_DATE = "date";

    /** The Constant JSON_TAG_LOG_STATUS. */
    public static final String JSON_TAG_LOG_STATUS = "status";

    /** The Constant JSON_TAG_CATEGORIES_CHILDREN_ID. */
    // JSON TAG FOR CATEGORIES
    public static final String JSON_TAG_CATEGORIES_CHILDREN_ID = "children_id";

    /** The Constant JSON_TAG_CATEGORIES_NAME. */
    public static final String JSON_TAG_CATEGORIES_NAME = "name";

    /**
     * The Constant JSON_TAG_CATEGORIES_PARENT_ID.
     */
    public static final String JSON_TAG_CATEGORIES_PARENT_ID = "parent_id";

    /**
     * The Constant JSON_TAG_CATEGORIES.
     */
    public static final String JSON_TAG_CATEGORIES = "categories";

    /**
     * The Constant JSON_TAG_ACTUALITE.
     */
    public static final String JSON_TAG_ACTUALITE = "actualites";

    public static final String JSON_TAG_AIDES = "aides";

    /**
     * The Constant JSON_TAG_CATEGORIES_VERSION.
     */
    public static final String JSON_TAG_CATEGORIES_VERSION = "version";

    /**
     * The Constant JSON_TAG_CATEGORIES_CURVERSION.
     */
    public static final String JSON_TAG_CATEGORIES_CURVERSION = "curVersion";

    /**
     * The Constant JSON_TAG_CATEGORIES_CURVERSION_MOBILE_PROD.
     */
    public static final String JSON_TAG_CATEGORIES_CURVERSION_MOBILE_PROD = "curVersionMobileProd";

    /** The Constant JSON_TAG_CATEGORIES_ALIAS. */
    public static final String JSON_TAG_CATEGORIES_ALIAS = "alias";

    /** The Constant JSON_TAG_IS_AGENT. */
    public static final String JSON_TAG_IS_AGENT = "isAgent";

    /** The Constant JSON_TAG_HORS_DMR. */
    public static final String JSON_TAG_HORS_DMR = "horsDMR";

    /** The Constant JSON_TAG_MESAGE_HORS_DMR. */
    public static final String JSON_TAG_MESAGE_HORS_DMR = "messageHorsDMR";

    /** The Constant JSON_TAG_ERROR_ERROR. */
    // JSON TAG FOR ERROR SIGNALEMENT
    public static final String JSON_TAG_ERROR_ERROR = "error";

    /** The Constant JSON_TAG_ERROR_MESSAGE. */
    public static final String JSON_TAG_ERROR_MESSAGE = "error_message";

    /** The Constant REQUEST_TYPE_INCIDENT_STATS. */
    // REQUEST TYPES
    public static final String REQUEST_TYPE_INCIDENT_STATS = "getIncidentStats";

    /** The Constant REQUEST_TYPE_INCIDENTS_BY_ID. */
    public static final String REQUEST_TYPE_INCIDENTS_BY_ID = "getIncidentById";

    /** The Constant REQUEST_TYPE_INCIDENT_BY_POSITION. */
    public static final String REQUEST_TYPE_INCIDENT_BY_POSITION = "getIncidentsByPosition";

    /** The Constant REQUEST_SEARCH_INCIDENTS_BY_ID_FDT. */
    public static final String REQUEST_SEARCH_INCIDENTS_BY_ID_FDT = "searchIncidentsByIdFdt";

    /** The Constant REQUEST_TYPE_SAVE_INCIDENT. */
    public static final String REQUEST_TYPE_SAVE_INCIDENT = "saveIncident";

    /** The Constant REQUEST_TYPE_SAVE_PRECISIONS_TERRAIN. */
    public static final String REQUEST_TYPE_SAVE_PRECISIONS_TERRAIN = "savePrecisionsTerrain";

    /** The Constant REQUEST_TYPE_SAVE_INFO_APRES_TOURNEE. */
    public static final String REQUEST_TYPE_SAVE_INFO_APRES_TOURNEE = "saveInfosApresTournee";

    /** The Constant REQUEST_TYPE_REPORTS. */
    public static final String REQUEST_TYPE_REPORTS = "getReports";

    /** The Constant REQUEST_TYPE_UPDATE_INCIDENT. */
    public static final String REQUEST_TYPE_UPDATE_INCIDENT = "updateIncident";

    /** The Constant REQUEST_TYPE_USER_ACTIVITIES. */
    public static final String REQUEST_TYPE_USER_ACTIVITIES = "getUsersActivities";

    /** The Constant REQUEST_TYPE_CHANGE_INCIDENT. */
    public static final String REQUEST_TYPE_CHANGE_INCIDENT = "changeIncident";

    /**
     * The Constant REQUEST_TYPE_INCIDENT_PHOTOS.
     */
    public static final String REQUEST_TYPE_INCIDENT_PHOTOS = "getIncidentPhotos";

    /**
     * The Constant REQUEST_TYPE_CATEGORIES_LIST.
     */
    public static final String REQUEST_TYPE_CATEGORIES_LIST = "getCategories";

    /**
     * The Constant REQUEST_TYPE_NEWS_LIST.
     */
    public static final String REQUEST_TYPE_NEWS_LIST = "getActualite";

    public static final String REQUEST_AIDES_LIST = "getAides";

    /**
     * The Constant REQUEST_TYPE_CHANGE_STATUS.
     */
    public static final String REQUEST_TYPE_CHANGE_STATUS = "changeStatus";

    /**
     * The Constant REQUEST_TYPE_ADD_ANOMALIE.
     */
    public static final String REQUEST_TYPE_ADD_ANOMALIE = "addAnomalie";

    /**
     * The Constant REQUEST_TYPE_CONGRATULATE_ANOMALIE.
     */
    public static final String REQUEST_TYPE_CONGRATULATE_ANOMALIE = "congratulateAnomalie";

    /** The Constant REQUEST_TYPE_SUBSCRIBE_ANOMALIE. */
    public static final String REQUEST_TYPE_SUBSCRIBE_ANOMALIE = "subscribeToAnomalie";

    /** The Constant REQUEST_TYPE_INCIDENT_RESOLVED. */
    public static final String REQUEST_TYPE_INCIDENT_RESOLVED = "incidentResolved";

    /** The Constant REQUEST_TYPE_FOLLOW. */
    public static final String REQUEST_TYPE_FOLLOW = "follow";

    /** The Constant REQUEST_TYPE_UNFOLLOW. */
    public static final String REQUEST_TYPE_UNFOLLOW = "unfollow";

    /** The Constant REQUEST_TYPE_GET_INCIDENTS_BY_USER. */
    public static final String REQUEST_TYPE_GET_INCIDENTS_BY_USER = "getIncidentsByUser";

    /** The Constant REQUEST_TYPE_PROCESS_WORKFLOW. */
    public static final String REQUEST_TYPE_PROCESS_WORKFLOW = "processWorkflow";

    /** The Constant REQUEST_TYPE_CHECK_VERSION. */
    public static final String REQUEST_TYPE_CHECK_VERSION = "checkVersion";

    /** The Constant UPDATE_STATUS_RESOLVED. */
    // UPDATING AN INCIDENT
    public static final String UPDATE_STATUS_RESOLVED = "Resolved";

    /** The Constant UPDATE_STATUS_CONFIRMED. */
    public static final String UPDATE_STATUS_CONFIRMED = "Confirmed";

    /** The Constant UPDATE_STATUS_INVALID. */
    public static final String UPDATE_STATUS_INVALID = "Invalid";

    /** The Constant PARAMETERS_JSON_STREAM. */
    // PARAMETERS
    public static final String PARAMETERS_JSON_STREAM = "jsonStream";

    /** The Constant PARAMETERS_ID_SOURCE. */
    public static final String PARAMETERS_ID_SOURCE = "idSource";

    /** The Constant PARAMETER_LATITUDE. */
    public static final String PARAMETER_LATITUDE = "latitude";

    /** The Constant PARAMETER_LONGITUDE. */
    public static final String PARAMETER_LONGITUDE = "longitude";

    /** The Constant PARAMETER_RADIUS. */
    public static final String PARAMETER_RADIUS = "radius";

    /** The Constant PARAMETER_TYPE_SIGNALEMENT_BEAN. */
    public static final String PARAMETER_TYPE_SIGNALEMENT_BEAN = "typeSignalementService";

    /** The Constant PARAMETER_SIGNALEMENT_SERVICE_BEAN. */
    public static final String PARAMETER_SIGNALEMENT_SERVICE_BEAN = "signalementService";

    /** The Constant PARAMETER_SIGNALEMENT_WORKFLOW_BEAN. */
    public static final String PARAMETER_SIGNALEMENT_WORKFLOW_BEAN = "signalement.workflowService";

    /** The Constant PARAMETER_PHOTO_SERVICE_BEAN. */
    public static final String PARAMETER_PHOTO_SERVICE_BEAN = "photoService";

    /** The Constant PARAMETER_ADRESSE_SERVICE_BEAN. */
    public static final String PARAMETER_ADRESSE_SERVICE_BEAN = "adresseSignalementService";

    /** The Constant PARAMETER_FDT_SERVICE_BEAN. */
    public static final String PARAMETER_FDT_SERVICE_BEAN = "feuilleDeTourneeService";

    /** The Constant PARAMETER_SIGNALEMENT_EXPORT_SERVICE_BEAN. */
    public static final String PARAMETER_SIGNALEMENT_EXPORT_SERVICE_BEAN = "signalementExportService";

    /** The Constant PARAMETER_PHOTO_SERVICE_BEAN. */
    public static final String PARAMETER_ACTUALITE_BEAN = "actualiteService";

    /** The Constant PARAMETER_ADRESSE_SERVICE_BEAN. */
    public static final String PARAMETER_AIDE_BEAN = "aideService";

    /** The Constant PARAMETERS_VERSION_ACTUALITE. */
    public static final String PARAMETERS_VERSION_ACTUALITE = "versionActualite";

    /** The Constant PARAMETERS_VERSION_AIDE. */
    public static final String PARAMETERS_VERSION_AIDE = "versionAide";

    public static final String PARAMETER_NUMBER = "number";

    public static final String PARAMETER_GUID = "guid";

    /** The Constant PARAMETERS_HEADER_UDID. */
    // HTTP headers names
    public static final String PARAMETERS_HEADER_UDID = "udid";

    /** The Constant PARAMETERS_HEADER_AUTHENTTOKEN. */
    public static final String PARAMETERS_HEADER_AUTHENTTOKEN = "authentToken";

    /** The Constant PARAMETERS_HEADER_INCIDENT_ID. */
    public static final String PARAMETERS_HEADER_INCIDENT_ID = "incident-id";

    /** The Constant PARAMETERS_HEADER_TYPE. */
    public static final String PARAMETERS_HEADER_TYPE = "type";

    /** The Constant PROPERTY_MULTIPART_SIZE_THRESHOLD. */
    // PROPERTIES
    public static final String PROPERTY_MULTIPART_SIZE_THRESHOLD = "signalement-rest.multipart.sizeThreshold";

    /** The Constant PROPERTY_MULTIPART_NORMALIZE_FILE_NAME. */
    public static final String PROPERTY_MULTIPART_NORMALIZE_FILE_NAME = "signalement-rest.multipart.activateNormalizeFileName";

    /** The Constant PROPERTY_MULTIPART_REQUEST_SIZE_MAX. */
    public static final String PROPERTY_MULTIPART_REQUEST_SIZE_MAX = "signalement-rest.multipart.requestSizeMax";

    /** The Constant PROPERTY_URL_PICTURE. */
    public static final String PROPERTY_URL_PICTURE = "signalement-rest.url_picture";

    /** The Constant PROPERTY_PRIVATE_KEY_ANDROID_API. */
    public static final String PROPERTY_PRIVATE_KEY_ANDROID_API = "signalement-rest.private.key.android_api";

    /** The Constant PROPERTY_ACTIVATION_SIGNREQUEST. */
    public static final String PROPERTY_ACTIVATION_SIGNREQUEST = "signalement-rest.signrequest.activation";

    /** The Constant PROPERTY_RADIUS. */
    public static final String PROPERTY_RADIUS = "signalement-rest.radius.parameter";

    /** The Constant PROPERTY_URL_RAMEN_REST. */
    public static final String PROPERTY_URL_RAMEN_REST = "signalement-rest.url.ramen.rest";

    /** The Constant PROPERTY_URL_RAMEN_REST_GEO_WITH_LIMIT. */
    public static final String PROPERTY_URL_RAMEN_REST_GEO_WITH_LIMIT = "signalement-rest.url.ramen.rest.geo.withLimit";

    /** The Constant PROPERTY_DEFAULT_EMAIL. */
    public static final String PROPERTY_DEFAULT_EMAIL = "signalement-rest.default.email";

    /** The Constant PROPERTY_LIST_DOMAIN_EMAIL. */
    public static final String PROPERTY_LIST_DOMAIN_EMAIL = "signalement-rest.list.domain";

    /** The Constant PROPERTY_INCIDENT_SOURCE_DMR. */
    public static final String PROPERTY_INCIDENT_SOURCE_DMR = "signalement-rest.source.dansmarue";

    /** The Constant PROPERTY_INCIDENT_SOURCE_RAMEN. */
    public static final String PROPERTY_INCIDENT_SOURCE_RAMEN = "signalement-rest.source.ramen";
    public static final String ERROR_GET_SIG_BY_NUMBER_NOT_FOUND = "signalement-rest.error.getSigByNumberNotFound";
    public static final String ERROR_GET_SIG_BY_ID_NOT_FOUND = "signalement-rest.error.getSigByIdNotFound";
    public static final String ERROR_GET_SIG_BY_NUMBER_TOO_OLD = "signalement-rest.error.getSigByNumberTooOld";
    public static final String ERROR_GET_ANOMALIES_FDT_BY_ID_NOT_FOUND = "signalement-rest.error.getAnomaliesFDTByIdNotFound";
    public static final String ERROR_GET_FDT_BY_ID_NOT_FOUND = "signalement-rest.error.getFDTByIdNotFound";

    /** The Constant MARK_BASE_URL. */
    // MARK
    public static final String MARK_BASE_URL = "base_url";

    /** The Constant TEMPLATE_WADL. */
    // TEMPLATES
    public static final String TEMPLATE_WADL = "admin/plugins/signalement/modules/rest/wadl.xml";

    /** The Constant HEADER_X_APP_VERSION. */
    // HTTP Headers
    public static final String HEADER_X_APP_VERSION = "x-app-version";

    /** The Constant HEADER_X_APP_PLATFORM. */
    public static final String HEADER_X_APP_PLATFORM = "x-app-platform";

    /** The Constant HEADER_X_APP_DEVICE_MODEL. */
    public static final String HEADER_X_APP_DEVICE_MODEL = "x-app-device-model";

    /** The Constant HEADER_X_APP_REQUEST_SIGNATURE. */
    public static final String HEADER_X_APP_REQUEST_SIGNATURE = "x-app-request-signature";

    /** The Constant ERROR_EMPTY_JSON_REQUEST. */
    // ERROR CODES
    public static final int ERROR_EMPTY_JSON_REQUEST = 1;

    /** The Constant ERROR_BAD_JSON_REQUEST. */
    public static final int ERROR_BAD_JSON_REQUEST = 2;

    /** The Constant ERROR_BAD_REQUEST_SUB_ELEMENT. */
    public static final int ERROR_BAD_REQUEST_SUB_ELEMENT = 3;

    /** The Constant ERROR_EMPTY_DEVICE_ID. */
    public static final int ERROR_EMPTY_DEVICE_ID = 4;

    /** The Constant ERROR_BAD_DEVICE_ID. */
    public static final int ERROR_BAD_DEVICE_ID = 5;

    /** The Constant ERROR_EMPTY_POSITION_PARAMETER. */
    public static final int ERROR_EMPTY_POSITION_PARAMETER = 6;

    /** The Constant ERROR_BAD_POSITION_PARAMETER. */
    public static final int ERROR_BAD_POSITION_PARAMETER = 7;

    /** The Constant ERROR_BAD_CATEGORY_ID_PARAMETER. */
    public static final int ERROR_BAD_CATEGORY_ID_PARAMETER = 8;

    /** The Constant ERROR_EMPTY_CATEGORY_ID_PARAMETER. */
    public static final int ERROR_EMPTY_CATEGORY_ID_PARAMETER = 9;

    /** The Constant ERROR_EMPTY_CATEGORY_ID_PARAMETER. */
    public static final int ERROR_EMPTY_INCIDENT_PRECISION_TERRAIN = 10;

    /** The Constant ERROR_BAD_ADDRESS_PARAMETER. */
    public static final int ERROR_BAD_ADDRESS_PARAMETER = 90;

    /** The Constant ERROR_ANY_INCIDENT_FOR_USER. */
    public static final int ERROR_ANY_INCIDENT_FOR_USER = 10;

    /** The Constant ERROR_EMPTY_INCIDENT_ID. */
    public static final int ERROR_EMPTY_INCIDENT_ID = 11;

    /** The Constant ERROR_BAD_INCIDENT_ID. */
    public static final int ERROR_BAD_INCIDENT_ID = 12;

    /** The Constant ERROR_BAD_USER_ID. */
    public static final int ERROR_BAD_USER_ID = 13;

    /** The Constant ERROR_EMPTY_USER_ID. */
    public static final int ERROR_EMPTY_USER_ID = 14;

    /** The Constant ERROR_EMPTY_INCIDENT_ID_BIS. */
    public static final int ERROR_EMPTY_INCIDENT_ID_BIS = 15;

    /** The Constant ERROR_BAD_INCIDENT_ID_BIS. */
    public static final int ERROR_BAD_INCIDENT_ID_BIS = 16;

    /** The Constant ERROR_ALREADY_RESOLVED. */
    public static final int ERROR_ALREADY_RESOLVED = 17;

    /** The Constant ERROR_ALREADY_CONFIRM. */
    public static final int ERROR_ALREADY_CONFIRM = 18;

    /** The Constant ERROR_ALREADY_INVALID. */
    public static final int ERROR_ALREADY_INVALID = 19;

    /** The Constant ERROR_BAD_PICTURE. */
    public static final int ERROR_BAD_PICTURE = 29;

    /** The Constant ERROR_IMPOSSIBLE_READ_PICTURE. */
    public static final int ERROR_IMPOSSIBLE_READ_PICTURE = 30;

    /** The Constant ERROR_API_REST. */
    public static final int ERROR_API_REST = 32;

    /** The Constant ERROR_SIGN_RESTQUEST. */
    public static final int ERROR_SIGN_RESTQUEST = 33;

    /** The Constant ERROR_BAD_ORIGIN. */
    public static final int ERROR_BAD_ORIGIN = 34;

    /** The Constant ERROR_BAD_SOURCE. */
    public static final int ERROR_BAD_SOURCE = 35;

    /** The Constant ERROR_BAD_OWNER. */
    public static final int ERROR_BAD_OWNER = 36;

    /** The Constant ERROR_BAD_USER_EMAIL. */
    public static final int ERROR_BAD_USER_EMAIL = 37;

    /** The Constant ERROR_BAD_DEVICE. */
    public static final int ERROR_BAD_DEVICE = 38;

    /** The Constant ERROR_BAD_ACTION_TAG. */
    public static final int ERROR_BAD_ACTION_TAG = 39;

    /** The Constant ERROR_BAD_USER_TOKEN. */
    public static final int ERROR_BAD_USER_TOKEN = 40;

    /** The Constant ERROR_ALREADY_FOLLOWED. */
    public static final int ERROR_ALREADY_FOLLOWED = 41;

    /** The Constant ERROR_INVALID_STATE_ACTION. */
    public static final int ERROR_INVALID_STATE_ACTION = 42;

    /** The Constant ERROR_NON_EXISTENT_FOLLOW_ITEM. */
    public static final int ERROR_NON_EXISTENT_FOLLOW_ITEM = 43;

    /** The Constant ERROR_NO_WORKFLOW_AVAILABLE. */
    public static final int ERROR_NO_WORKFLOW_AVAILABLE = 44;

    /** The Constant ERROR_NO_WORKFLOW_SELECTED. */
    public static final int ERROR_NO_WORKFLOW_SELECTED = 45;

    /** The Constant ERROR_GET_ALL_SOUS_TYPE_SIGNALEMENT_CASCADE. */
    public static final int ERROR_GET_ALL_SOUS_TYPE_SIGNALEMENT_CASCADE = 46;

    /** The Constant ERROR_GET_SIGNALEMENT_BY_ID. */
    public static final int ERROR_GET_SIGNALEMENT_BY_ID = 47;

    /** The Constant ERROR_GET_GEOM_FROM_LAMBERT_TO_WQ84. */
    public static final int ERROR_GET_GEOM_FROM_LAMBERT_TO_WQ84 = 48;

    /** The Constant ERROR_GET_DISTANCE_BETWEEN_SIGNALEMENT. */
    public static final int ERROR_GET_DISTANCE_BETWEEN_SIGNALEMENT = 49;

    /** The Constant ERROR_FIND_ALL_SIGNALLEMENT_IN_PERIMETER_WITH_DTO. */
    public static final int ERROR_FIND_ALL_SIGNALLEMENT_IN_PERIMETER_WITH_DTO = 50;

    /** The Constant ERROR_IS_SIGNALEMENT_FOLLOWABLE_AND_IS_SIGNALEMENT_FOLLOWED_BY_USER. */
    public static final int ERROR_IS_SIGNALEMENT_FOLLOWABLE_AND_IS_SIGNALEMENT_FOLLOWED_BY_USER = 51;

    /** The Constant ERROR_GET_ALL_PRIORITE. */
    public static final int ERROR_GET_ALL_PRIORITE = 56;

    /** The Constant ERROR_LOAD_PRIORITE_BY_ID. */
    public static final int ERROR_LOAD_PRIORITE_BY_ID = 57;

    /** The Constant ERROR_GET_ARRONDISSEMENT_BY_GEOM. */
    public static final int ERROR_GET_ARRONDISSEMENT_BY_GEOM = 58;

    /** The Constant ERROR_ADD_FOLLOWER. */
    public static final int ERROR_ADD_FOLLOWER = 59;

    /** The Constant ERROR_SAVE_SIGNALEMENT. */
    public static final int ERROR_SAVE_SIGNALEMENT = 60;

    /** The Constant ERROR_WORKFLOW_ACTION. */
    public static final int ERROR_WORKFLOW_ACTION = 61;

    /** The Constant ERROR_GET_SIGNALEMENT_BY_TOKEN. */
    public static final int ERROR_GET_SIGNALEMENT_BY_TOKEN = 65;

    /** The Constant ERROR_GET_HISTORY_SIGNALEMENT. */
    public static final int ERROR_GET_HISTORY_SIGNALEMENT = 66;

    /** The Constant ERROR_GET_TYPE_SIGNALEMENT_TREE_FOR_SOURCE. */
    public static final int ERROR_GET_TYPE_SIGNALEMENT_TREE_FOR_SOURCE = 67;
    public static final int ERROR_GET_SIGNALEMENT_BY_NUMBER_NOT_FOUND = 68;
    public static final int ERROR_GET_SIGNALEMENT_BY_NUMBER_TOO_OLD = 69;

    /** The Constant ERROR_GET_FDT_BY_ID. */
    public static final int ERROR_GET_FDT_BY_ID = 70;

    /** The Constant VUE_ENSEMBLE. */
    // CONSTANTS PICTURE
    public static final Integer VUE_ENSEMBLE = 1;

    /** The Constant VUE_PRES. */
    public static final Integer VUE_PRES = 0;

    /** The Constant VUE_SERVICE_FAIT. */
    public static final Integer VUE_SERVICE_FAIT = 2;

    /** The Constant PICTURE_FAR. */
    public static final String PICTURE_FAR = "far";

    /** The Constant PICTURE_CLOSE. */
    public static final String PICTURE_CLOSE = "close";

    /** The Constant PICTURE_DONE. */
    public static final String PICTURE_DONE = "done";

    /** The Constant ACTION_TRANSFERED_STATE_WEBSERVICE_ACCEPTED. */
    // CONSTANTS ACTION
    public static final Integer ACTION_TRANSFERED_STATE_WEBSERVICE_ACCEPTED = 62;

    /** The Constant ACTION_TRANSFERED_STATE_WEBSERVICE_REJECTED. */
    public static final Integer ACTION_TRANSFERED_STATE_WEBSERVICE_REJECTED = 64;

    /** The Constant ACTION_TRANSFERED_STATE_WEBSERVICE_PROGRAMMED. */
    public static final Integer ACTION_TRANSFERED_STATE_WEBSERVICE_PROGRAMMED = 68;

    /** The Constant ACTION_TRANSFERED_STATE_WEBSERVICE_REQUALIFIED. */
    public static final Integer ACTION_TRANSFERED_STATE_WEBSERVICE_REQUALIFIED = 78;

    /** The Constant ACTION_TRANSFERED_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_TRANSFERED_STATE_WEBSERVICE_A_REQUALIFIED = 63;

    /** The Constant ACTION_PROGRAMMED_STATE_WEBSERVICE_ACCEPTED. */
    public static final Integer ACTION_PROGRAMMED_STATE_WEBSERVICE_ACCEPTED = 70;

    /** The Constant ACTION_PROGRAMMED_STATE_WEBSERVICE_REJECTED. */
    public static final Integer ACTION_PROGRAMMED_STATE_WEBSERVICE_REJECTED = 71;

    /** The Constant ACTION_PROGRAMMED_STATE_WEBSERVICE_PROGRAMMED. */
    public static final Integer ACTION_PROGRAMMED_STATE_WEBSERVICE_PROGRAMMED = 72;

    /** The Constant ACTION_PROGRAMMED_STATE_WEBSERVICE_REQUALIFIED. */
    public static final Integer ACTION_PROGRAMMED_STATE_WEBSERVICE_REQUALIFIED = 79;

    /** The Constant ACTION_PROGRAMMED_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_PROGRAMMED_STATE_WEBSERVICE_A_REQUALIFIED = 73;

    /** The Constant ACTION_TODO_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_TODO_STATE_WEBSERVICE_A_REQUALIFIED = 75;

    /** The Constant ACTION_TO_REQUALIFIE_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_TO_REQUALIFIE_STATE_WEBSERVICE_A_REQUALIFIED = 45;

    /** The Constant ACTION_NEW_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_NEW_STATE_WEBSERVICE_A_REQUALIFIED = 14;

    /** The Constant ACTION_PROGRAMME_STATE_WEBSERVICE_A_REQUALIFIED. */
    public static final Integer ACTION_PROGRAMME_STATE_WEBSERVICE_A_REQUALIFIED = 23;

    /** The Constant TRANFERED_PROVIDER_STATUS_ID. */
    // CONSTANTS STATE
    public static final Integer TRANFERED_PROVIDER_STATUS_ID = 18;

    /** The Constant PROGRAMMED_STATUS_ID. */
    public static final Integer PROGRAMMED_STATUS_ID = 21;

    /** The Constant SERVICE_FAIT_STATUS_ID. */
    public static final Integer SERVICE_FAIT_STATUS_ID = 10;

    /** The Constant ERROR_MESSAGE_NO_ANOMALY_FOUND. */
    // I18N MESSAGES
    public static final String ERROR_MESSAGE_NO_ANOMALY_FOUND = "module.dansmarue.rest.webservice_listener.error.no_anomaly_found";

    /** The Constant ERROR_MESSAGE_NO_TOKEN_FOUND. */
    public static final String ERROR_MESSAGE_NO_TOKEN_FOUND = "module.dansmarue.rest.webservice_listener.error.no_token_found";

    /** The Constant ERROR_MESSAGE_DIFFERENT_STATUS. */
    public static final String ERROR_MESSAGE_DIFFERENT_STATUS = "module.dansmarue.rest.webservice_listener.error.different_status";

    /** The Constant ERROR_MESSAGE_INITIAL_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_INITIAL_WRONG_STATUS = "module.dansmarue.rest.webservice_listener.error.initial.wrong_status";


    /** The Constant ERROR_MESSAGE_DONE_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_DONE_WRONG_STATUS = "module.dansmarue.rest.webservice_listener.error.done.wrong_status";

    /** The Constant ERROR_MESSAGE_REJECTED_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_REJECTED_WRONG_STATUS = "module.dansmarue.rest.webservice_listener.error.rejected.wrong_status";

    /** The Constant ERROR_MESSAGE_REJECTED_WRONG_REJECT_CAUSE_ID. */
    public static final String ERROR_MESSAGE_REJECTED_WRONG_REJECT_CAUSE_ID = "module.dansmarue.rest.webservice_listener.error.rejected.wrong_reject_cause_id";

    /** The Constant ERROR_MESSAGE_REQUALIFIED_WRONG_TYPE_ANOMALIE. */
    public static final String ERROR_MESSAGE_REQUALIFIED_WRONG_TYPE_ANOMALIE = "module.dansmarue.rest.webservice_listener.error.requalified.wrong_type_anomalie";

    /** The Constant ERROR_MESSAGE_PROGRAMMED_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_PROGRAMMED_WRONG_STATUS = "module.dansmarue.rest.webservice_listener.error.programmed.wrong_status";

    /** The Constant ERROR_MESSAGE_REQUALIFIED_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_REQUALIFIED_WRONG_STATUS = "module.dansmarue.rest.webservice_listener.error.requalified.wrong_status";

    /** The Constant ERROR_MESSAGE_ACTION_WRONG_DATE_FORMAT. */
    public static final String ERROR_MESSAGE_ACTION_WRONG_DATE_FORMAT = "module.dansmarue.rest.webservice_listener.error.action.wrong_date_format";

    /** The Constant ERROR_MESSAGE_PROGRAMMED_WRONG_DATE_FORMAT. */
    public static final String ERROR_MESSAGE_PROGRAMMED_WRONG_DATE_FORMAT = "module.dansmarue.rest.webservice_listener.error.programmed.wrong_date_format";

    /** The Constant ERROR_MESSAGE_WRONG_FORMAT. */
    public static final String ERROR_MESSAGE_WRONG_FORMAT = "module.dansmarue.rest.webservice_listener.error.wrong_format";

    /** The Constant ERROR_MESSAGE_WRONG_STATUS. */
    public static final String ERROR_MESSAGE_WRONG_TYPE = "module.dansmarue.rest.webservice_listener.error.wrong_status";

    /** The Constant ERROR_MESSAGE_PROGRAMMED_DATE_BEFORE_TODAY. */
    public static final String ERROR_MESSAGE_PROGRAMMED_DATE_BEFORE_TODAY = "module.dansmarue.rest.webservice_listener.error.programmed.date.before.today";

    /** The Constant PROPERTY_ID_STATE_SERVICE_FAIT. */
    // STATUS
    public static final String PROPERTY_ID_STATE_SERVICE_FAIT = "signalement.idStateServiceFait";

    /**
     * Constructor.
     */
    private SignalementRestConstants( )
    {
    }
}
