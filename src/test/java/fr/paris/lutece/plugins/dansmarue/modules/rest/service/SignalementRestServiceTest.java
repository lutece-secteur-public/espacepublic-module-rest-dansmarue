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

import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_EMAIL;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT_ADDRESS;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT_CATEGORIE_ID;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT_DESCRIPTIVE;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT_ORIGIN;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_INCIDENT_PRIORITE_ID;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_LATITUDE;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_LONGITUDE;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_POSITION;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_UDID;
import static fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SignalementRestConstants.JSON_TAG_COMMENTAIRE_AGENT_TERRAIN;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.CollectionUtils;

import fr.paris.lutece.plugins.dansmarue.business.entities.Adresse;
import fr.paris.lutece.plugins.dansmarue.business.entities.Arrondissement;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signalement;
import fr.paris.lutece.plugins.dansmarue.business.entities.Signaleur;
import fr.paris.lutece.plugins.dansmarue.business.entities.TypeSignalement;
import fr.paris.lutece.plugins.dansmarue.modules.rest.util.exception.ParseSignalementFromJSONException;
import fr.paris.lutece.plugins.dansmarue.service.IAdresseService;
import fr.paris.lutece.plugins.dansmarue.service.IPhotoService;
import fr.paris.lutece.plugins.dansmarue.service.IPrioriteService;
import fr.paris.lutece.plugins.dansmarue.service.ISignalementService;
import fr.paris.lutece.plugins.dansmarue.service.ISignaleurService;
import fr.paris.lutece.plugins.dansmarue.service.ITypeSignalementService;
import fr.paris.lutece.plugins.dansmarue.service.IWorkflowService;
import fr.paris.lutece.plugins.dansmarue.util.constants.SignalementConstants;
import fr.paris.lutece.plugins.dansmarue.utils.SignalementUtils;
import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector.Sector;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import net.sf.json.JSONObject;

/**
 * The Class SignalementRestServiceTest.
 */
@RunWith( PowerMockRunner.class )
@PrepareForTest( {
        SignalementConstants.class, AppPropertiesService.class, SignalementUtils.class
} )
public class SignalementRestServiceTest
{

    /** The Constant JSON_VALUE_UDID. */
    private static final String JSON_VALUE_UDID = "B52";

    /** The Constant JSON_VALUE_EMAIL. */
    private static final String JSON_VALUE_EMAIL = "junit@lutece.fr";

    /** The Constant JSON_VALUE_LATITUDE. */
    private static final String JSON_VALUE_LATITUDE = "48.8468545";

    /** The Constant JSON_VALUE_LATITUDE_DOUBLE. */
    private static final double JSON_VALUE_LATITUDE_DOUBLE = new Double( JSON_VALUE_LATITUDE );

    /** The Constant JSON_VALUE_LONGITUDE. */
    private static final String JSON_VALUE_LONGITUDE = "2.369533";

    /** The Constant JSON_VALUE_LONGITUDE_DOUBLE. */
    private static final double JSON_VALUE_LONGITUDE_DOUBLE = new Double( JSON_VALUE_LONGITUDE );

    /** The Constant JSON_VALUE_INCIDENT_CATEGORIE_ID. */
    private static final String JSON_VALUE_INCIDENT_CATEGORIE_ID = "5201";

    /** The Constant JSON_VALUE_INCIDENT_CATEGORIE_ID_INT. */
    private static final int JSON_VALUE_INCIDENT_CATEGORIE_ID_INT = new Integer( JSON_VALUE_INCIDENT_CATEGORIE_ID );

    /** The Constant JSON_VALUE_INCIDENT_ADDRESS. */
    private static final String JSON_VALUE_INCIDENT_ADDRESS = "226 rue";

    /** The Constant JSON_VALUE_INCIDENT_DESCRIPTIVE. */
    private static final String JSON_VALUE_INCIDENT_DESCRIPTIVE = "Trou dans le sol";

    /** The Constant JSON_VALUE_INCIDENT_PRIORITE_ID. */
    private static final Long JSON_VALUE_INCIDENT_PRIORITE_ID = 3L;

    /** The Constant JSON_VALUE_ORIGIN. */
    private static final String JSON_VALUE_ORIGIN = "A";

    /** Possible origin list *. */
    private static final String [ ] SIGNALEMENT_POSSIBLE_ORIGINS = {
            "A", "G", "S", "B"
    };

    /** The json src. */
    JSONObject jsonSrc;

    /** The possible signalement origins. */
    private List<String> possibleSignalementOrigins;

