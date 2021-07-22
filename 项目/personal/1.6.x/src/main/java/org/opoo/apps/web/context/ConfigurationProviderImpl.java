package org.opoo.apps.web.context;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.module.ModuleManagerImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;

import com.jivesoftware.license.LicenseException;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, using AppsContextLoader
 */
public class ConfigurationProviderImpl implements ConfigurationProvider {
	private static final Log log = LogFactory.getLog(ConfigurationProviderImpl.class);
	
	public static final String ENV_KEY_SPRING_CONTEXT_CONFIG_LOCATIONS = "apps.spring.contextConfigLocation";
	
	public static final String REMOTING_ENABLE_FLAG = "apps.remoting.enabled";
	public static final String WS_ENABLE_FLAG = "apps.ws.enabled";
	public static final String BROKER_CONTEXT_FLAG = "apps.brokerConfig";
	public static final String EXTENSION_PATH_FLAG = "apps.extensionPath";
	public static final String RUNTIME_CONTEXT_FLAG = "apps.runtimeContext";
	public static final String SINGLE_NODE_BROKER_CONTEXT = "classpath:spring-singleNodeMessaging.xml";
	public static final String WS_CONTEXT = "classpath:spring-ws.xml";
	public static final String BOOT_CONTEXT = "classpath:spring-boot.xml";
	public static final String RUNTIME_CONTEXT = "classpath:spring-application.xml";
	public static final String REMOTING_CONTEXT = "classpath:spring-remoting.xml";
	public static final String SETUP_CONTEXT = "classpath:spring-setup.xml";
	public static final String DEV_MODE_CONTEXT = "classpath:spring-devMode.xml";
	private Boolean isInSetup;
	private String explicitConfiguration[] = null;
	private ServletContext servletContext;
	private List<String> extensionConfigs = new ArrayList<String>();
	//private static XMLOpooProperties2 buildConfig;
	
	
	public ConfigurationProviderImpl(){
	}
	
