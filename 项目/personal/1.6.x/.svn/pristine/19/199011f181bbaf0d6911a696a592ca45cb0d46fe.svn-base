package org.opoo.apps.license;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;

/**
 * License 日志类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsLicenseLog implements Log{
	
	public static final AppsLicenseLog LOG = new AppsLicenseLog();
	
	public static final String LICENSE_LOG_ENABLED_KEY = "license.logEnabled";
	public static final String LICENSE_LOG_NAME_KEY = "license.logName";
	
	private final Log licenseLog = LogFactory.getLog(getLicenseLog4jCategory());
	
	/**
	 * 是否记录 License 相关的日志。
	 * 
	 * @return
	 */
	public static boolean isLogEnabled(){
		return AppsGlobals.getSetupProperty(LICENSE_LOG_ENABLED_KEY, false);
	}
	
	/**
	 * 用 Log4j 作为日志系统时的 Category 定义。
	 * @return
	 */
	public static String getLicenseLog4jCategory(){
		return AppsGlobals.getSetupProperty(LICENSE_LOG_NAME_KEY, "License");
	}
	
	public void debug(Object message) {
		if(isDebugEnabled()){
			licenseLog.debug(message);
		}
	}

	public void debug(Object message, Throwable t) {
		if(isDebugEnabled()){
			licenseLog.debug(message, t);
		}
	}

	public void error(Object message) {
		if(isErrorEnabled()){
			licenseLog.error(message);
		}
	}

	public void error(Object message, Throwable t) {
		if(isErrorEnabled()){
			licenseLog.error(message, t);
		}
	}

	public void fatal(Object message) {
		if(isFatalEnabled()){
			licenseLog.fatal(message);
		}
	}

	public void fatal(Object message, Throwable t) {
		if(isFatalEnabled()){
			licenseLog.fatal(message, t);
		}
	}

	public void info(Object message) {
		if(isInfoEnabled()){
			licenseLog.info(message);
		}
	}

	public void info(Object message, Throwable t) {
		if(isInfoEnabled()){
			licenseLog.info(message, t);
		}
	}

	public boolean isDebugEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.debugEnabled", false);			
	}

	public boolean isErrorEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.errorEnabled", false);			
	}

	public boolean isFatalEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.fatalEnabled", false);			
	}

	public boolean isInfoEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.infoEnabled", false);			
	}

	public boolean isTraceEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.traceEnabled", false);			
	}

	public boolean isWarnEnabled() {
		return isLogEnabled() || AppsGlobals.getSetupProperty("license.log.warnEnabled", false);			
	}

	public void trace(Object message) {
		if(isTraceEnabled()){
			licenseLog.trace(message);
		}
	}

	public void trace(Object message, Throwable t) {
		if(isTraceEnabled()){
			licenseLog.trace(message, t);
		}
	}

	public void warn(Object message) {
		if(isWarnEnabled()){
			licenseLog.warn(message);
		}
	}

	public void warn(Object message, Throwable t) {
		if(isWarnEnabled()){
			licenseLog.warn(message, t);
		}
	}
}
