/*
 * $Revision$
 * $Date$
 *
 */
package org.opoo.apps.dv.provider;

import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.dv.ConvertableObject;

public interface ConvertibleObjectProvider {

    /**
     * Get the name of the file associated with the binary body.
     *
     * @return
     */
    String getFilename();

    /**
     * Get the content type of the file associated with the binary body.
     *
     * @return
     */
    String getContentType();

    /**
     * Get the data from the binary body.
     *
     * @return
     */
    InputStream getStream() throws IOException;
    
    /**
     * Get version for the provider
     *
     * @return
     */
    int getRevisionNumber();

    /**
     * The lengh of the content
     *
     * @return
     */
    long getContentLength();

    /**
     * Get other versions of this convertable object by number
     *
     * @param version
     * @return
     */
    ConvertableObject getVersion(int version);


    /**
     * Returns whether the effective user is alloed to convert the convertable object
     *
     */
    boolean isAllowedToConvert();

    /**
     * Modify the stream with some metadata informatin for download     
     *
     */
    SizedInputStream getModifiedStream() throws IOException;

    /**
     * A wrapper around modified input stream and its size
     */
    public class SizedInputStream {

        private InputStream inputStream;
        private long length;

        public SizedInputStream(InputStream is, long length) {
            this.inputStream = is;
            this.length = length;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public long getLength() {
            return length;
        }
    }
}
