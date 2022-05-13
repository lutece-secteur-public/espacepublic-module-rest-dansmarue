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

/**
 * SiraRestConstants.
 */
public class SiraRestConstants
{

    /** The Constant PATH_WADL. */
    // CONSTANTS
    public static final String PATH_WADL = "wadl";

    /** The Constant PATH_API. */
    public static final String PATH_API = "API";

    /** The Constant PATH_SIGNALEMENT_API. */
    public static final String PATH_SIGNALEMENT_API = "signalement/api";

    /** The Constant PATH_IDENTITY_STORE. */
    public static final String PATH_IDENTITY_STORE = "identitystore";

    /** The Constant PATH_TEST_GET. */
    public static final String PATH_TEST_GET = "test_get";

    /** The Constant SLASH. */
    public static final String SLASH = "/";

    /** The Constant WORKFLOW_RAMEN_RESOURCE_TYPE. */
    public static final String WORKFLOW_RAMEN_RESOURCE_TYPE = "RAMEN_DOSSIER";

    /** The Constant WORKFLOW_RAMEN_ID. */
    public static final int WORKFLOW_RAMEN_ID = 1;

    /** The Constant WORKFLOW_SIGNALEMENT_ID. */
    public static final int WORKFLOW_SIGNALEMENT_ID = 2;

    /** The Constant WORKFLOW_SIGNALEMENT_RESOURCE_TYPE. */
    public static final String WORKFLOW_SIGNALEMENT_RESOURCE_TYPE = "SIGNALEMENT_SIGNALEMENT";

    /** The Constant ID_ACTION_SERVICE_FAIT_RAMEN_PLANIFIE. */
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_PLANIFIE = 5;

    /** The Constant ID_ACTION_SERVICE_FAIT_RAMEN_PRIS_COMPTE. */
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_PRIS_COMPTE = 30;

    /** The Constant ID_ACTION_SERVICE_FAIT_RAMEN_REPLANIFIE. */
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_REPLANIFIE = 34;

    /** The Constant ID_ACTION_SERVICE_FAIT_RAMEN_CLANDESTIN. */
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_CLANDESTIN = 46;

    /** The Constant ID_ACTION_SERVICE_FAIT_SIGNALEMENT_A_TRAITER. */
    public static final int ID_ACTION_SERVICE_FAIT_SIGNALEMENT_A_TRAITER = 18;

    /** The Constant ID_ACTION_SERVICE_FAIT_SIGNALEMENT_PROGRAMME. */
    public static final int ID_ACTION_SERVICE_FAIT_SIGNALEMENT_PROGRAMME = 22;

    /** The Constant ID_STATE_PLANIFIE. */
    public static final int ID_STATE_PLANIFIE = 2;

    /** The Constant ID_STATE_PRIS_COMPTE. */
    public static final int ID_STATE_PRIS_COMPTE = 3;

    /** The Constant ID_STATE_REPLANIFIE. */
    public static final int ID_STATE_REPLANIFIE = 14;

    /** The Constant ID_STATE_A_TRAITER. */
    public static final int ID_STATE_A_TRAITER = 8;

    /** The Constant ID_STATE_PROGRAMME. */
    public static final int ID_STATE_PROGRAMME = 9;

    /** The Constant NO_ERROR_0. */
    public static final int NO_ERROR_0 = 0;

    /** The Constant ERROR_1. */
    public static final int ERROR_1 = 1;

    /** The Constant JSON_TAG_REQUEST. */
    // JSON TAG
    public static final String JSON_TAG_REQUEST = "request";

    /** The Constant JSON_TAG_ERROR. */
    public static final String JSON_TAG_ERROR = "error";

    /** The Constant JSON_TAG_ANSWER. */
    public static final String JSON_TAG_ANSWER = "answer";

    /** The Constant JSON_TAG_STATUS. */
    public static final String JSON_TAG_STATUS = "status";

    /** The Constant JSON_TAG_CLOSEST_INCIDENTS. */
    public static final String JSON_TAG_CLOSEST_INCIDENTS = "closest_incidents";

    /** The Constant JSON_TAG_INCIDENT. */
    public static final String JSON_TAG_INCIDENT = "incident";

    /** The Constant JSON_TAG_POSITION. */
    public static final String JSON_TAG_POSITION = "position";

    /** The Constant JSON_TAG_LATITUDE. */
    public static final String JSON_TAG_LATITUDE = "latitude";

    /** The Constant JSON_TAG_LONGITUDE. */
    public static final String JSON_TAG_LONGITUDE = "longitude";

    /** The Constant JSON_TAG_RESOLVED_INCIDENTS. */
    public static final String JSON_TAG_RESOLVED_INCIDENTS = "resolved_incidents";

    /** The Constant JSON_TAG_ONGOING_INCIDENTS. */
    public static final String JSON_TAG_ONGOING_INCIDENTS = "ongoing_incidents";

