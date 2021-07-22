/*
 * $Id: AppsGlobals.java 57 2011-04-22 02:47:41Z alex $ 
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.database.spring.DatabaseProperties;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.util.AbstractOpooProperties;
import org.opoo.util.OpooProperties;
import org.opoo.util.XMLOpooProperties2;

import com.jivesoftware.license.LicenseException;


/**
 * 
 * 系统配置访问类。
 * 注意，由于Properties不能在集群中正确使用，所以此类也不能在集群下正常运行。
 * 
 * @author Alex
 * @since 1.5
 */
public class AppsGlobals {
	
	 public static class ResetLocalizationSettingsClusterTask extends AbstractClusterTask {
		private static final long serialVersionUID = 5090880847642512790L;
		public void execute() {
			AppsGlobals.resetLocalization();
		}
	}
	 
	public static class LocalizationSettingsListener implements EventListener<PropertyEvent> {
		public void handle(PropertyEvent event) {
			if (event.getName().startsWith("locale.")) {
				AppsGlobals.resetLocalization();
				CacheFactory.doClusterTask(new ResetLocalizationSettingsClusterTask());
			}
		}
	}
	
	
		
	public static final String DEV_MODE_KEY = "apps.devMode";
	public static final String APPS_INSTANCE_ID = "appsInstanceId";
	public static final String APPS_NODE_ID = "appsNodeId";
//	private static volatile boolean evalBuild = false;
//	private static volatile boolean initEvalBuild = false;
//	private static volatile String revision = "Custom";
//	private static volatile boolean initRevision = false;
	private static String name;
	private static final Object appsHomeLock = new Object();
	
	
	//private static final StringManager messages = StringManager.getManager("org.opoo.apps");
	
	//private static String APPS_CONFIG_FILENAME = "apps_setup.xml";

	//private static String APPS_INIT_CONFIG = "apps_init.xml";
	
	//private static String APPS_BUILD_CONFIG = "apps_build.xml";
	
	private static final String DEFAULT_CHAR_ENCODING = "UTF-8";

	//public static String workHome = null;

	public static boolean failedLoading = false;

	private static Locale locale = null;

	private static TimeZone timeZone = null;

	private static String characterEncoding = null;

	private static DateFormat dateFormat = null;

	private static DateFormat dateTimeFormat = null;
	
	private static DateFormat shortDateTimeFormat = null;

	private static Date startupDate = new Date();
	
    /**
     * Whether or not we're a eval build
     */
    private static volatile boolean evalBuild = false;
    /**
     * Whether or not eval build has been determined and initialized.
     */
    private static volatile boolean initEvalBuild = false;
    /**
     * Whether or not we're a eval build
     */
    private static volatile String revision = "Custom";
    /**
     * Whether or not eval build has been determined and initialized.
     */
    private static volatile boolean initRevision = false;
    

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
	private static OpooProperties<String, String> setupProperties_BAK;
	
	private static AppsProperties<String, String> setupProperties;
	
	
	private static volatile OpooProperties<String, String> localizedProperties = null;


	private static Log log = LogFactory.getLog(AppsGlobals.class);

//	/**
//	 * 系统初始化设置。一般是只读的。
//	 */
//	private static OpooProperties<String, String> initProperties;
//
//	private static OpooProperties<String, String> buildProperties;
	
	

	static {
		log .info(Version.getImplementationTitle() + ": "
				+ Version.getImplementationVersion() + " - by "
				+ Version.getImplementationVendor());
//		log.info("<init>");
	}
	
	
	private AppsGlobals() {
	}

	/**
	 * 
	 */
	public static void reset() {
		synchronized (appsHomeLock) {
			failedLoading = false;
			setupProperties = null;
			properties = null;
			localizedProperties = null;
			startupDate = new Date();
//			whiteLabel = false;
//			initWhiteLabel = false;
			DatabaseProperties.destroy();
			DataSourceManager.setDataSourceProvider(null);
			resetLocalization();
		}
	}

	public static void resetLocalization() {
		locale = null;
		timeZone = null;
		characterEncoding = null;
		dateFormat = null;
		dateTimeFormat = null;
	}

	public static Date getStartupDate() {
		return new Date(startupDate.getTime());
	}

	public static Locale getLocale() {
		if (locale == null) {
			String language = getProperties().get(AppsConstants.LOCALE_LANGUAGE);
			if (language == null)
				language = "";
			String country = getProperties().get(AppsConstants.LOCALE_COUNTRY);
			if (country == null)
				country = "";
			if (language.equals("") && country.equals(""))
				locale = Locale.getDefault();
			else
				locale = new Locale(language, country);
		}
		return locale;
	}

