package org.opoo.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.input.ProxyInputStream;
import org.apache.log4j.Logger;

/**
 * Proxy stream that closes and discards the underlying stream as soon as the end of input has been reached or when
 * the stream is explicitly closed. Not even a reference to the underlying stream is kept after it has been closed,
 * so any allocated in-memory buffers can be freed even if the client application still keeps a reference to the
 * proxy stream.
 * (Jive specific modification) Any IOException will also close the underlying stream. 
 * <p/>
 * This class is typically used to release any resources related to an open stream as soon as possible even if the
 * client application (by not explicitly closing the stream when no longer needed) or the underlying stream (by
 * not releasing resources once the last byte has been read) do not do that.
 *
 * @version $Id$
 * @since Commons IO 1.4
 */
public class AutoCloseInputStream extends ProxyInputStream {
    private static final Logger log = Logger.getLogger(AutoCloseInputStream.class);

    /**
     * Creates an automatically closing proxy for the given input stream.
     *
     * @param in underlying input stream
     */
    public AutoCloseInputStream(InputStream in) {
        super(in);
    }

    /**
     * Closes the underlying input stream and replaces the reference to it with a {@link ClosedInputStream}
     * instance.
     * <p/>
     * This method is automatically called by the read methods when the end of input has been reached.
     * <p/>
     * Note that it is safe to call this method any number of times. The original underlying input stream is closed
     * and discarded only once when this method is first called.
     *
     * @throws IOException if the underlying input stream can not be closed
     */
    @Override
    public void close() {
        try {
            in.close();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        finally {
        	in = ClosedInputStream.CLOSED;
        }
    }

    /**
     * Reads and returns a single byte from the underlying input stream. If the underlying stream returns -1, the
     * {@link #close()} method is called to automatically close and discard the stream.
     *
     * @return next byte in the stream, or -1 if no more bytes are available
     * @throws IOException if the stream could not be read or closed
     */
    @Override
    public int read() {
        try {
            int n = in.read();
            if (n == -1) {
                close();
            }
            return n;
        }
        catch (IOException e) {
        	log.error(e.getMessage(), e);
            close();
        }
        return -1;
    }

    /**
     * Reads and returns bytes from the underlying input stream to the given buffer. If the underlying stream
     * returns -1, the {@link #close()} method is called to automatically close and discard the stream.
     *
     * @param b buffer to which bytes from the stream are written
     * @return number of bytes read, or -1 if no more bytes are available
     * @throws IOException if the stream could not be read or closed
     */
    @Override
    public int read(byte[] b) {
        try {
            int n = in.read(b);
            if (n == -1) {
                close();
            }
            return n;
        }
        catch (IOException e) {
        	log.error(e.getMessage(), e);
            close();
        }
        return -1;
    }

    /**
     * Reads and returns bytes from the underlying input stream to the given buffer. If the underlying stream
     * returns -1, the {@link #close()} method is called to automatically close and discard the stream.
     *
     * @param b buffer to which bytes from the stream are written
     * @param off start offset within the buffer
     * @param len maximum number of bytes to read
     * @return number of bytes read, or -1 if no more bytes are available
     * @throws IOException if the stream could not be read or closed
     */
    @Override
    public int read(byte[] b, int off, int len) {
        try {
            int n = in.read(b, off, len);
            if (n == -1) {
                close();
            }
            return n;
        }
        catch (IOException e) {
        	log.error(e.getMessage(), e);
            close();
        }
        return -1;
    }

    /**
     * Ensures that the stream is closed before it gets garbage-collected. As mentioned in {@link #close()}, this is
     * a no-op if the stream has already been closed.
     *
     * @throws Throwable if an error occurs
     */
    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}