    /** The Constant JSON_TAG_UPDATED_INCIDENTS. */
    public static final String JSON_TAG_UPDATED_INCIDENTS = "updated_incidents";

    /** The Constant JSON_TAG_INCIDENT_PICTURE. */
    public static final String JSON_TAG_INCIDENT_PICTURE = "incident_picture";

    /** The Constant JSON_TAG_GUID. */
    public static final String JSON_TAG_GUID = "guid";

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

    /** The Constant JSON_TAG_LOG_INCIDENT_ID. */
    // JSON TAG FOR LOG
    public static final String JSON_TAG_LOG_INCIDENT_ID = "incidentId";

    /** The Constant JSON_TAG_LOG_UDID. */
    public static final String JSON_TAG_LOG_UDID = "udid";

    /** The Constant JSON_TAG_LOG_DATE. */
    public static final String JSON_TAG_LOG_DATE = "date";

    /** The Constant JSON_TAG_LOG_STATUS. */
    public static final String JSON_TAG_LOG_STATUS = "status";

    /** The Constant JSON_TAG_ERROR_ERROR. */
    // JSON TAG FOR ERROR RAMEN
    public static final String JSON_TAG_ERROR_ERROR = "error";

    /** The Constant JSON_TAG_ERROR_MESSAGE. */
    public static final String JSON_TAG_ERROR_MESSAGE = "error_message";

    /** The Constant REQUEST_TYPE_ENCOMBRANTS. */
    // REQUEST TYPES
    public static final String REQUEST_TYPE_ENCOMBRANTS = "ramenTypesEncombrants";

    /** The Constant REQUEST_ENTITY_HIERARCHY. */
    public static final String REQUEST_ENTITY_HIERARCHY = "entityHierarchy";

    /** The Constant REQUEST_FEUILLE_DE_ROUTE. */
    public static final String REQUEST_FEUILLE_DE_ROUTE = "ramenFeuilleDeRoute";

    /** The Constant REQUEST_RAMEN_POSTER_FAIT. */
    public static final String REQUEST_RAMEN_POSTER_FAIT = "ramenPosterFait";

    /** The Constant JSON_TAG_DOSSIER_LIST. */
    public static final String JSON_TAG_DOSSIER_LIST = "dossierList";

    /** The Constant JSON_TAG_DOSSIEROBJ_ID. */
    // JSON TAG FOR DOSSIER OBJ
    public static final String JSON_TAG_DOSSIEROBJ_ID = "id";

    /** The Constant JSON_TAG_DOSSIEROBJ_NUMBER. */
    public static final String JSON_TAG_DOSSIEROBJ_NUMBER = "number";

    /** The Constant JSON_TAG_DOSSIEROBJ_TYPE. */
    public static final String JSON_TAG_DOSSIEROBJ_TYPE = "type";

    /** The Constant JSON_TAG_DOSSIEROBJ_TYPENAME. */
    public static final String JSON_TAG_DOSSIEROBJ_TYPENAME = "typename";

    /** The Constant JSON_TAG_DOSSIEROBJ_PRIORITY. */
    public static final String JSON_TAG_DOSSIEROBJ_PRIORITY = "priority";

    /** The Constant JSON_TAG_DOSSIEROBJ_QUANTITY. */
    public static final String JSON_TAG_DOSSIEROBJ_QUANTITY = "quantity";

    /** The Constant JSON_TAG_DOSSIEROBJ_QUANTITIES. */
    public static final String JSON_TAG_DOSSIEROBJ_QUANTITIES = "quantities";

    /** The Constant JSON_TAG_DOSSIEROBJ_ADDRESS. */
    public static final String JSON_TAG_DOSSIEROBJ_ADDRESS = "adress";

    /** The Constant JSON_TAG_DOSSIEROBJ_DATE. */
    public static final String JSON_TAG_DOSSIEROBJ_DATE = "date";

    /** The Constant JSON_TAG_DOSSIEROBJ_LATITUDE. */
    public static final String JSON_TAG_DOSSIEROBJ_LATITUDE = "latitude";

    /** The Constant JSON_TAG_DOSSIEROBJ_LONGITUDE. */
    public static final String JSON_TAG_DOSSIEROBJ_LONGITUDE = "longitude";

    /** The Constant JSON_TAG_DOSSIEROBJ_PRECISION. */
    public static final String JSON_TAG_DOSSIEROBJ_PRECISION = "precision";

    /** The Constant JSON_TAG_DOSSIEROBJ_PICTURE. */
    public static final String JSON_TAG_DOSSIEROBJ_PICTURE = "picture";

    /** The Constant JSON_TAG_DOSSIEROBJ_DESCRIPTION. */
    public static final String JSON_TAG_DOSSIEROBJ_DESCRIPTION = "description";