	public static String getName() {
		if (name == null && AppsHome.isInitialized()) {
			String propName = getProperties().get("apps.name");
			if (propName != null)
				name = propName;
		}
		if (name == null) {
			String envName = System.getProperty("apps.name", null);
			if (envName != null) {
				name = envName;
			} else {
				log.info("No component name specified. Defaulting to 'opoo-apps'.");
				name = "opoo-apps";
			}
		}
		return name;
	}

	public static void setLocale(Locale newLocale) {
		setProperty(AppsConstants.LOCALE_COUNTRY, newLocale.getCountry());
		setProperty(AppsConstants.LOCALE_LANGUAGE, newLocale.getLanguage());
		resetLocalization();
	}

	public static String getCharacterEncoding() {	
		if (characterEncoding == null) {
            // Workaround for MySQL 4.1 which needs the character encoding before
            // creating the first database connection -- try loading the property
            // from the XML file.
            String encoding = getSetupProperty(AppsConstants.LOCALE_CHARACTER_ENCODING);
            if (encoding != null) {
                characterEncoding = encoding;
            }

            String charEncoding = getProperty(AppsConstants.LOCALE_CHARACTER_ENCODING);
            if (charEncoding != null) {
                characterEncoding = charEncoding;
            }
            else if (characterEncoding == null) {
                // The default encoding is UTF-8. We use the version of
                // the encoding name that seems to be most widely compatible.
                characterEncoding = DEFAULT_CHAR_ENCODING;
            }
        }

        return characterEncoding;
	}

	public static void setCharacterEncoding(String characterEncoding) throws UnsupportedEncodingException {
		if (!isValidCharacterEncoding(characterEncoding)) {
			throw new UnsupportedEncodingException((new StringBuilder()).append("Invalid character encoding: ").append(
					characterEncoding).toString());
		} else {
			AppsGlobals.characterEncoding = characterEncoding;
			setProperty(AppsConstants.LOCALE_CHARACTER_ENCODING, characterEncoding);
			resetLocalization();
			return;
		}
	}

