package org.opoo.apps.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.opoo.apps.service.MimeTypeManager;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;
import org.springframework.beans.factory.annotation.Required;


public class MimeTypeManagerImpl implements MimeTypeManager {

    private static final Logger log = LogManager.getLogger(MimeTypeManagerImpl.class);

    protected MagicMimeTypeIdentifier magicMimeTypeIdentifier;
    
    
    @Required
    public void setMagicMimeTypeIdentifier(MagicMimeTypeIdentifier magicMimeTypeIdentifier) {
		this.magicMimeTypeIdentifier = magicMimeTypeIdentifier;
	}

	public String getFileMimeType(String fileName, String providedMimeType, InputStream fileInputStream) throws
            IOException
    {
        String determinedMimeType = determineMimeType(null, fileName, fileInputStream);
        System.out.println("determinedMimeType: " + determinedMimeType);
        return determinedMimeType != null ? determinedMimeType : providedMimeType;
    }

    public String getRemoteResourceMimeType(String url, String providedMimeType, InputStream remoteResourceInputStream)
            throws IOException
    {
        String determinedMimeType = determineMimeType(new URIImpl(url), null, remoteResourceInputStream);
        return determinedMimeType != null ? determinedMimeType : providedMimeType;
    }

    public String getExtensionMimeType(String fileName) {
        if(fileName == null){
            return null;
        }
        //try aperture first
        String type = magicMimeTypeIdentifier.identify(null, fileName, null);
        log.debug("magicMimeTypeIdentifier.identify type: " + type);
        if (type == null){
            //fallback to mime type map from javax.activation
            MimetypesFileTypeMap mimeTypes = null;
            String mimeTypesPath = "META-INF" + File.separator + "mime.types";
            try {
                mimeTypes = new MimetypesFileTypeMap(mimeTypesPath);
            }
            catch (IOException e) {
                log.warn("Failed to load " + mimeTypesPath + ": " + e.getMessage());
                mimeTypes = new MimetypesFileTypeMap();
            }
            type = mimeTypes.getContentType(fileName.toLowerCase());
        }
        return type;
    }
    
    public String getMimeType(String fileName){
    	MimetypesFileTypeMap mimeTypes = null;
        String mimeTypesPath = "META-INF" + File.separator + "mime.types";
        try {
            mimeTypes = new MimetypesFileTypeMap(mimeTypesPath);
        }
        catch (IOException e) {
            log.warn("Failed to load " + mimeTypesPath + ": " + e.getMessage());
            mimeTypes = new MimetypesFileTypeMap();
        }
        return mimeTypes.getContentType(fileName.toLowerCase());
    }

    protected String determineMimeType(URI uri, String fileName, InputStream content) throws IOException {
        MagicMimeTypeIdentifier typeIdentifier = magicMimeTypeIdentifier;//magicMimeTypeIdentifierProvider.get();
        byte [] firstBytes = new byte[typeIdentifier.getMinArrayLength()];

        content.mark(firstBytes.length);
        try {
            int read = content.read(firstBytes, 0, firstBytes.length);
            return read < firstBytes.length ? typeIdentifier.identify(null, fileName, uri)
                    : typeIdentifier.identify(firstBytes, fileName, uri);
        }
        finally {
            content.reset();
        }
    }

    public void destroy() {

    }
}

