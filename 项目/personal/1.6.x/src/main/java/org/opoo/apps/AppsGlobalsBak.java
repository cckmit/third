/*
 * $Id: AppsGlobalsBak.java 13 2010-11-26 05:04:02Z alex $ 
 *
 * Copyright 2008 Alex Lin. All rights reserved.
 * Use is subject to license terms.
 * See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.imageio.IIOImage;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.opoo.apps.lifecycle.Application;
import org.opoo.util.ClassUtils;
import org.opoo.util.OpooProperties;
import org.opoo.util.OpooPropertiesSynchronizer;
import org.opoo.util.OpooPropertiesWrapper;
import org.opoo.util.XMLOpooProperties;

/****************
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import com.sun.imageio.plugins.gif.GIFImageWriter;
import com.sun.imageio.plugins.gif.GIFImageWriterSpi;
**************/

/**
 * 系统配置访问类。
 * 注意，由于Properties不能在集群中正确使用，所以此类也不能在集群下正常运行。
 * 
 * @author Alex
 * @deprecated
 * @exclude
 */
public class AppsGlobalsBak {
	
	
	public static final String DEV_MODE_KEY = "apps.devMode";
	
	
	//private static final StringManager messages = StringManager.getManager("org.opoo.apps");
	
	//private static String APPS_CONFIG_FILENAME = "apps_setup.xml";

	//private static String APPS_INIT_CONFIG = "apps_init.xml";
	
	//private static String APPS_BUILD_CONFIG = "apps_build.xml";
	
	private static final String DEFAULT_CHAR_ENCODING = "GBK";

	//public static String workHome = null;

	public static boolean failedLoading = false;

	private static Locale locale = null;

	private static TimeZone timeZone = null;

	private static String characterEncoding = null;

	private static DateFormat dateFormat = null;

	private static DateFormat dateTimeFormat = null;

	private static Date startupDate = new Date();

	/**
	 * 每个项目的本地化设置。
	 * 一般使用存储在数据库中的数据，访问频繁，数据量大。
	 * 这个设置文件是可读写的。
	 */
	private static OpooProperties<String, String> properties;

	/**
	 * 系统核心设置。这是系统启动后的默认设置。可读写。
	 * Local与setup中有相同属性的，local的优先级更高。
	 * 但当系统无法访问数据库时，此时只能使用setup的设置，
	 * 其次，系统setup相关的属性只存在于这个配置中，如系统
	 * 缓存目录、索引目录、附件保存目录、license等等信息。
	 */
	private static OpooProperties<String, String> setupProperties;


	private static Log log = LogFactory.getLog(AppsGlobalsBak.class);

//	/**
//	 * 系统初始化设置。一般是只读的。
//	 */
//	private static OpooProperties<String, String> initProperties;
//
//	private static OpooProperties<String, String> buildProperties;
	
	private static final OpooProperties<String, String> empty = new OpooPropertiesWrapper(new HashMap<String, String>());
	
	

	static {
		log .info(Version.getImplementationTitle() + ": "
				+ Version.getImplementationVersion() + " - by "
				+ Version.getImplementationVendor());
	}
	
	
	private AppsGlobalsBak() {
	}


	private static void loadLocalProperties() {
		if (properties == null) {
			OpooPropertiesSynchronizer sync = //AppsGlobals.getBeansHolder().get("localPropertiesSynchronizer", OpooPropertiesSynchronizer.class);
				Application.getContext().get("localPropertiesSynchronizer", OpooPropertiesSynchronizer.class);
			if(sync == null){
				String className = getSetupProperty("localPropertiesSynchronizer.className");
				if(className != null){
					sync = (OpooPropertiesSynchronizer) ClassUtils.newInstance(className);
				}
			}else{
				log.info("find localPropertiesSynchronizer from BeansHolder: " + sync);
			}
			/*
			String className = getSetupProperty("localPropertiesSynchronizer.className");
			
			if (className != null) {
				sync = (OpooPropertiesSynchronizer) ClassUtils.newInstance(className);
				//if (sync != null) {
				//	LocalProperties.setPropertiesSynchronizer(manager);
				//}
			}
			//properties = LocalProperties.getInstance();*/
			
			//maybe sync is null
			if(sync == null){
				if(AppsGlobalsBak.isSetup() && Application.isContextInitialized()){
					sync = Application.getContext().get("localPropertiesSynchronizer", OpooPropertiesSynchronizer.class);
				}else{
					throw new IllegalStateException("必须在系统安装和数据库应用启动后才能调用Local Properties的相关操作。");
				}
				//log.warn("未指定本地属性同步器，将使用默认的内存同步器，所设置的本地属性不能持久保存。");
			}
			
//			properties = new LocalProperties(sync);
		}
	}
	
