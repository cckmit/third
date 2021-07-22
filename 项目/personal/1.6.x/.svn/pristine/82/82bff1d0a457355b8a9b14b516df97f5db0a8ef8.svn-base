package org.opoo.apps.file.converter;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

public class DefaultFileTypeConverterManagerTest extends TestCase {

	DefaultFileTypeConverterManager manager;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		manager = new DefaultFileTypeConverterManager();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}




	public void etestConvert(){
		
	}
	
	public void testVideoConverter(){
		//processFLV("d:\\ay.mpg", "d.ay.flv");
		runCMD();
	}
	
	private static void runCMD(){
		List<String> command = new java.util.ArrayList<String>();
		command.add("cmd");
		command.add("/C");
		command.add("dir");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.directory(new File("F:\\Downloads\\VideoConvert\\tool"));
			builder.command(command);
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private static boolean processFLV(String oldfilepath, String newfilepath) {
		List<String> commend = new java.util.ArrayList<String>();
		commend.add("ffmpeg.exe");
		commend.add("-i");
		commend.add(oldfilepath);
		commend.add("-ab");
		commend.add("64");
		commend.add("-acodec");
		commend.add("mp3");
		commend.add("-ac");
		commend.add("2");
		commend.add("-ar");
		commend.add("22050");
		commend.add("-b");
		commend.add("230");
		commend.add("-r");
		commend.add("24");
		commend.add("-y");
		commend.add(newfilepath);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.directory(new File("F:\\Downloads\\VideoConvert\\tool"));
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
