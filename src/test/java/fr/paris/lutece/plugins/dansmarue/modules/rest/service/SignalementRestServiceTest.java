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


@RunWith(PowerMockRunner.class)
@PrepareForTest( { SignalementConstants.class, AppPropertiesService.class, SignalementUtils.class })
public class SignalementRestServiceTest
{
    private static final String JSON_VALUE_UDID = "B52";
    private static final String JSON_VALUE_EMAIL = "junit@lutece.fr";

    private static final String JSON_VALUE_LATITUDE = "48.8468545";
    private static final double JSON_VALUE_LATITUDE_DOUBLE = new Double( JSON_VALUE_LATITUDE );
    private static final String JSON_VALUE_LONGITUDE = "2.369533";
    private static final double JSON_VALUE_LONGITUDE_DOUBLE = new Double( JSON_VALUE_LONGITUDE );

    private static final String JSON_VALUE_INCIDENT_CATEGORIE_ID = "5201";
    private static final int JSON_VALUE_INCIDENT_CATEGORIE_ID_INT = new Integer( JSON_VALUE_INCIDENT_CATEGORIE_ID );
    private static final String JSON_VALUE_INCIDENT_ADDRESS = "226 rue";
    private static final String JSON_VALUE_INCIDENT_DESCRIPTIVE = "Trou dans le sol";
    private static final Long JSON_VALUE_INCIDENT_PRIORITE_ID = 3L;
    private static final String JSON_VALUE_ORIGIN="A";

    /** Possible origin list **/
    private static final String[] SIGNALEMENT_POSSIBLE_ORIGINS = {"A","G","S","B"};

    JSONObject jsonSrc;
    private List<String> possibleSignalementOrigins;

    @Mock
    ITypeSignalementService _typeSignalementService;
    @Mock
    ISignalementService _signalementService;
    @Mock
    IAdresseService _adresseService;
    @Mock
    IPhotoService _photoService;
    @Mock
    ISignaleurService _signaleurService;
    @Mock
    IWorkflowService _signalementWorkflowService;
    @Mock
    RamenClientService _ramenClientService;
    @Mock
    IPrioriteService _prioriteService;

    @InjectMocks
    SignalementRestService signalementRestService = new SignalementRestService( );


    @Before
    public void init( )
    {
        JSONObject jsonIncident = new JSONObject( );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_CATEGORIE_ID, JSON_VALUE_INCIDENT_CATEGORIE_ID );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_ADDRESS, JSON_VALUE_INCIDENT_ADDRESS );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_DESCRIPTIVE, JSON_VALUE_INCIDENT_DESCRIPTIVE );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_PRIORITE_ID, JSON_VALUE_INCIDENT_PRIORITE_ID );
        jsonIncident.accumulate( JSON_TAG_INCIDENT_ORIGIN, JSON_VALUE_ORIGIN);

        JSONObject jsonPosition = new JSONObject( );
        jsonPosition.accumulate( JSON_TAG_LATITUDE, JSON_VALUE_LATITUDE );
        jsonPosition.accumulate( JSON_TAG_LONGITUDE, JSON_VALUE_LONGITUDE );

        jsonSrc = new JSONObject( );
        jsonSrc.accumulate( JSON_TAG_UDID, JSON_VALUE_UDID );
        jsonSrc.accumulate( JSON_TAG_EMAIL, JSON_VALUE_EMAIL );
        jsonSrc.accumulate( JSON_TAG_INCIDENT, jsonIncident );
        jsonSrc.accumulate( JSON_TAG_POSITION, jsonPosition );

        possibleSignalementOrigins = CollectionUtils.arrayToList(SIGNALEMENT_POSSIBLE_ORIGINS);

    }

    /**
     * Parse JSONObject into new Java Object that contains all informations
     * necessary for SignalementServiceRest.saveIncident()
     * @throws ParseSignalementFromJSONException throw in case of json exception
     */
    @Test
    public void parseIncidentFromJson( ) throws ParseSignalementFromJSONException
    {
        PowerMockito.mockStatic(AppPropertiesService.class);
        when(AppPropertiesService.getProperty(Mockito.anyString())).thenReturn("");

        PowerMockito.mockStatic(SignalementUtils.class);
        when(SignalementUtils.getProperties(Mockito.anyString())).thenReturn(possibleSignalementOrigins);


        //PowerMockito.mockStatic(SignalementConstants.class);
        //when(SignalementConstants.SIGNALEMENT_PREFIXES).thenReturn( possibleSignalementOrigins );

        when( _typeSignalementService.findByIdTypeSignalement( JSON_VALUE_INCIDENT_CATEGORIE_ID_INT ) ).thenReturn(
                new TypeSignalement( ) );
        when( _adresseService.getArrondissementByGeom( JSON_VALUE_LONGITUDE_DOUBLE, JSON_VALUE_LATITUDE_DOUBLE ) )
        .thenReturn( new Arrondissement( ) );
        when(
                _adresseService.getSecteurByGeomAndTypeSignalement( JSON_VALUE_LONGITUDE_DOUBLE,
                        JSON_VALUE_LATITUDE_DOUBLE, JSON_VALUE_INCIDENT_CATEGORIE_ID_INT ) ).thenReturn( new Sector( ) );



        Signalement signalement = new Signalement( );
        Signaleur signaleur = new Signaleur( );
        Adresse adresse = new Adresse( );

        signalementRestService.parseSignalement( jsonSrc, signalement, signaleur, adresse );

        assertTrue( signalement.getCommentaire( ).equals( JSON_VALUE_INCIDENT_DESCRIPTIVE ) );

        Calendar actualTime = Calendar.getInstance( );
        // assertTrue( ( signalement.getMois( ).charAt( 0 ) - 65 ) == actualTime.get( Calendar.MONTH ) );
    }
}
