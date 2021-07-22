package org.opoo.apps.log;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.opoo.apps.AppsHome;

/**
 * Log4J日志配置。
 * 重新配置日志文件，将日志写在appsHome中。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class Log4JConfigurator {

	public static final String LOG4J_CONFIG_KEY = "apps.pathToLog4jConfig";

	private Log4JConfigurator() {
	}
	
	/**
	 * 设置系统属性并重新加载日志配置。
	 */
	public static void configureAppsLogsPath() {
		File logDirectory = AppsHome.getAppsLogs();
		Logger log = LogManager.getLogger(Log4JConfigurator.class);
		initializeSystemProperties();
		if (!logDirectory.exists() || !logDirectory.isDirectory() || !logDirectory.canWrite()) {
			try {
				log.warn((new StringBuilder()).append("Unable to write to configured log directory: '").append(logDirectory.getAbsolutePath()).toString());
				logDirectory = File.createTempFile("apps", "log");
				logDirectory.delete();
				logDirectory.mkdirs();
				System.setProperty("apps.logs", logDirectory.getAbsolutePath());
				log.warn((new StringBuilder()).append("File logging will be written to '").append(logDirectory.getAbsolutePath()).append("'.").toString());
			} catch (IOException ioe) {
				System.err.println("Failed to establish core logging after invalid environment configuration. Logging will be unstable and likely not visible from the admin console.");
				ioe.printStackTrace(System.err);
			}
		}
		try {
			File logExtension;
			String log4jConfig = System.getProperty(LOG4J_CONFIG_KEY);
			if (log4jConfig != null)
				logExtension = new File(log4jConfig);
			else
				logExtension = new File(AppsHome.getEtc(), "log4j.properties");

			//System.out.println("Log Extension: " + logExtension);
			
			boolean customized = false;
			if (logExtension.exists()) {
				log.info("Load log config from: " + logExtension);
				PropertyConfigurator.configure(logExtension.getAbsolutePath());
				customized = true;
			} else {
				//File file = ResourceUtils.getFile("classpath:log4j-file.properties");
				//PropertyConfigurator.configure(file.getAbsolutePath());
				
				PropertyResourceBundle bundle = (PropertyResourceBundle)ResourceBundle.getBundle("log4j-file");
                Enumeration<String> keys = bundle.getKeys();
                Properties props = new Properties();
                String key;
                for(; keys.hasMoreElements(); props.put(key, bundle.getObject(key)))
                    key = keys.nextElement();

                if(props.size() > 0){
                	PropertyConfigurator.configure(props);
                }
				
			}
			if (!customized) {
				//TODO 根据主配置文件设置日志级别
				/*
				try {
					if (AppsGlobals.isSetup()) {
						//暂时不设置
						//String thresholdLevel = AppsGlobals.getCurrentThreshold();
						//AppsGlobals.setCurrentThreshold(thresholdLevel);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}*/
				log.warn("File logging online.");
			} else {
				log.warn("Customized logging online.");
			}
		} catch (Exception ex) {
			System.err.println("Unhandled exception configuring logging. Logging may be inconsistent or non-existent.");
			ex.printStackTrace(System.err);
		}
	}

	private static void initializeSystemProperties() {
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
		//设置系统名称
		if (null == System.getProperty("apps.name")) {
			System.setProperty("apps.name", "apps");
		}
		//设置日志路径
		if (null == System.getProperty("apps.logs")) {
			System.setProperty("apps.logs", AppsHome.getAppsLogsPath());
		}
	}
}