	private static OpooProperties<String, String> getSetupProperties(){
		if(failedLoading){
			return empty;
		}
		if(setupProperties == null){
			File ah = AppsHome.getAppsHome();
			if(!ah.exists()){
				throw new IllegalArgumentException("The specified appsHome directory does not exist (" + ah.getAbsolutePath() + ")");
			}else if (!ah.canRead() || !ah.canWrite()){
				throw new IllegalArgumentException("运行该应用的用户对appsHome目录没有读或写的权限：" + ah.getAbsolutePath());
			}
			try {
				setupProperties = new XMLOpooProperties(AppsHome.getConfigurationFile().getAbsolutePath());
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return setupProperties;
	}


	
	

	public static Date getStartupDate() {
		return startupDate;
	}

	/**
	 * 
	 * @return
	 * @deprecated
	 */
	public static String getWorkHome() {
		return AppsHome.getAppsHome().getAbsolutePath();
	}

	public static Locale getLocale() {
		if (locale == null)
			if (properties != null) {
				String language = (String) setupProperties.get("locale.language");
				if (language == null)
					language = "";
				String country = (String) setupProperties.get("locale.country");
				if (country == null)
					country = "";
				if (language.equals("") && country.equals(""))
					locale = Locale.getDefault();
				else
					locale = new Locale(language, country);
			} else {
				return Locale.getDefault();
			}
		return locale;
	}

	public static void setLocale(Locale newLocale) {
		locale = newLocale;
		setSetupProperty("locale.country", locale.getCountry());
		setSetupProperty("locale.language", locale.getLanguage());
		//dateFormat = DateFormat.getDateInstance(2, locale);
		//dateTimeFormat = DateFormat.getDateTimeInstance(2, 2, locale);
		//dateFormat.setTimeZone(timeZone);
		//dateTimeFormat.setTimeZone(timeZone);
	}

	public static String getLocalProperty(String name) {
		String value = getProperty(name);
		if (value == null) {
			return getSetupProperty(name);
		}
		return value;
	}

	public static String getLocalProperty(String name, String defaultValue) {
		String v = getLocalProperty(name);
		if (v != null) {
			return v;
		}
		return defaultValue;
	}

	public static String getCharacterEncoding() {
		if (characterEncoding == null) {
			String encoding = getSetupProperty("locale.characterEncoding");

			if (encoding != null)
				characterEncoding = encoding;
			else
				characterEncoding = DEFAULT_CHAR_ENCODING;
		}
		return characterEncoding;
	}

	public static boolean isValidCharacterEncoding(String encoding) {
		boolean valid = true;
		try {
			"".getBytes(encoding);
		} catch (Exception e) {
			valid = false;
		}
		return valid;
	}

	public static void setCharacterEncoding(String characterEncoding)
			throws UnsupportedEncodingException {
		if (!isValidCharacterEncoding(characterEncoding)) {
			throw new UnsupportedEncodingException(
					"Invalid character encoding: " + characterEncoding);
		} else {
			AppsGlobalsBak.characterEncoding = characterEncoding;
			setSetupProperty("locale.characterEncoding", characterEncoding);
			return;
		}
	}

	public static TimeZone getTimeZone() {
		if (timeZone == null)
			if (properties != null) {
				String timeZoneID = (String) properties.get("locale.timeZone");
				if (timeZoneID == null)
					timeZone = TimeZone.getDefault();
				else
					timeZone = TimeZone.getTimeZone(timeZoneID);
			} else {
				return TimeZone.getDefault();
			}
		return timeZone;
	}

	public static void setTimeZone(TimeZone newTimeZone) {
		timeZone = newTimeZone;
		//dateFormat.setTimeZone(timeZone);
		//dateTimeFormat.setTimeZone(timeZone);
		setProperty("locale.timeZone", timeZone.getID());
	}

	private static void initDateFormat() {
		if (dateFormat == null) {
			String style = getLocalProperty("formatStyle.dateFormat");
			if (style != null) {
				dateFormat = new SimpleDateFormat(style);
			} else {
				dateFormat = DateFormat.getDateInstance(2, getLocale());
				dateFormat.setTimeZone(getTimeZone());
			}
		}
	}

	private static void initDateTimeFormat() {
		if (dateTimeFormat == null) {
			String style = getLocalProperty("formatStyle.dateTimeFormat");
			if (style != null) {
				dateTimeFormat = new SimpleDateFormat(style);
			} else {
				dateTimeFormat = DateFormat.getDateTimeInstance(2, 2, getLocale());
				dateTimeFormat.setTimeZone(getTimeZone());
			}
		}
	}

	public static String formatDate(Date date) {
		initDateFormat();
		return dateFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		initDateTimeFormat();
		return dateTimeFormat.format(date);
	}

	public static Date parseDate(String source) throws ParseException {
		initDateFormat();
		return dateFormat.parse(source);
	}

	public static Date parseDateTime(String source) throws ParseException {
		initDateTimeFormat();
		return dateTimeFormat.parse(source);
	}

	public static boolean isSetup() {
		return "true".equals(getSetupProperty("setup"));
	}

	public static String getSetupProperty(String name) {
		return getSetupProperties().getProperty(name);
	}

	public static int getSetupIntProperty(String name, int defaultValue) {
		String value = getSetupProperty(name);
		if (value != null)
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
			}
		return defaultValue;
	}
	
	public static Map<String, String> getSetupChildrenProperties(String parent) {
		Map<String, String> map = new HashMap<String, String> ();
		Collection<String> propNames = getSetupProperties().getChildrenNames(parent);
		for(String propName: propNames){
			String value = getSetupProperty(parent + "." + propName);
			if (value != null)
				map.put(propName, value);
		}
		return map;
	}
	
	public static Collection<String> getSetupChildrenNames(String parent) {
		return getSetupProperties().getChildrenNames(parent);
	}
	
	
	public static List<String> getSetupPropertyNames(){
		return new ArrayList<String>(getSetupProperties().getPropertyNames());
	}

	public static List<String> getSetupProperties(String parent) {
		Collection<String> propNames = getSetupProperties().getChildrenNames(parent);
		List<String> values = new ArrayList<String>();
		for(String propName: propNames){
			String value = getSetupProperty(parent + "." + propName);
			if (value != null)
				values.add(value);
		}
		/*Iterator<String> i = propNames.iterator();
		while (i.hasNext()) {
			String propName = (String) i.next();
			String value = getSetupProperty(parent + "." + propName);
			if (value != null)
				values.add(value);
		}*/
		return values;
	}

	public static void setSetupProperty(String name, String value) {
		getSetupProperties().put(name, value);
	}

	public static void setSetupProperties(Map<String, String> propertyMap) {
		getSetupProperties().putAll(propertyMap);
	}

	public static void deleteSetupProperty(String name) {
		getSetupProperties().remove(name);
	}

	public static String getProperty(String name) {
		loadLocalProperties();
		return properties.getProperty(name);
	}

	public static String getProperty(String name, String defaultValue) {
		loadLocalProperties();
		return properties.getProperty(name, defaultValue);
	}

	public static List<String> getPropertyNames() {
		loadLocalProperties();
		return new ArrayList<String>(properties.getPropertyNames());
	}

	public static List<String> getPropertyNames(String parent) {
		loadLocalProperties();
		return new ArrayList<String>(properties.getChildrenNames(parent));
	}

	
	public static List<String> getProperties(String parent) {
		loadLocalProperties();
		Collection<String> propertyNames = properties.getChildrenNames(parent);
		List<String> values = new ArrayList<String>();
		for(String propName: propertyNames){
			String value = getProperty(propName);
			if (value != null)
				values.add(value);
		}
		/*Iterator<String> i = propertyNames.iterator();
		do {
			if (!i.hasNext())
				break;
			String propName = i.next();
			String value = getProperty(propName);
			if (value != null)
				values.add(value);
		} while (true);*/
		return values;
	}
	/*
	public static Collection<String> getProperties(String parent){
		return properties.getProperties(parent);
	}*/

	public static int getIntProperty(String name, int defaultValue) {
		String value = getProperty(name);
		if (value != null)
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
			}
		return defaultValue;
	}