    /** The Constant JSON_TAG_NOMENCLATURE. */
    public static final String JSON_TAG_NOMENCLATURE = "nomenclature";

    /** The Constant JSON_TAG_ROUND. */
    public static final String JSON_TAG_ROUND = "round";

    /** The Constant JSON_TAG_FEUILLE_ROUTE. */
    public static final String JSON_TAG_FEUILLE_ROUTE = "feuilleDeRoute";

    /** The Constant JSON_TAG_ENCOMBRANT_ID. */
    // JSON TAG FOR ENCOMBRANT
    public static final String JSON_TAG_ENCOMBRANT_ID = "id";

    /** The Constant JSON_TAG_ENCOMBRANT_NAME. */
    public static final String JSON_TAG_ENCOMBRANT_NAME = "name";

    /** The Constant JSON_TAG_ENCOMBRANT_IMAGE_URL. */
    public static final String JSON_TAG_ENCOMBRANT_IMAGE_URL = "picture";

    /** The Constant JSON_TAG_ENCOMBRANT_FORBIDDEN. */
    public static final String JSON_TAG_ENCOMBRANT_FORBIDDEN = "isForbidden";

    /** The Constant JSON_TAG_RAMEN_TYPES_LIST. */
    public static final String JSON_TAG_RAMEN_TYPES_LIST = "RamentypesList";

    /** The Constant JSON_TAG_RAMEN_TYPE_OBJET. */
    public static final String JSON_TAG_RAMEN_TYPE_OBJET = "ramenType";

    /** The Constant JSON_TAG_ENCOMBRANT_QUANTITY. */
    public static final String JSON_TAG_ENCOMBRANT_QUANTITY = "quantity";

    /** The Constant JSON_TAG_UNIT_ID. */
    // JSON TAG FOR UNIT
    public static final String JSON_TAG_UNIT_ID = "id";

    /** The Constant JSON_TAG_UNIT_NAME. */
    public static final String JSON_TAG_UNIT_NAME = "name";

    /** The Constant JSON_TAG_CHILDREN_NAME. */
    public static final String JSON_TAG_CHILDREN_NAME = "children";

    /** The Constant JSON_TAG_ENTITY. */
    public static final String JSON_TAG_ENTITY = "entity";

    /** The Constant JSON_TAG_INCIDENT_ENCOMBRANTS. */
    // JSON TAG FOR INCIDENT
    public static final String JSON_TAG_INCIDENT_ENCOMBRANTS = "encombrants";

    /** The Constant JSON_TAG_NAME. */
    // JSON TAG FOR MON COMPTE
    public static final String JSON_TAG_NAME = "name";

    /** The Constant JSON_TAG_FIRSTNAME. */
    public static final String JSON_TAG_FIRSTNAME = "firstname";

    /** The Constant JSON_TAG_MAIL. */
    public static final String JSON_TAG_MAIL = "mail";

    /** The Constant JSON_TAG_USER. */
    public static final String JSON_TAG_USER = "user";

    /** The Constant PARAMETERS_JSON_STREAM. */
    // PARAMETERS
    public static final String PARAMETERS_JSON_STREAM = "jsonStream";

    /** The Constant PARAMETER_NOMENCLATURE_RAMEN. */
    public static final String PARAMETER_NOMENCLATURE_RAMEN = "nomenclature";

    /** The Constant PROPERTY_DEFAULT_CATEGORY_ID. */
    // PROPERTIES
    public static final String PROPERTY_DEFAULT_CATEGORY_ID = "ramen-rest.default.category.id";

    /** The Constant PROPERTY_DESCRIPTION_MESSAGE_DOSSIER. */
    public static final String PROPERTY_DESCRIPTION_MESSAGE_DOSSIER = "ramen-rest.description.dossier";

    /** The Constant PROPERTY_PRIVATE_KEY_ANDROID_API. */
    public static final String PROPERTY_PRIVATE_KEY_ANDROID_API = "signalement-rest.private.key.android_api";

    /** The Constant PROPERTY_ACTIVATION_SIGNREQUEST. */
    public static final String PROPERTY_ACTIVATION_SIGNREQUEST = "signalement-rest.signrequest.activation";

    /** The Constant PROPERTY_URL_PICTURE. */
    public static final String PROPERTY_URL_PICTURE = "sira-rest.url_picture";

    /** The Constant PROPERTY_URL_DOSSIER_PICTURE. */
    public static final String PROPERTY_URL_DOSSIER_PICTURE = "sira-rest.dossier.picture";

    /** The Constant MSG_DOSSIER_RAMEN_ALIAS. */
    public static final String MSG_DOSSIER_RAMEN_ALIAS = "module.dansmarue.rest.dossier.alias";

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

    /**
     * Instantiates a new sira rest constants.
     */
    private SiraRestConstants( )
    {

    }
}
