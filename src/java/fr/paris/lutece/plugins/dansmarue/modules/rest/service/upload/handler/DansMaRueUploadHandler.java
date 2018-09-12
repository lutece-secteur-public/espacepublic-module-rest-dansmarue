package fr.paris.lutece.plugins.dansmarue.modules.rest.service.upload.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

/**
 *
 * DansMaRueUploadHandler
 *
 */
public class DansMaRueUploadHandler extends AbstractUploadHandler{
    public static final String DMR_UPLOAD_HANDLER = "DMRUploadHandler";

    public static final String SESSION_DEMANDE_ID = "demandeSignalement";

    private static Map<String, Map<String, List<FileItem>>> mapAsynchronousUpload = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdInSession(HttpSession session) {
        Object numDemande = session.getAttribute(SESSION_DEMANDE_ID);
        return numDemande != null ? numDemande.toString() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUploadDirectory() {
        return uploadDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Map<String, Map<String, List<FileItem>>> getMapAsynchronousUpload() {
        return mapAsynchronousUpload;
    }



}