    /** The type signalement service. */
    @Mock
    ITypeSignalementService _typeSignalementService;

    /** The signalement service. */
    @Mock
    ISignalementService _signalementService;

    /** The adresse service. */
    @Mock
    IAdresseService _adresseService;

    /** The photo service. */
    @Mock
    IPhotoService _photoService;

    /** The signaleur service. */
    @Mock
    ISignaleurService _signaleurService;

    /** The signalement workflow service. */
    @Mock
    IWorkflowService _signalementWorkflowService;

    /** The ramen client service. */
    @Mock
    RamenClientService _ramenClientService;

    /** The priorite service. */
    @Mock
    IPrioriteService _prioriteService;

    /** The signalement rest service. */
    @InjectMocks
    SignalementRestService signalementRestService = new SignalementRestService( );

    /**
     * Inits the.
     */
    @Before
    public void init( )
    {
        JSONObject jsonIncident = new JSONObject( );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_CATEGORIE_ID, JSON_VALUE_INCIDENT_CATEGORIE_ID );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_ADDRESS, JSON_VALUE_INCIDENT_ADDRESS );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_DESCRIPTIVE, JSON_VALUE_INCIDENT_DESCRIPTIVE );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_PRIORITE_ID, JSON_VALUE_INCIDENT_PRIORITE_ID );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_ORIGIN, JSON_VALUE_ORIGIN );
        jsonIncident.accumulate( JSON_TAG_COMMENTAIRE_AGENT_TERRAIN, false );

        JSONObject jsonPosition = new JSONObject( );
        jsonPosition.accumulate( JSON_TAG_LATITUDE, JSON_VALUE_LATITUDE );
        jsonPosition.accumulate( JSON_TAG_LONGITUDE, JSON_VALUE_LONGITUDE );

        jsonSrc = new JSONObject( );
        jsonSrc.accumulate( JSON_TAG_UDID, JSON_VALUE_UDID );
        jsonSrc.accumulate( JSON_TAG_EMAIL, JSON_VALUE_EMAIL );
        jsonSrc.accumulate( JSON_TAG_INCIDENT, jsonIncident );
        jsonSrc.accumulate( JSON_TAG_POSITION, jsonPosition );

        possibleSignalementOrigins = CollectionUtils.arrayToList( SIGNALEMENT_POSSIBLE_ORIGINS );

    }

    /**
     * Parse JSONObject into new Java Object that contains all informations necessary for SignalementServiceRest.saveIncident()
     * 
     * @throws ParseSignalementFromJSONException
     *             throw in case of json exception
     */
    @Test
    public void parseIncidentFromJson( ) throws ParseSignalementFromJSONException
    {
        PowerMockito.mockStatic( AppPropertiesService.class );
        when( AppPropertiesService.getProperty( Mockito.anyString( ) ) ).thenReturn( "" );

        PowerMockito.mockStatic( SignalementUtils.class );
        when( SignalementUtils.getProperties( Mockito.anyString( ) ) ).thenReturn( possibleSignalementOrigins );

        // PowerMockito.mockStatic(SignalementConstants.class);
        // when(SignalementConstants.SIGNALEMENT_PREFIXES).thenReturn( possibleSignalementOrigins );

        when( _typeSignalementService.findByIdTypeSignalement( JSON_VALUE_INCIDENT_CATEGORIE_ID_INT ) ).thenReturn( new TypeSignalement( ) );
        when( _adresseService.getArrondissementByGeom( JSON_VALUE_LONGITUDE_DOUBLE, JSON_VALUE_LATITUDE_DOUBLE ) ).thenReturn( new Arrondissement( ) );
        when( _adresseService.getSecteurByGeomAndTypeSignalement( JSON_VALUE_LONGITUDE_DOUBLE, JSON_VALUE_LATITUDE_DOUBLE,
                JSON_VALUE_INCIDENT_CATEGORIE_ID_INT ) ).thenReturn( new Sector( ) );

        Signalement signalement = new Signalement( );
        Signaleur signaleur = new Signaleur( );
        Adresse adresse = new Adresse( );

        signalementRestService.parseSignalement( jsonSrc, signalement, signaleur, adresse );

        assertTrue( signalement.getCommentaire( ).equals( JSON_VALUE_INCIDENT_DESCRIPTIVE ) );

        Calendar actualTime = Calendar.getInstance( );
        // assertTrue( ( signalement.getMois( ).charAt( 0 ) - 65 ) == actualTime.get( Calendar.MONTH ) );
    }
}