	public static TimeZone getTimeZone() {
		if (timeZone == null)
			if (properties != null) {
				String timeZoneID = properties.get(AppsConstants.LOCALE_TIMEZONE);
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
		String s = newTimeZone.getID();
		setProperty(AppsConstants.LOCALE_TIMEZONE, s);
		resetLocalization();
	}

	/**
	 * @deprecated using {@link #getSetupProperty(String)}
	 * @param name
	 * @return
	 */
	public static String getLocalProperty(String name) {
		return getSetupProperties().get(name);
	}

	/**
	 * @deprecated
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static int getLocalProperty(String name, int defaultValue) {
		return getSetupProperties().getProperty(name, defaultValue);
	}

	/**
	 * @deprecated
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getLocalProperty(String name, String defaultValue) {
		return getSetupProperties().getProperty(name, defaultValue);
	}
	
	/**
	 * @deprecated
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getLocalProperty(String name, boolean defaultValue){
		return getSetupProperties().getProperty(name, defaultValue);
	}


	public static List<String> getLocalProperties(String parent) {
		Collection<String> propNames = getSetupProperties().getChildrenNames(parent);
		List<String> values = new ArrayList<String>();
		for (String propName : propNames) {
			String value = getProperty(parent + "." + propName);
			if (value != null)
				values.add(value);
		}
		return values;
	}

	/**
	 * @deprecated
	 * @param name
	 * @param value
	 */
	public static void setLocalProperty(String name, String value) {
		getSetupProperties().put(name, value);
	}

	/**
	 * @deprecated
	 * @param propertyMap
	 */
	public static void setLocalProperties(Map<String, String> propertyMap) {
		getSetupProperties().putAll(propertyMap);
	}

	/**
	 * @deprecated
	 * @param name
	 */
	public static void deleteLocalProperty(String name) {
		try {
			getSetupProperties().remove(name);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static String getProperty(String name) {
		return getProperties().get(name);
	}

	public static String getProperty(String name, String defaultValue) {

		return getProperties().getProperty(name, defaultValue);
	}

	private static OpooProperties<String, String> getProperties() {
		if (properties == null) {
			getSetupProperties();
			if(failedLoading){
				properties = null;
			}else{
				properties = DatabaseProperties.getInstance();
			}
		}
		return (properties == null ? AbstractOpooProperties.getEmptyPropertiesInstance() : properties);
	}

	public static List<String> getPropertyNames() {
		return new ArrayList<String>(getProperties().getPropertyNames());
	}

	/**
	 * 返回下级属性的属性名称。例如有属性集合 <tt>X.Y.A</tt>, <tt>X.Y.B</tt>, <tt>X.Y.C</tt> 和 <tt>X.Y.C.D</tt>，
	 * 调用该方法则返回 <tt>X.Y</tt> 的下级属性名称集合为 <tt>X.Y.A</tt>, <tt>X.Y.B</tt> 和 <tt>X.Y.C</tt>，
	 *  <tt>X.Y.C.D</tt> 不会返回。
	 * 
	 * <p>请注意该方法与 {@link #getSetupChildrenNames(String)} 的区别。
	 * 
	 * @param parent the name of the parent property to return the children for.
     * @return all child property values for the given parent.
	 */
	public static List<String> getPropertyNames(String parent) {
		return new ArrayList<String>(getProperties().getChildrenNames(parent));
	}

	public static List<String> getProperties(String parent) {
		Collection<String> propertyNames = getProperties().getChildrenNames(parent);
		List<String> values = new ArrayList<String>();
		for (String propertyName : propertyNames) {
			String value = getProperty(propertyName);
			if (value != null)
				values.add(value);
		}
		return values;
	}

	public static int getProperty(String name, int defaultValue) {
		return getProperties().getProperty(name, defaultValue);
	}

	public static long getProperty(String name, long defaultValue){
		String string = getProperties().get(name);
		if(string != null){
			return NumberUtils.toLong(string);
		}
		return defaultValue;
	}
	
	public static boolean getPropertyBooleanValue(String name) {
		return getProperties().getBooleanProperty(name);
	}

	public static boolean getProperty(String name, boolean defaultValue) {
		return getProperties().getProperty(name, defaultValue);
	}

	public static void setProperty(String name, String value) {
		getProperties().put(name, value);
	}

	public static void setProperties(Map<String, String> propertyMap) {
		getProperties().putAll(propertyMap);
	}

	public static void deleteProperty(String name) {
		getProperties().remove(name);
	}

	public static String getLocalizedProperty(String name, Locale locale) {
		return getLocalizedProperties().get(name + "." + locale);
	}

	public static List<Locale> getLocalizedPropertyLocales(String name) {
		if (getLocalizedProperties() instanceof DatabaseProperties)
			return ((DatabaseProperties) getLocalizedProperties()).getLocalesForProperty(name);
		else
			return Collections.emptyList();
	}

	public static void setLocalizedProperty(String name, String value, Locale locale) {
		getLocalizedProperties().put(name + "." + locale.toString(), value);
	}

	public static void deleteLocalizedProperty(String name, Locale locale) {
		getLocalizedProperties().remove(name + "." + locale.toString());
	}

	private static OpooProperties<String, String> getLocalizedProperties() {
		if (localizedProperties == null) {
			getSetupProperties();
			localizedProperties = DatabaseProperties.getLocalizedInstance();
		}
		return (localizedProperties == null ? AbstractOpooProperties.getEmptyPropertiesInstance() : localizedProperties);
	}

	/*
	public static String getRevision() {
		if (!initRevision) {
			String s = getBuildProperty("revision");
			if (s != null)
				revision = s;
			initRevision = true;
		}
		return revision;
	}

	public static String getBuildProperty(String name) {
		return null;
	}
*/
	
	public static Map<String, String> getUnmodifiableMap() {
		return Collections.unmodifiableMap(getProperties());
	}

	public static boolean isHidden(String propName) {
		if (propName == null) {
			return false;
		} else {
			String propNameLower = propName.toLowerCase();
			return propNameLower.indexOf("passwd") > -1 || propNameLower.indexOf("password") > -1
					&& propNameLower.indexOf("passwordreset") == -1 || propNameLower.indexOf("cookiekey") > -1
					|| propNameLower.indexOf("secret") > -1;
		}
	}



	@SuppressWarnings("unused")
	private static OpooProperties<String, String> getSetupProperties_BAK(){
		if(failedLoading){
			return AbstractOpooProperties.getEmptyPropertiesInstance();
		}
		if(setupProperties_BAK == null){
			synchronized(appsHomeLock){
				if(setupProperties_BAK == null){
					File ah = AppsHome.getAppsHome();
			        if(!ah.exists()){
			        	failedLoading = true;
			            log.fatal("The specified appsHome directory does not exist (" + ah.getAbsolutePath() + ")");
			        }else if(!ah.canRead() || !ah.canWrite()){
			        	failedLoading = true;
			        	log.fatal("运行该应用的用户对appsHome目录没有读或写的权限：" + ah.getAbsolutePath());
			        }
			        if(!failedLoading){
			        	try{
			        		setupProperties_BAK = new XMLOpooProperties2(AppsHome.getConfigurationFile().getAbsolutePath());
			        	}catch(Exception e){
			        		failedLoading = true;
			        		log.error(e.getMessage(), e);
			        	}
			        }
			        if(failedLoading){
			        	return AbstractOpooProperties.getEmptyPropertiesInstance();
			        }
					
			        
			        /*
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
					}*/
				}
			}
		}
		return setupProperties_BAK;
	}
	
	
	private static AppsProperties<String, String> getSetupProperties() {
        if (failedLoading) {
            return EmptyAppsProperties.getInstance();
        }

        if (setupProperties == null) {

            if (setupProperties != null) {
                return setupProperties;
            }

            // Create a manager with the full path to the xml config file.
            try {
                // Do a permission check on the jiveHome directory:
                File ah = AppsHome.getAppsHome();
                if (!ah.exists()) {
                    log.fatal("The specified appsHome directory does not exist (" + ah.getAbsolutePath() + ")");
                }
                else {
                    if (!ah.canRead() || !ah.canWrite()) {
                        log.fatal("The operating system user running this application can not "
                                + "read and write to the specified jiveHome directory ("
                                + ah.getAbsolutePath() + "). Please grant the executing user "
                                + "read and write permissions.");
                    }
                }
                setupProperties = new XMLAppsProperties(AppsHome.getConfigurationFile().getAbsolutePath());
            }
            catch (IOException ioe) {
                log.warn("应用初始化失败 ，这是启动系统的基本过程，通常不应该发生此类错误。");
                log.debug(ioe.getMessage(), ioe);
                failedLoading = true;
                return EmptyAppsProperties.getInstance();
            }catch (LicenseException le){
            	log.warn("应用初始化失败 ，这是启动系统的基本过程，通常不应该发生此类错误。");
                log.debug(le.getMessage(), le);
                failedLoading = true;
                return EmptyAppsProperties.getInstance();
            }
        }
        return setupProperties;
    }

	
	/**
	 * 
	 * @return
	 * @deprecated using AppsHome.getAppsHome()
	 */
	public static String getWorkHome() {
		return AppsHome.getAppsHome().getAbsolutePath();
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

	

	private static void initDateFormat() {
		if (dateFormat == null) {
			String style = getSetupProperty("formatStyle.dateFormat");
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
			String style = getSetupProperty("formatStyle.dateTimeFormat");
			if (style != null) {
				dateTimeFormat = new SimpleDateFormat(style);
			} else {
				dateTimeFormat = DateFormat.getDateTimeInstance(2, 2, getLocale());
				dateTimeFormat.setTimeZone(getTimeZone());
			}
		}
	}
	
	private static void initShortDateTimeFormat(){
		if(shortDateTimeFormat == null){
			String style = getSetupProperty("formatStyle.shortDateTimeFormat");
			if (style != null) {
				shortDateTimeFormat = new SimpleDateFormat(style);
			} else {
				dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
	
	/**
	 * @since 1.6.1
	 * @param date
	 * @return
	 */
	public static String formatShortDateTime(Date date){
		initShortDateTimeFormat();
		return shortDateTimeFormat.format(date);
	}

	public static Date parseDate(String source) throws ParseException {
		initDateFormat();
		return dateFormat.parse(source);
	}

	public static Date parseDateTime(String source) throws ParseException {
		initDateTimeFormat();
		return dateTimeFormat.parse(source);
	}
	
	/**
	 * @since 1.6.1
	 * @param date
	 * @return
	 */
	public static Date parseShortDateTime(String source) throws ParseException{
		initShortDateTimeFormat();
		return shortDateTimeFormat.parse(source);
	}


	/**
	 * @param name
	 * @return
	 */
	public static String getSetupProperty(String name) {
		return getSetupProperties().get(name);
	}

	public static int getSetupProperty(String name, int defaultValue) {
		return getSetupProperties().getProperty(name, defaultValue);
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
	
	/**
	 * 返回下级属性的属性名称。例如有属性集合 <tt>X.Y.A</tt>, <tt>X.Y.B</tt>, <tt>X.Y.C</tt> 和 <tt>X.Y.C.D</tt>，
	 * 调用该方法则返回 <tt>X.Y</tt> 的下级属性名称集合为 <tt>A</tt>, <tt>B</tt> 和 <tt>C</tt>，
	 *  <tt>C.D</tt> 不会返回。
	 * 
	 * <p>请注意该方法与 {@link #getPropertyNames(String)} 的区别。
	 * 
	 * @param parent the name of the parent property to return the children for.
     * @return all child property values for the given parent.
	 */
	public static Collection<String> getSetupChildrenNames(String parent) {
		return getSetupProperties().getChildrenNames(parent);
	}
	
	
	public static List<String> getSetupPropertyNames(){
		return new ArrayList<String>(getSetupProperties().getPropertyNames());
	}

	/**
	 * 
	 * @param parent
	 * @return
	 */
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
		
		
		
//		Collection<String> propNames = getSetupProperties().getChildrenNames(parent);
//		List<String> values = new ArrayList<String>();
//		for (String propName : propNames) {
//			String value = getProperty(parent + "." + propName);
//			if (value != null)
//				values.add(value);
//		}
//		return values;
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



	



	public static String getSetupProperty(String name, String defaultValue) {
		return getSetupProperties().getProperty(name, defaultValue);
	}
	
	
	
	public static String getCurrentThreshold() {
		boolean devMode = isDevMode();//Boolean.parseBoolean(System.getProperty("apps.devMode", "false"));
		return AppsGlobals.getProperty("log4j.threshold", devMode ? "INFO" : "ERROR");
	}

	public static void setCurrentThreshold(String thresholdLevel) {
		if (AppsGlobals.isSetup() && StringUtils.isNotBlank(thresholdLevel)) {
			setThreshold(thresholdLevel);
		}
	}

	static void setThreshold(String thresholdLevel) {
		LoggerRepository logRepository = LogManager.getLoggerRepository();
		System.out.println((new StringBuilder()).append("Setting logging threshold to ").append(
				thresholdLevel.toUpperCase()).toString());
		logRepository.setThreshold(thresholdLevel.toUpperCase());
		AppsGlobals.setSetupProperty("log4j.threshold", thresholdLevel);
	}
	


	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getSetupProperty(String name, boolean defaultValue) {
		return getSetupProperties().getProperty(name, defaultValue);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean isSetup() {
		return "true".equals(getLocalProperty("setup"));
	}
	/**
	 * 
	 * @return
	 */
	public static boolean isDevMode(){
		//return System.getProperty(DEV_MODE_KEY, "false").equals("true");
		//return Boolean.parseBoolean(System.getProperty(DEV_MODE_KEY, "false"));
		return Boolean.getBoolean(DEV_MODE_KEY);
	}
	
    public static boolean isDebugMode()
    {
        return getProperty(DEV_MODE_KEY, false) || BooleanUtils.toBoolean(System.getProperty(DEV_MODE_KEY));
    }
    
    /**
     * 允许上传文件大小的最大值。
     * @return
     */
    public static long getMultipartMaxSize(){
    	int maxNumber = AppsGlobals.getProperty(AppsConstants.ATTACHMENTS_MAX_ATTACHMENTS_PER_MESSAGE, 5);
		int maxSize = AppsGlobals.getProperty(AppsConstants.ATTACHMENTS_MAX_ATTACHMENT_SIZE, 1024);
		long maxAllowedSize = maxNumber * maxSize * 1024L + 2096L * maxNumber;
		long limit1 = (long) ((double) maxAllowedSize * 1.5D);
		long limit2 = maxAllowedSize + 0xf00000L;
		maxAllowedSize = limit1 <= limit2 ? limit2 : limit1;
		return maxAllowedSize;
    }
    
    
    /**
     * Returns a build-level property. These are properties that are part of the build itself
     * and can not be overridden or changed at runtime.
     * <p/>
     * Note, this method will be generally less performant that other property accessors, so
     * it's best to cache the answer from this method.
     *
     * @param name the name of the proeprty
     * @return the value of the property if it is set or <tt>null</tt> if the property is
     *      blank or if the prop doesn't exist.
     */
    public static String getBuildProperty(String name) {
        return AppsInstanceLoader.getProperty(name);
    }
    
    /**
     * Returns <tt>true</tt> if the application was built as a eval build, <tt>false</tt>
     * otherwise. <tt>false</tt> is the default. Eval build changes some messaging to help guide
     * the user to get a production version.
     *
     * @return true if we're in white label mode, false otherwise. False is the default.
     */
    public static boolean isEvalBuild() {
        if (!initEvalBuild) {
            evalBuild = "true".equals(getBuildProperty("evaluation"));
            initEvalBuild = true;
        }
        return evalBuild;
    }

    /**
     * Returns the svn revsion number if the application was built from the official build system,
     * "custom" otherwise. "Custom" is the default.
     *
     * @return true if we're in white label mode, false otherwise. False is the default.
     */
    public static String getRevision() {
        if (!initRevision) {
            String s = getBuildProperty("revision");
            if (s != null)
                revision = s;
            initRevision = true;
        }
        return revision;
    }
}
