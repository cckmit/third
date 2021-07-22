package org.opoo.apps.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opoo.apps.AppsHome;
import org.opoo.util.AutoCloseInputStream;

/**
 * Utils for file handling
 */
public abstract class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Logger log = Logger.getLogger(FileUtils.class);

//    private FileUtils() {
//    }

    /**
     * Creates a temporary file in AppsHome.getTemp() with the provided prefix and the current time in millis as a suffix
     *
     * @param prefix Desired prefix of the filename
     * @return The file that was created
     * @throws IOException Thrown if the file could not be created.
     */
    public static File createTempFile(String prefix) throws IOException {
        File tmpDir = AppsHome.getTemp();

        return File.createTempFile(prefix, String.valueOf(System.currentTimeMillis()), tmpDir);
    }
    
    /**
     * Creates a temporary file.
     *
     * @param caller Object that is calling this method, used for prefix and suffix info.
     * @return The file that was created.
     * @throws IOException Thrown if the file could not be created.
     */
    public static File createTempFile(Object caller) throws IOException {
        File tmpDirectory = AppsHome.getTemp();

        return File.createTempFile(caller.getClass().getSimpleName(), String.valueOf(caller.hashCode()), tmpDirectory);
    }

    public static String loadFile(InputStream is) throws Exception {
        InputStreamReader reader = null;
        StringBuilder stb = null;
        try {
            reader = new InputStreamReader(is);
            stb = new StringBuilder();
            int i = -1;
            while ((i = reader.read()) != -1) {
                stb.appendCodePoint(i);
            }
        }
        finally {
            reader.close();
        }
        return stb.toString();
    }

    public static String loadFile(String location) throws Exception {
        File file = new File(location);
        FileReader reader = new FileReader(file);
        StringBuilder stb = new StringBuilder((int) file.length());
        int i = -1;
        while ((i = reader.read()) != -1) {
            stb.appendCodePoint(i);
        }
        reader.close();
        return stb.toString();
    }

    public static void cleanOutHiddenFiles(File dir, boolean goDeep) throws Exception {
        if (dir == null) {
            throw new Exception("dir must not be null");
        }
        if (!dir.exists()) {
            throw new Exception("dir does not exist: " + dir.getPath());
        }
        if (!dir.isDirectory()) {
            throw new Exception("Provided dir must be a directory: " + dir.getPath());
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isHidden() || file.getName().startsWith(".")) {
                try {
                    if (file.isDirectory()) {
                        org.apache.commons.io.FileUtils.deleteDirectory(file);
                    }
                    else {
                        if (!file.delete()) {
                            throw new Exception();
                        }
                    }
                }
                catch (Throwable e) {
                    throw new Exception("Unable to delete hidden file: " + file.getPath());
                }
            }
            else if (goDeep && file.isDirectory()) {
                cleanOutHiddenFiles(file, goDeep);
            }
        }
    }

    /**
     * Copies the source file to the destination file
     *
     * @param src - the source file
     * @param dest - the destination file
     * @throws IOException if there is a problem copying the files
     */
    public static void copyFileToFile(File src, File dest) throws IOException {
        // Create channel on the source
        FileChannel srcChannel = new FileInputStream(src).getChannel();

        // Create channel on the destination
        FileChannel dstChannel = new FileOutputStream(dest).getChannel();

        // Copy file contents from source to destination
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

        // Close the channels
        srcChannel.close();
        dstChannel.close();
    }

    /**
     * Inserts path separators between each node in a string list except the last and returns a path representing the
     * joined string.
     *
     * @param nodes
     * @return
     */
    public static String join(List<String> nodes) {
        StringBuilder buffer = new StringBuilder(512);
        for (String node : nodes) {
            buffer.append(node).append(File.separator);
        }
        return buffer.substring(0, buffer.length() - 1);
    }

    /**
     * Performs a join as in <em>FileUtils.join</em> but appends a sensible OS-specific default to the initial path.
     *
     * @param nodes Nodes to join
     * @param drivePrefix prefix to append when in Windows
     * @return
     */
    public static String absoluteJoin(List<String> nodes, String drivePrefix) {
        List<String> newList = new LinkedList<String>(nodes);
        if (System.getProperty("os.name", "Windows").indexOf("Windows") != -1) {
            if (drivePrefix == null) {
                newList.add(0, "c:");
            }
            else {
                newList.add(0, drivePrefix);
            }
        }
        else {
            newList.add(0, "/");
        }
        return join(newList);
    }

    /**
     * Same as absoluteJoin with the default that if on Windows, the drive will default to "c:"
     *
     * @param nodes
     * @return
     */
    public static String absoluteJoin(List<String> nodes) {
        return absoluteJoin(nodes, "c:");
    }

    public static byte[] loadFileData(InputStream f) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
        try {
            int i = -1;
            while ((i = f.read()) != -1) {
                out.write(i);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                f.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    /**
     * Save the text to the destination file
     *
     * @param text - text to sace
     * @param file - the destination file
     */
    public static void saveText(String text, File file) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.append(text).flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (Exception e) {
                }
            }
        }
    }

    public static InputStream newFileInputStream(String file) throws FileNotFoundException {
        return newFileInputStream(new FileInputStream(new File(file)));
    }

    public static InputStream newFileInputStream(File file) throws FileNotFoundException {
        return newFileInputStream(new FileInputStream(file));
    }

    public static InputStream newFileInputStream(InputStream in) {
        return new AutoCloseInputStream(in);
    }

    public static boolean waitForFile(File file, int timeout) {
        log.info("Pausing for file: " + file.getAbsolutePath() + " for " + timeout + " ms");
        long time = System.currentTimeMillis();
        while(((System.currentTimeMillis() - time) < timeout) && !file.exists() && !canRead(file.getParentFile(), file)){
            try {
                log.info("Waiting for file at" + file.getAbsolutePath());
                Thread.sleep(Math.max(500, timeout/10 > 10000 ? 10000 : timeout/10));
            }
            catch (InterruptedException e) {
            }
        }
        log.info("Stopped pausing for file: " + file.getAbsolutePath() + " after " + (System.currentTimeMillis() - time) + " ms");
        return file.exists();
    }

    private static boolean canRead(File parent, File file) {
        if (canRead(file)) {
            return true;
        }else{
            //Unbelievable hack for NFS mounted shares
            log.debug(String.format("Could not read file: [%s]. Refresh file handles from parent directory [%s] and attempt to load file again", file.getAbsolutePath(), parent.getAbsolutePath()));
            File[] files = parent.listFiles();
            if(canRead(file)){
                return true;
            }
            //do nothing
            log.debug(files);
        }
        return false;
    }

    private static boolean canRead(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int i = fis.read();
            if(i != -1){
                log.info("Successfully opened file for read:" + file.getAbsolutePath());
                return true;
            }
            fis.close();
        }
        catch (Exception e) {
            log.error(String.format("Unable to read file: %s", file.getAbsolutePath()));
        }
        finally {
            if(fis != null){
                try { fis.close();} catch (Exception e) {}
            }
        }
        return false;
    }
    
    
    /**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(final InputStream is) throws IOException{
		StringBuffer result = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = in.readLine()) != null) {
			result.append(line).append("\n");
		}
		return result.toString();
	}
}
