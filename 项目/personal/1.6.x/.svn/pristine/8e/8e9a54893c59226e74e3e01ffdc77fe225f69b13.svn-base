package org.opoo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Callable which will read from an input stream and write to an output stream.
 * 
 * @since 1.5
 * @author Alexander Wenckus
 */
public class InputOutputStreamWrapper implements Callable<Object> {
    private static final int DEFAULT_BUFFER_SIZE = 8000;

    private long amountWritten = 0;
    private final int bufferSize;
    private final InputStream in;
    private final OutputStream [] outs;
    private final boolean closeStreamsOnCompletion;

    public InputOutputStreamWrapper(int bufferSize, boolean closeStreamsOnCompletion, InputStream in,
            OutputStream... outputStreams)
    {
        if (bufferSize <= 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        this.bufferSize = bufferSize;
        this.closeStreamsOnCompletion = closeStreamsOnCompletion;
        this.in = in;
        this.outs = outputStreams;
    }

    public InputOutputStreamWrapper(int bufferSize, InputStream in, OutputStream... outputStreams) {
        this(bufferSize, false, in, outputStreams);
    }

    public InputOutputStreamWrapper(InputStream in, OutputStream... outputStreams) {
        this(DEFAULT_BUFFER_SIZE, in, outputStreams);
    }

    public InputOutputStreamWrapper(boolean closeStreamsOnCompletion, InputStream in, OutputStream... outputStreams) {
        this(DEFAULT_BUFFER_SIZE, closeStreamsOnCompletion, in, outputStreams);
    }

    public Object call() throws Exception {
        return doWrap();
    }

    public long doWrap() throws IOException {
        final byte[] b = new byte[bufferSize];
        int count = 0;
        amountWritten = 0;

        do {
            // write to the output stream
            for (OutputStream out : outs) {
                out.write(b, 0, count);
            }

            amountWritten += count;

            // read more bytes from the input stream
            count = in.read(b);
        }
        while (count >= 0);

        for (OutputStream out : outs) {
            out.flush();
        }
        if (closeStreamsOnCompletion) {
            try {
                closeStreams();
            }
            catch (IOException ioe) {
                // DO NOTHING
            }
        }
        return amountWritten;
    }

    public InputOutputStreamWrapper closeStreams() throws IOException {
        try {
            in.close();
        }
        finally {
            for (OutputStream out : outs) {
                try {
                    out.close();
                }
                catch (IOException ioe) {
                    // do nothing
                }
            }
        }
        return this;
    }

    public long getAmountWritten() {
        return amountWritten;
    }
}