	public ConfigurationProviderImpl(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	
	protected void addBuildToContextList(List<String> contextList, String property){
		if(AppsInstanceLoader.getAppsInstance() != null){
			//String s = loader.getProperty("setup-context");
			String s = AppsInstanceLoader.getAppsInstance().getProperty(property);
			if(StringUtils.hasText(s)){
				List<String> list = tokenizeToStringArray(s);
				contextList.addAll(list);
			}
		}
	}
	
	
	public final String[] getConfigLocations() {
		// List<String> list = new ArrayList<String>();
		// list.addAll(getWebConfigLocations());
		// list.addAll(getEffectiveConfigLocations());
		// list.addAll(getExtensionConfigLocations());
		// list.addAll(getModuleConfigLocations());

		List<String> list = getContextFiles();
		return list.toArray(new String[list.size()]);
	}

	public List<String> getContextFiles() {
		List<String> masterList = new ArrayList<String>(3);
		masterList.add(BOOT_CONTEXT);
		if (explicitConfiguration != null && explicitConfiguration.length > 0) {
			log.info("Using explicit spring context definitions.");
			masterList.addAll(Arrays.asList(explicitConfiguration));
			masterList.addAll(extensionConfigs);
			return masterList;
		} else {
			masterList.addAll(getSetupContextFiles());
			masterList.addAll(getRuntimeContextFiles());
			masterList.addAll(getExtensionContextFiles(null));
			masterList.addAll(getRemotingContextFiles());
			masterList.addAll(getWebServiceContextFiles());
			masterList.addAll(getModuleConfigFiles());
			masterList.addAll(getWebConfigFiles(servletContext));
			masterList.addAll(getEffectiveConfigFiles());
			return masterList;
		}
	}
	
	protected List<String> tokenizeToStringArray(String configLocation){
		String[] configLocations = StringUtils.tokenizeToStringArray(configLocation, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		return Arrays.asList(configLocations);
	}
	
	
	protected final List<String> getModuleConfigFiles(){
		if(isInSetup()){
			return emptyList();
		}else{
			return ModuleManagerImpl.getInstance().getConfigLocations();
		}
	}
	
	
	protected final List<String> getWebConfigFiles(ServletContext servletContext){
		if(!isInSetup() && servletContext != null){
			String configLocation = servletContext.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM);
			if(configLocation != null){
				//String[] configLocations = StringUtils.tokenizeToStringArray(configLocation, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
				return tokenizeToStringArray(configLocation);
			}
		}
		return emptyList();
	}
	
	/**
	 * 
	 * @return
	 */
	protected final List<String> getEffectiveConfigFiles(){
		String configLocation = System.getProperty(ENV_KEY_SPRING_CONTEXT_CONFIG_LOCATIONS);//"apps.spring.contextConfigLocation");
		if(configLocation != null){
			return tokenizeToStringArray(configLocation);
		}
		return emptyList();
	}
	
	

	private List<String> emptyList(){
		return new ArrayList<String>();//Collections.emptyList();
	}
	
	
	public void appendConfiguration(String file) {
        extensionConfigs.add(file);
    }
	

    public void setExplicitConfiguration(String files[]) {
		explicitConfiguration = files;
	}

	public boolean isInSetup() {
		if (isInSetup == null) {
			try {
				isInSetup = new Boolean(!AppsGlobals.isSetup() || AppsLicenseManager.getInstance().isFailed());
			} catch (LicenseException le) {
				log.fatal("**********************************************************************************************");
				log.fatal("Current license is not valid for application. Entering setup, please provide an upgraded license.");
				log.fatal("**********************************************************************************************");
				log.debug(le, le);
				return true;
			}
		}
		return isInSetup.booleanValue();
	}

	public List<String> getSetupContextFiles() {
		List<String> setupContextFiles = new ArrayList<String>(1);
		if (isInSetup()) {
			setupContextFiles.add(SETUP_CONTEXT);
			//setupContextFiles.add("classpath:spring-*-setup.xml");
			addBuildToContextList(setupContextFiles, "context.setup");
		}
		return setupContextFiles;
	}

	public List<String> getRuntimeContextFiles() {
		List<String> runtimeContextFiles = new ArrayList<String>(1);
		String envConfig = System.getProperty(RUNTIME_CONTEXT_FLAG);
		if (envConfig != null) {
			log.info("Using runtime property context override '" + envConfig + "'.");
			runtimeContextFiles.addAll(tokenizeToStringArray(envConfig));
		} else {
			envConfig = System.getenv(RUNTIME_CONTEXT_FLAG);
			if (envConfig != null) {
				log.info("Using runtime environment context override '" + envConfig + "'.");
				runtimeContextFiles.addAll(tokenizeToStringArray(envConfig));
			}
		}
		if (envConfig != null) {
			return runtimeContextFiles;
		}
		if (!isInSetup()) {
			runtimeContextFiles.add(RUNTIME_CONTEXT);
			runtimeContextFiles.add(getBrokerConfigFile());
			
			addBuildToContextList(runtimeContextFiles, "context.runtime");
		}
		return runtimeContextFiles;
	}

	public List<String> getWebServiceContextFiles() {
		List<String> wsContextFiles = new ArrayList<String>(1);
		try {
			if (Boolean.parseBoolean(System.getProperty(WS_ENABLE_FLAG, "false"))) {
				log.info("Explicitly enabling web services.");
				wsContextFiles.add(WS_CONTEXT);
				
				addBuildToContextList(wsContextFiles, "context.ws");
			} else {
				log.info("Excluding web services from Spring context.");
			}
		} catch (Exception ex) {
			log.error("Misconfiguration of web services. Web services will not be enabled.");
		}
		return wsContextFiles;
	}

	public List<String> getRemotingContextFiles() {
		List<String> remoteContextFiles = new ArrayList<String>(1);
		try {
			boolean remotingEnabled = Boolean.parseBoolean(System.getProperty(REMOTING_ENABLE_FLAG, "false"));
			if (remotingEnabled) {
				remoteContextFiles.add(REMOTING_CONTEXT);
				
				addBuildToContextList(remoteContextFiles, "context.remoting");
			}
		} catch (Exception ex) {
			log.error("Misconfiguration of remoting services. Remoting services will not be enabled.");
		}
		return remoteContextFiles;
	}

	public final List<String> getExtensionContextFiles(String path) {
		List<String> etcContextFiles = new ArrayList<String>(3);
		if (isInSetup())
			return etcContextFiles;
		if (path == null || path.equals(""))
			path = System.getProperty(EXTENSION_PATH_FLAG);
		if (path == null) {
			path = AppsHome.getEtc().getAbsolutePath();
			log.info("No configuration extension override path defined. Defaulting to '" + path + "'.");
		} else {
			log.info("Using configuration extension override path '" + path + "'. appsHome/etc will be ignored.");
		}
		addXmlFilesToContextList(path, etcContextFiles);
		return etcContextFiles;
	}

	public final List<String> getDevModeContextFiles() {
		List<String> devFiles = new ArrayList<String>(1);
		if (AppsGlobals.isDevMode()) {
			// if(Boolean.parseBoolean(System.getProperty("apps.devMode",
			// "false"))){
			devFiles.add(DEV_MODE_CONTEXT);
			
			addBuildToContextList(devFiles, "context.dev");
		}
		return devFiles;
	}

	public final String getBrokerConfigFile() {
		String config = System.getProperty(BROKER_CONTEXT_FLAG);
		if (config != null) {
			log.info("Using property broker override '" + config + "'.");
			return config;
		}
		config = System.getenv(BROKER_CONTEXT_FLAG);
		if (config != null) {
			log.info("Using environment broker override '" + config + "'.");
			return config;
		}
		if (isInSetup()){
			return null;
		}	else{
			return SINGLE_NODE_BROKER_CONTEXT;
		}
	}

	private void addXmlFilesToContextList(String pathStr, List<String> files) {
		File path = new File(pathStr);
		if (!path.exists() && !path.isDirectory()) {
			return;
		}
		int counter = 0;
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		};
		File[] arr = path.listFiles(filter);
		for (File file : arr) {
			files.add("file:" + file.getAbsolutePath());
			log.info("Adding '" + file.getAbsolutePath() + "' to spring context.");
			counter++;
		}

		log.info("Added " + counter + " xml files to spring context.");
	}

	public Resource getAppsInstanceResource() {
		return new ClassPathResource("apps_build.xml");
	}
	
	
	public static void main(String[] args) throws IOException{
		Resource rs = new ClassPathResource("apps_build.xml");
		System.out.println(rs.getFile());
	}
	
}
