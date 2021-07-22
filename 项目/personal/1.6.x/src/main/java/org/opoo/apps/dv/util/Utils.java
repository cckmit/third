package org.opoo.apps.dv.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;

public class Utils {
	private static final Log log = LogFactory.getLog(Utils.class);
	
	public static final String DEFAULT_INSTANCE_ID = "NA";
	private static boolean devMode = false;
	private static final AtomicInteger jobID = new AtomicInteger();
	 
	/**
	 * 生成转换过程中所需的Job ID信息。
	 * @return
	 */
	public static String createJobID(){
		 //return UUID.randomUUID().toString();
		//return "JOB-" + JOB_ID_GENERATOR.getNext();
		return Integer.toString(jobID.incrementAndGet());
	}
	
	public static final String getInstanceID(){
		if(devMode){
			//log.debug("devMode is true, return 'NA' as instanceID.");
			return DEFAULT_INSTANCE_ID;
		}
		String instanceID = AppsGlobals.getProperty(AppsGlobals.APPS_INSTANCE_ID);
		if(instanceID != null){
			return instanceID;
		}else{
			log.warn("No instanceID in apps runtime properties, using 'NA' as default.");
			return DEFAULT_INSTANCE_ID;
		}
//		if(AppsGlobals.failedLoading){
//			return DEFAULT_INSTANCE_ID;
//		}else{
//			throw new IllegalStateException();
//		}
	}
	
	public static File createTempFile(String prefix) throws IOException {
        File tmpDir = getTemp();
        return File.createTempFile(prefix, String.valueOf(System.currentTimeMillis()), tmpDir);
    }
	
	public static File createTempFile(String prefix, String suffix)	throws IOException {
		File tmpDir = getTemp();
		return File.createTempFile(prefix, suffix, tmpDir);
	}
	
	public static File createTempFile(Object caller) throws IOException {
        File tmpDirectory = getTemp();
        return File.createTempFile(caller.getClass().getSimpleName(), String.valueOf(caller.hashCode()), tmpDirectory);
    }
	
	private static File getTemp(){
		if(devMode){
			//log.debug("devMode is true, use system temp directory as application's temp directory.");
			return new File(System.getProperty("java.io.tmpdir"));
		}
//		try{
		return AppsHome.getTemp();
//		}catch(Exception e){
//			return new File(System.getProperty("java.io.tmpdir"));
//		}
	}
	
	public static void setDevMode(boolean devMode){
		Utils.devMode = devMode;
	}

	public static boolean isDevMode() {
		return devMode;
	}
}
