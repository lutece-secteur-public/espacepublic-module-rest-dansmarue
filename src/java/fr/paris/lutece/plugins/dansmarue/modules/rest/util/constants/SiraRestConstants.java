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


/**
 * SiraRestConstants
 *
 */
public class SiraRestConstants
{
    // CONSTANTS
    public static final String PATH_WADL = "wadl";
    public static final String PATH_API = "API";
    public static final String PATH_SIGNALEMENT_API = "signalement/api";
    public static final String PATH_IDENTITY_STORE = "identitystore";
    public static final String PATH_TEST_GET = "test_get";
    public static final String SLASH = "/";
    public static final String WORKFLOW_RAMEN_RESOURCE_TYPE = "RAMEN_DOSSIER";
    public static final int WORKFLOW_RAMEN_ID = 1;
    public static final int WORKFLOW_SIGNALEMENT_ID = 2;
    public static final String WORKFLOW_SIGNALEMENT_RESOURCE_TYPE = "SIGNALEMENT_SIGNALEMENT";
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_PLANIFIE = 5;
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_PRIS_COMPTE = 30;
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_REPLANIFIE = 34;
    public static final int ID_ACTION_SERVICE_FAIT_RAMEN_CLANDESTIN = 46;
    public static final int ID_ACTION_SERVICE_FAIT_SIGNALEMENT_A_TRAITER = 18;
    public static final int ID_ACTION_SERVICE_FAIT_SIGNALEMENT_PROGRAMME = 22;
    public static final int ID_STATE_PLANIFIE = 2;
    public static final int ID_STATE_PRIS_COMPTE = 3;
    public static final int ID_STATE_REPLANIFIE = 14;
    public static final int ID_STATE_A_TRAITER = 8;
    public static final int ID_STATE_PROGRAMME = 9;
    public static final int NO_ERROR_0 = 0;
    public static final int ERROR_1 = 1;

    //JSON TAG
    public static final String JSON_TAG_REQUEST = "request";
    public static final String JSON_TAG_ERROR = "error";
    public static final String JSON_TAG_ANSWER = "answer";
    public static final String JSON_TAG_STATUS = "status";
    public static final String JSON_TAG_CLOSEST_INCIDENTS = "closest_incidents";
    public static final String JSON_TAG_INCIDENT = "incident";
    public static final String JSON_TAG_POSITION = "position";
    public static final String JSON_TAG_LATITUDE = "latitude";
    public static final String JSON_TAG_LONGITUDE = "longitude";
    public static final String JSON_TAG_RESOLVED_INCIDENTS = "resolved_incidents";
    public static final String JSON_TAG_ONGOING_INCIDENTS = "ongoing_incidents";
    public static final String JSON_TAG_UPDATED_INCIDENTS = "updated_incidents";
    public static final String JSON_TAG_INCIDENT_PICTURE = "incident_picture";
    public static final String JSON_TAG_GUID = "guid";

    //ERROR CODES
    public static final int ERROR_EMPTY_JSON_REQUEST = 1;
    public static final int ERROR_BAD_JSON_REQUEST = 2;
    public static final int ERROR_BAD_REQUEST_SUB_ELEMENT = 3;
    public static final int ERROR_EMPTY_DEVICE_ID = 4;
    public static final int ERROR_BAD_DEVICE_ID = 5;
    public static final int ERROR_EMPTY_POSITION_PARAMETER = 6;
    public static final int ERROR_BAD_POSITION_PARAMETER = 7;
    public static final int ERROR_BAD_CATEGORY_ID_PARAMETER = 8;
    public static final int ERROR_EMPTY_CATEGORY_ID_PARAMETER = 9;
    public static final int ERROR_BAD_ADDRESS_PARAMETER = 90;
    public static final int ERROR_ANY_INCIDENT_FOR_USER = 10;
    public static final int ERROR_EMPTY_INCIDENT_ID = 11;
    public static final int ERROR_BAD_INCIDENT_ID = 12;
    public static final int ERROR_BAD_USER_ID = 13;
    public static final int ERROR_EMPTY_USER_ID = 14;
    public static final int ERROR_EMPTY_INCIDENT_ID_BIS = 15;
    public static final int ERROR_BAD_INCIDENT_ID_BIS = 16;
    public static final int ERROR_ALREADY_RESOLVED = 17;
    public static final int ERROR_ALREADY_CONFIRM = 18;
    public static final int ERROR_ALREADY_INVALID = 19;
    public static final int ERROR_BAD_PICTURE = 29;
    public static final int ERROR_IMPOSSIBLE_READ_PICTURE = 30;
    public static final int ERROR_API_REST = 32;
    public static final int ERROR_SIGN_RESTQUEST = 33;

    //JSON TAG FOR LOG
    public static final String JSON_TAG_LOG_INCIDENT_ID = "incidentId";
    public static final String JSON_TAG_LOG_UDID = "udid";
    public static final String JSON_TAG_LOG_DATE = "date";
    public static final String JSON_TAG_LOG_STATUS = "status";

