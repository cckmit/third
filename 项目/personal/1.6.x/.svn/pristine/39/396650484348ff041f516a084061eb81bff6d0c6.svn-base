package org.opoo.apps.service;

import java.io.IOException;
import java.io.InputStream;

public interface MimeTypeManager {
	/**
     * Determines the mime type for a given file. This is most generally a file uploaded directly to Jive SBS where we
     * have the user-provided file name and mime type to fall back on if a file type could not be determined from the
     * stream itself.
     *
     * @param fileName the file name provided by the user, the extension may be inspected to determine the type of the file.
     * @param providedMimeType the mime type provided by the user.
     * @param fileInputStream a mark-supported input stream the first several bytes will be read.
     * @return the determined content-type.
     * @throws IllegalArgumentException if the provided input stream does not support marking.
     * @throws java.io.IOException if there are issues reading from the provided stream
     */
    String getFileMimeType(String fileName, String providedMimeType, InputStream fileInputStream) throws IOException;

    /**
     * Determines the mime-type for a give remote file.
     * 
     * @param url the url of the remote file.
     * @param providedMimeType the provided mime-type, if one is available.
     * @param remoteResourceInputStream the mark-supported input stream.
     * @return the determined content-type
     * @throws IllegalArgumentException if the provided input stream does not support marking.
     * @throws java.io.IOException if there are issues reading from the provided stream.
     */
    String getRemoteResourceMimeType(String url, String providedMimeType, InputStream remoteResourceInputStream) throws
            IOException;

    /**
     * Returns the content type according to the extension of the given file name.
     *
     * @param filename the file name from which the content type is being determined.
     * @return the content type according to the extension of the given file name.
     */
    String getExtensionMimeType(String filename);
}