	public static boolean getBooleanProperty(String name) {
		return Boolean.valueOf(getProperty(name)).booleanValue();
	}

	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = getProperty(name);
		if (value != null)
			return Boolean.valueOf(value).booleanValue();
		else
			return defaultValue;
	}

	public static void setProperty(String name, String value) {
		loadLocalProperties();
		properties.put(name, value);
	}

	public static void setProperties(Map<String, String> propertyMap) {
		loadLocalProperties();
		properties.putAll(propertyMap);
	}

	public static void deleteProperty(String name) {
		loadLocalProperties();
		properties.remove(name);
	}


	

	
	
//	public static void setBeansHolder(BeansHolder holder){
//		AppsGlobals.holder = holder;
//	}
//	
//	/**
//	 * 
//	 * @return
//	 * @deprecated
//	 */
//	public static BeansHolder getBeansHolder(){
//		return holder;
//	}
	
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getLogPath(String fileName){
		File file = new File(fileName);
		if(!file.isAbsolute()){
			String path = AppsGlobalsBak.getSetupProperty("logs.path");
			if(path == null){
				path = AppsGlobalsBak.getWorkHome() + File.separator + "logs";
			}
			file = new File(path, fileName);
			fileName = file.getAbsolutePath();
		}
		log.info("日志路径：" + fileName);
		return fileName;
	}
	
	
	private static String tempPath = null;
	/**
	 * @deprecated
	 * @return
	 */
	public static String getTempPath(){
		if(tempPath == null){
			tempPath = buildTempPath();
		}
		return tempPath;
	}
	private static String buildTempPath(){
		String tmp0 = AppsGlobalsBak.getSetupProperty("tmpdir");
		File file0 = new File(tmp0);
		if(!file0.exists()){
			file0.mkdirs();
		}
		if(file0.exists() && file0.isDirectory()){
			return file0.getAbsolutePath();
		}
		
		
		String workHome = AppsGlobalsBak.getWorkHome();
		File tmp = new File(workHome, "/tmp");
		boolean b = false;
		if(!tmp.exists()){
			b = tmp.mkdirs();
		}else{
			b = true;
		}
		if(b && tmp.canWrite()){
			//return tmp.getAbsolutePath();
			File tempFile = null;
			try {
				tempFile = File.createTempFile("tmp", ".tmp");
				//tempFile.setReadable(false);
				System.out.println(tempFile);
				if(tempFile.canRead()){
					return tmp.getAbsolutePath();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(tempFile != null){
					tempFile.delete();
				}
			}
		}
		
		
		
		String tmpdir = System.getProperty("java.io.tmpdir");
		if(StringUtils.isNotBlank(tmpdir)){
			return tmpdir;
		}
		
		throw new IllegalArgumentException("Cannot find tmp dir.");
	}
	

	
	



	public static String getSetupProperty(String name, String defaultValue) {
		String value = AppsGlobalsBak.getSetupProperty(name);
		return value != null ? value : defaultValue;
	}
	
	
	
	public static String getCurrentThreshold() {
		boolean devMode = isDevMode();//Boolean.parseBoolean(System.getProperty("apps.devMode", "false"));
		return AppsGlobalsBak.getSetupProperty("log4j.threshold", devMode ? "INFO" : "ERROR");
	}

	public static void setCurrentThreshold(String thresholdLevel) {
		if (AppsGlobalsBak.isSetup() && StringUtils.isNotBlank(thresholdLevel)) {
			setThreshold(thresholdLevel);
		}
	}

	static void setThreshold(String thresholdLevel) {
		LoggerRepository logRepository = LogManager.getLoggerRepository();
		System.out.println((new StringBuilder()).append("Setting logging threshold to ").append(
				thresholdLevel.toUpperCase()).toString());
		logRepository.setThreshold(thresholdLevel.toUpperCase());
		AppsGlobalsBak.setSetupProperty("log4j.threshold", thresholdLevel);
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isDevMode(){
		//return System.getProperty(DEV_MODE_KEY, "false").equals("true");
		return Boolean.parseBoolean(System.getProperty(DEV_MODE_KEY, "false"));
	}


	public static boolean getSetupBooleanProperty(String name, boolean defaultValue) {
		String value = getSetupProperty(name);
		if (value != null)
			return Boolean.parseBoolean(value);
		return defaultValue;
	}
	/***************************
	public static void main2(String[] args){
		//Globals.setSetupProperty("oqs.showSql", "3");
		AppsGlobalsBak.getWorkHome();
		System.out.println(AppsGlobalsBak.getLogPath("ts.log"));
		for(String s: AppsGlobalsBak.setupProperties.getPropertyNames()){
			System.out.println(s + "=" + getSetupProperty(s));
		}
		
		List<String> names = AppsGlobalsBak.getSetupPropertyNames();
		System.out.println(names);
		System.out.println(AppsGlobalsBak.getTempPath());
		//Globals.setSetupProperty("test.prop.is", "value");
		
		//javax.sql.DataSource ds = Globals.getBeansHolder().get("dataSource", javax.sql.DataSource.class);
		//System.out.println(Globals.class.getProtectionDomain().getCodeSource().getLocation());
		
		//StringManager messages = StringManager.getManager("org.opoo.apps");
		//System.out.println(messages.getString("Globals.workHome.CriticalError"));
		//System.out.println(messages.getString("Globals.workHome.tryError", "abcd"));
	}
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		FileImageInputStream fimg = new FileImageInputStream(new File(
				"E:\\work.home\\usp44pfH.gif"));
		ImageOutputStream foimg = new FileImageOutputStream(new File("e:\\work.home\\a.gif"));

		ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
		ir.setInput(fimg);
		ImageReadParam imageParam = new ImageReadParam();
		imageParam.setSourceSubsampling(4, 4, 0, 0);

		ImageWriter iw = new GIFImageWriter(new GIFImageWriterSpi());
		iw.setOutput(foimg);
		
		
		ImageReadParam param = ir.getDefaultReadParam();
		System.out.println(param);
		

		ImageWriteParam wparam = new ImageWriteParam(Locale.CHINA);
		iw.prepareWriteSequence(iw.getDefaultStreamMetadata(wparam));
		System.out.println(System.currentTimeMillis() - start);
		for (int i = ir.getMinIndex(); i < ir.getNumImages(true); i++) {
			
			System.out.println((System.currentTimeMillis() - start) + "\nread");
			IIOImage iioImage = ir.readAll(i, imageParam);
			System.out.println(System.currentTimeMillis() - start);
			iw.writeToSequence(iioImage, wparam);
			System.out.println(System.currentTimeMillis() - start);
			iw.setOutput(foimg);
		}
		System.out.println(System.currentTimeMillis() - start);
		iw.endWriteSequence();
		ir.dispose();
		iw.dispose();
		System.out.println(System.currentTimeMillis() - start);
	}
	*****************/
}