    //JSON TAG FOR ERROR RAMEN
    public static final String JSON_TAG_ERROR_ERROR = "error";
    public static final String JSON_TAG_ERROR_MESSAGE = "error_message";

    //REQUEST TYPES
    public static final String REQUEST_TYPE_ENCOMBRANTS = "ramenTypesEncombrants";
    public static final String REQUEST_ENTITY_HIERARCHY = "entityHierarchy";
    public static final String REQUEST_FEUILLE_DE_ROUTE = "ramenFeuilleDeRoute";
    public static final String REQUEST_RAMEN_POSTER_FAIT = "ramenPosterFait";
    public static final String JSON_TAG_DOSSIER_LIST = "dossierList";

    //JSON TAG FOR DOSSIER OBJ
    public static final String JSON_TAG_DOSSIEROBJ_ID = "id";
    public static final String JSON_TAG_DOSSIEROBJ_NUMBER = "number";
    public static final String JSON_TAG_DOSSIEROBJ_TYPE = "type";
    public static final String JSON_TAG_DOSSIEROBJ_TYPENAME = "typename";
    public static final String JSON_TAG_DOSSIEROBJ_PRIORITY = "priority";
    public static final String JSON_TAG_DOSSIEROBJ_QUANTITY = "quantity";
    public static final String JSON_TAG_DOSSIEROBJ_QUANTITIES = "quantities";
    public static final String JSON_TAG_DOSSIEROBJ_ADDRESS = "adress";
    public static final String JSON_TAG_DOSSIEROBJ_DATE = "date";
    public static final String JSON_TAG_DOSSIEROBJ_LATITUDE = "latitude";
    public static final String JSON_TAG_DOSSIEROBJ_LONGITUDE = "longitude";
    public static final String JSON_TAG_DOSSIEROBJ_PRECISION = "precision";
    public static final String JSON_TAG_DOSSIEROBJ_PICTURE = "picture";
    public static final String JSON_TAG_DOSSIEROBJ_DESCRIPTION = "description";
    public static final String JSON_TAG_NOMENCLATURE = "nomenclature";
    public static final String JSON_TAG_ROUND = "round";
    public static final String JSON_TAG_FEUILLE_ROUTE = "feuilleDeRoute";

    //JSON TAG FOR ENCOMBRANT
    public static final String JSON_TAG_ENCOMBRANT_ID = "id";
    public static final String JSON_TAG_ENCOMBRANT_NAME = "name";
    public static final String JSON_TAG_ENCOMBRANT_IMAGE_URL = "picture";
    public static final String JSON_TAG_ENCOMBRANT_FORBIDDEN = "isForbidden";
    public static final String JSON_TAG_RAMEN_TYPES_LIST = "RamentypesList";
    public static final String JSON_TAG_RAMEN_TYPE_OBJET = "ramenType";
    public static final String JSON_TAG_ENCOMBRANT_QUANTITY = "quantity";

    //JSON TAG FOR UNIT
    public static final String JSON_TAG_UNIT_ID = "id";
    public static final String JSON_TAG_UNIT_NAME = "name";
    public static final String JSON_TAG_CHILDREN_NAME = "children";
    public static final String JSON_TAG_ENTITY = "entity";

    //JSON TAG FOR INCIDENT
    public static final String JSON_TAG_INCIDENT_ENCOMBRANTS = "encombrants";

    //JSON TAG FOR MON COMPTE
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_FIRSTNAME = "firstname";
    public static final String JSON_TAG_MAIL = "mail";
    public static final String JSON_TAG_USER = "user";

    //PARAMETERS
    public static final String PARAMETERS_JSON_STREAM = "jsonStream";
    public static final String PARAMETER_NOMENCLATURE_RAMEN = "nomenclature";

    //PROPERTIES
    public static final String PROPERTY_DEFAULT_CATEGORY_ID = "ramen-rest.default.category.id";
    public static final String PROPERTY_DESCRIPTION_MESSAGE_DOSSIER = "ramen-rest.description.dossier";
    public static final String PROPERTY_PRIVATE_KEY_ANDROID_API = "signalement-rest.private.key.android_api";
    public static final String PROPERTY_ACTIVATION_SIGNREQUEST = "signalement-rest.signrequest.activation";
    public static final String PROPERTY_URL_PICTURE = "sira-rest.url_picture";
    public static final String PROPERTY_URL_DOSSIER_PICTURE = "sira-rest.dossier.picture";
    public static final String MSG_DOSSIER_RAMEN_ALIAS = "module.dansmarue.rest.dossier.alias";

    //MARK
    public static final String MARK_BASE_URL = "base_url";

    //TEMPLATES
    public static final String TEMPLATE_WADL = "admin/plugins/signalement/modules/rest/wadl.xml";

    //HTTP Headers
    public static final String HEADER_X_APP_VERSION = "x-app-version";
    public static final String HEADER_X_APP_PLATFORM = "x-app-platform";
    public static final String HEADER_X_APP_DEVICE_MODEL = "x-app-device-model";
    public static final String HEADER_X_APP_REQUEST_SIGNATURE = "x-app-request-signature";

    private SiraRestConstants( ) {

    }
}
