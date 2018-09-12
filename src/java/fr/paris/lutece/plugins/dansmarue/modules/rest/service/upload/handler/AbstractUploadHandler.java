package fr.paris.lutece.plugins.dansmarue.modules.rest.service.upload.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import fr.paris.lutece.plugins.asynchronousupload.service.AbstractAsynchronousUploadHandler;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.util.filesystem.UploadUtil;

/**
 *
 * AbstractUploadHandler
 *
 */
public abstract class AbstractUploadHandler extends AbstractAsynchronousUploadHandler
{

    @Value( "${signalement.directory.upload}" )
    protected String uploadDirectory;

    @Value( "${signalement.upload.max.file.size}" )
    private String   maxFileSize;

    @Value( "${signalement.upload.max.category.files.count}" )
    private Integer  maxCategoryFilesCount;

    public String getMaxFileSize( )
    {
        return maxFileSize;
    }

    private static final String PREFIX_ENTRY_ID   = "dansmarue_";
    private static final Log  LOGGER            = LogFactory.getLog( AbstractUploadHandler.class );
    public static final String  ERROR_EXTENSION   = "module.dansmarue.rest.message.upload.file.error.extension";
    public static final String  ERROR_FILES_COUNT = "module.dansmarue.rest.message.upload.file.error.files.count";
    private static final String HANDLER_NAME      = "DansmarueAsynchronousUploadHandler";

    private static List<String> extensionList     = Arrays.asList( "jpeg", "png", "jpg" );

    /**
     * Constructor
     */
    public AbstractUploadHandler( )
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFileItemToUploadedFilesList( FileItem fileItem, String strFieldName, HttpServletRequest request )
    {
        String strFileName = UploadUtil.cleanFileName( fileItem.getName( ).trim( ) );

        initMap( request.getSession( ), PREFIX_ENTRY_ID + strFieldName );

        List<FileItem> uploadedFiles = getListUploadedFiles( strFieldName, request.getSession( ) );

        if ( uploadedFiles != null )
        {
            boolean bNew = true;

            if ( !uploadedFiles.isEmpty( ) )
            {
                Iterator<FileItem> iterUploadedFiles = uploadedFiles.iterator( );

                while ( bNew && iterUploadedFiles.hasNext( ) )
                {
                    FileItem uploadedFile = iterUploadedFiles.next( );
                    String strUploadedFileName = UploadUtil.cleanFileName( uploadedFile.getName( ).trim( ) );
                    bNew = !( StringUtils.equals( strUploadedFileName, strFileName ) && ( uploadedFile.getSize( ) == fileItem.getSize( ) ) );
                }
            }

            if ( bNew )
            {
                uploadedFiles.add( fileItem );
            }
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String canUploadFiles( HttpServletRequest request, String strFieldName, List<FileItem> listFileItemsToUpload, Locale locale )
    {
        if ( StringUtils.isNotBlank( strFieldName ) )
        {

            if ( getListUploadedFiles( strFieldName, request.getSession( ) ).size( ) >= maxCategoryFilesCount )
            {
                return I18nService.getLocalizedString( ERROR_FILES_COUNT, new Object[] { maxCategoryFilesCount }, locale );
            }

            for ( FileItem fileItem : listFileItemsToUpload )
            {
                if ( !extensionList.contains( FilenameUtils.getExtension( fileItem.getName( ).toLowerCase( ) ) ) )
                {
                    return I18nService.getLocalizedString( ERROR_EXTENSION, locale ) + StringUtils.join( extensionList.toArray( ), ", " );
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHandlerName( )
    {

        return HANDLER_NAME;
    }

    /**
     * return picture file.
     * @param request
     *           the http request.
     * @param strFieldName
     *          the field name.
     * @return the file item
     */
    public FileItem getFile( HttpServletRequest request, String strFieldName )
    {
        if ( StringUtils.isNotBlank( strFieldName ) )
        {
            List<FileItem> listUploadedFileItems = getListUploadedFiles( strFieldName, request.getSession( ) );

            if ( !listUploadedFileItems.isEmpty( ) )
            {
                return listUploadedFileItems.get( 0 );
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileItem> getListUploadedFiles( String strFieldName, HttpSession session )
    {
        if ( StringUtils.isBlank( strFieldName ) )
        {
            throw new AppException( "id field name is not provided for the current file upload" );
        }

        initMap( session, strFieldName );

        Map<String, List<FileItem>> mapFileItemsSession = getMapAsynchronousUpload( ).get( session.getId( ) );

        return mapFileItemsSession.get( strFieldName );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFileItem( String strFieldName, HttpSession session, int nIndex )
    {
        List<FileItem> uploadedFiles = getListUploadedFiles( strFieldName, session );
        remove( strFieldName, session, nIndex, uploadedFiles );

    }

    private void initMap( HttpSession session, String strFieldName )
    {
        String strSessionId = session.getId( );
        Map<String, List<FileItem>> mapFileItemsSession = getMapAsynchronousUpload( ).get( strSessionId );

        if ( mapFileItemsSession == null )
        {
            synchronized ( this )
            {
                mapFileItemsSession = getMapAsynchronousUpload( ).get( strSessionId );

                if ( mapFileItemsSession == null )
                {
                    mapFileItemsSession = new ConcurrentHashMap<String, List<FileItem>>( );
                    getMapAsynchronousUpload( ).put( strSessionId, mapFileItemsSession );
                }
            }
        }

        List<FileItem> listFileItems = mapFileItemsSession.get( strFieldName );

        if ( listFileItems == null )
        {
            listFileItems = new ArrayList<>( );
            mapFileItemsSession.put( strFieldName, listFileItems );
        }
    }

    private void removeFile( HttpSession session, FileItem fileToSave, String categoryName )
    {
        String id = getIdInSession( session );
        if ( ( null == id ) || ( null == fileToSave ) || StringUtils.isEmpty( categoryName ) )
        {
            return;
        }

        String categoryPath = FilenameUtils.concat( getUploadDirectory( ), id );
        categoryPath = FilenameUtils.concat( categoryPath, categoryName );

        String fileName = FilenameUtils.getName( fileToSave.getName( ) );
        try
        {
            String filePath = FilenameUtils.concat( categoryPath, fileName );
            Path path = Paths.get( filePath );
            Files.deleteIfExists( path );

            LOGGER.info( "Fichier supprimé avec succès : " + fileName );
        } catch ( IOException ex )
        {
            LOGGER.error( "Impossible de supprimer le fichier : " + fileName, ex );
        }
    }

    private void remove( String strFieldName, HttpSession session, int nIndex, List<FileItem> uploadedFiles )
    {
        if ( ( uploadedFiles != null ) && !uploadedFiles.isEmpty( ) && ( uploadedFiles.size( ) > nIndex ) )
        {
            FileItem fileItem = uploadedFiles.remove( nIndex );
            removeFile( session, fileItem, strFieldName );
            fileItem.delete( );
        }
    }

    public boolean hasFile( HttpServletRequest request, String strFieldName )
    {
        if ( StringUtils.isNotBlank( strFieldName ) )
        {
            List<FileItem> listUploadedFileItems = getListUploadedFiles( strFieldName, request.getSession( ) );

            if ( !listUploadedFileItems.isEmpty( ) )
            {
                return true;
            }
        }
        return false;
    }

    public abstract String getIdInSession( HttpSession session );

    public abstract String getUploadDirectory( );

    abstract Map<String, Map<String, List<FileItem>>> getMapAsynchronousUpload( );
}
