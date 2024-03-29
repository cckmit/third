

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class JockyJavac {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		File f = new File("JockyJavac.args.txt");
//		File fo = new File("JockyJavac.args.o.txt");
//		
//		FileReader r = new FileReader(f);
//		BufferedReader reader = new BufferedReader(r);
//		FileWriter w = new FileWriter(fo);
//		BufferedWriter writer = new BufferedWriter(w);
//		
//		System.out.println(f.getAbsolutePath());
//		String line = reader.readLine();
//		boolean skipLine = false;
//		while(line != null){
//			System.out.println(line);
//			if(skipLine){
//				System.out.println("Skiping '-s' arg line ...");
//				skipLine = false;
//			}else if("\"-s\"".equals(line)){
//				System.out.println("Skiping '-s' line ...");
//				skipLine = true;
//			}else{
//				writer.write(line);
//				writer.newLine();
//			}
//			line = reader.readLine();
//		}
//		
//		writer.flush();
//		writer.close();
//		reader.close();
//		
//		System.out.println(fo.getAbsolutePath());
//		String string = fo.getAbsolutePath();
//		string = string.replaceAll("\\\\", "/");
//		System.out.println("@" + string);
//		
//		if(true){
//			return;
//		}
		if(args.length > 0){
			String s = args[0];
			if(s != null && s.length() > 1 && s.startsWith("@")){
				File file = new File(s.substring(1));
				if(file.exists() && file.canRead()){
					copyFile(file, new File("JockyJavac.args.txt"));
					File fo = new File("JockyJavac.args.o.txt");
					s = newArgs(fo);
					copyNewArgsFile(file, fo);
					args[0] = s;
					System.out.println("New arguments file: " + s);
				}
			}
		}
		//TODO com.sun.tools.javac.Main.main(args);
	}
	
	private static void copyNewArgsFile(File f, File fo) throws IOException{
		FileReader r = new FileReader(f);
		BufferedReader reader = new BufferedReader(r);
		FileWriter w = new FileWriter(fo);
		BufferedWriter writer = new BufferedWriter(w);
		
		String line = reader.readLine();
		boolean skipLine = false;
		while(line != null){
			System.out.println(line);
			if(skipLine){
				System.out.println("Skiping '-s' arg line ...");
				skipLine = false;
			}else if("\"-s\"".equals(line)){
				System.out.println("Skiping '-s' line ...");
				skipLine = true;
			}else{
				writer.write(line);
				writer.newLine();
			}
			line = reader.readLine();
		}
		writer.flush();
		reader.close();
		r.close();
		writer.close();
		w.close();
	}
	
	
	private static String newArgs(File fo){
		String string = fo.getAbsolutePath();
		string = string.replaceAll("\\\\", "/");
//		System.out.println("@" + string);
		return "@" + string;
	}
	
	
	
	 /**
     * Copies a file to a new location preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to the
     * specified destination file. The directory holding the destination file is
     * created if it does not exist. If the destination file exists, then this
     * method will overwrite it.
     * 
     * @param srcFile  an existing file to copy, must not be <code>null</code>
     * @param destFile  the new file, must not be <code>null</code>
     * 
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFileToDirectory(File, File)
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to a new location.
     * <p>
     * This method copies the contents of the specified source file
     * to the specified destination file.
     * The directory holding the destination file is created if it does not exist.
     * If the destination file exists, then this method will overwrite it.
     *
     * @param srcFile  an existing file to copy, must not be <code>null</code>
     * @param destFile  the new file, must not be <code>null</code>
     * @param preserveFileDate  true if the file date of the copy
     *  should be the same as the original
     *
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFileToDirectory(File, File, boolean)
     */
    public static void copyFile(File srcFile, File destFile,
            boolean preserveFileDate) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (srcFile.exists() == false) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        if (destFile.getParentFile() != null && destFile.getParentFile().exists() == false) {
            if (destFile.getParentFile().mkdirs() == false) {
                throw new IOException("Destination '" + destFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }
	
    /**
     * Internal copy file method.
     * 
     * @param srcFile  the validated source file, must not be <code>null</code>
     * @param destFile  the validated destination file, must not be <code>null</code>
     * @param preserveFileDate  whether to preserve the file date
     * @throws IOException if an error occurs
     */
    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream input = new FileInputStream(srcFile);
        try {
            FileOutputStream output = new FileOutputStream(destFile);
            try {
                copy(input, output);
            } finally {
                closeQuietly(output);
            }
        } finally {
            closeQuietly(input);
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
    
    
    // copy from InputStream
    //-----------------------------------------------------------------------
    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     * Large streams (over 2GB) will return a bytes copied value of
     * <code>-1</code> after the copy has completed since the correct
     * number of bytes cannot be returned as an int. For large streams
     * use the <code>copyLarge(InputStream, OutputStream)</code> method.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @throws ArithmeticException if the byte count is too large
     * @since Commons IO 1.1
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    
    
    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.3
     */
    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    /**
     * The default buffer size to use.
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    

    /**
     * Unconditionally close an <code>OutputStream</code>.
     * <p>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param output  the OutputStream to close, may be null or already closed
     */
    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    /**
     * Unconditionally close an <code>InputStream</code>.
     * <p>
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param input  the InputStream to close, may be null or already closed
     */
    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
