package org.opoo.apps;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.opoo.util.ClassPathUtils;
import org.opoo.util.InputOutputStreamWrapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StopWatch;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

/**
 * Self-contained abstraction of the logic used to derive the home directory of the application.
 * <p/>
 * This class also exposes various commonly used resources from the application home.
 * <p/>
 * It is intentionally staticly initialized as access to its information is usually needed in early application
 * bootstrap initialization.
 * <p/>
 * The contract of this file is then, that apps home can never be null. It may not exist but the configured value will
 * always exist or be defaulted.
 *
 * @javadoc api
 */
public final class AppsHome {

    //implementation note - careful with calls to external services (AppsGlobals)
    //which almost universally assume this class is initialized

    public static final String LICENSE_FILE = "apps.license";
    public static final String CONFIG_FILE = "apps_startup.xml";
    public static final String APPS_ROOT_KEY = "apps.home";
    public static final String APPS_LOG_KEY = "apps.logs";
    public static final String APPS_HOME_KEY = "apps.instance.home";
    public static final String APPS_ROOT_ENV_KEY = "APPS_HOME";

    public static final String CLASSPATH_RESOURCE_ROOT = "resources";

    /**
     * Should we copy scripts from JAR to appsHome? If the scripts are copied the AppsResourceServlet will pick it up
     * if it is serving resources and make development in QuickStart a headache for everyone. Its easier if they just
     * remain on the classpath
     */
    public static final String COPY_SCRIPTS_TO_HOME = "apps.resources.scripts.copy.classpath";

    private static UUID nodeUuid = null;

    //preserves the startup path allowing a comparison of the effective path to the
    //initial path
//    private static final String startupAppsHomePath;
//    private static final String startupAppsRootPath;
//    private static final String startupLogPath;

    ///the effective path which will impact all file objects and path strings
    //dispatched from this class
    private static String effectiveAppsHomePath;
    private static String effectiveAppsRootPath;
    private static String effectiveAppsLogPath;
    private static String effectiveJMSDataPath;

    //used to prevent overly verbose logging
    private static boolean initialization = true;
    private static boolean initialized = false;

    private static final Logger log = LogManager.getLogger(AppsHome.class);

    static {
        //note that calling these methods in the static block will also initialize the effective
        //paths as well
//        startupAppsRootPath = getAppsRootPath();
//        startupAppsHomePath = getAppsHomePath();
//        startupLogPath = getAppsLogsPath();
        initialization = false;
    }

//    /**
//     * Returns the root path that was present at startup. This will not be the same as
//     * the effective path if an outside entity has established a different home path.
//     *
//     * @return  the root path that was present at startup.
//     */
//    public static String getStartupAppsHomePath() {
//        return startupAppsHomePath;
//    }
//
//    /* Returns the Apps Home path that was present at startup. This will not be the same as
//     * the effective path if an outside entity has established a different root path.
//     *
//     * @return the Apps Home path that was present at startup.
//     */
//    public static String getStartupAppsRootPath() {
//        return startupAppsRootPath;
//    }
//
//    /**
//     * Returns the path to application log files that was present at startup. This will not be the
//     * same as the effective path if an outside entity has established a different log path.
//     *
//     * @return the path to application log files that was present at startup.
//     */
//    public static String getStartupAppsLogsPath() {
//        return startupLogPath;
//    }

    /**
     * Resets the effective Apps Home path for the running application, overriding system defaults.
     * This method is intentionally not thread-safe and is not intended for use in a running system. The only intended
     *
     * use is during startup or setup operations which may need to alter this path.
     *
     * Setting this value to null will effectively result in a re-initialization of the path
     * from the runtime environment.
     *
     * @param path the path to set
     */
    public static synchronized void setEffectiveAppsHomePath(String path) {
        if (path != null) {
            File pathFile = new File(path);
            if (pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() && pathFile.canWrite()) {
                effectiveAppsHomePath = path;
            }
        }
        else {
            effectiveAppsHomePath = null;
        }
    }

    /**
     * Resets the effective Apps Root path for the running application, overriding system defaults.
     *
     * This method is intentionally not thread-safe and is not intended for use in a running system. The only intended
     * use is during startup or setup operations which may need to alter this path.
     *
     * Setting this value to null will effectively result in a re-initialization of the path
     * from the runtime environment.
     *
     * @param path the path to set
     */
    public static synchronized void setEffectiveAppsRootPath(String path) {
        if (path != null) {
            File pathFile = new File(path);
            if (pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() && pathFile.canWrite()) {
                effectiveAppsRootPath = path;
            }
        }
        else {
            effectiveAppsRootPath = null;
        }
    }

    /**
     * Resets the effective Apps log path for the running application, overriding system defaults.
     *
     * This method is intentionally not thread-safe and is not intended for use in a running system. The only intended
     * use is during startup or setup operations which may need to alter this path.
     *
     * Setting this value to null will effectively result in a re-initialization of the path
     * from the runtime environment.
     *
     * @param path the path to set
     */
    public static synchronized void setEffectiveAppsLogsPath(String path) {
        if(path != null) {
            File pathFile = new File(path);
            if (pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() && pathFile.canWrite()) {
                effectiveAppsLogPath = path;
            }
        }
        else {
            effectiveAppsLogPath = null;
        }
    }

    /**
     * Returns the log path given the current runtime environment.
     * This is not necessarily the same as the effective runtime path
     * which may be changed programatically.
     *
     * @return the log path given the current runtime environment.
     */
     public static String getEnvironmentAppsLogsPath() {
         final Logger log = LogManager.getLogger(AppsHome.class);
         // Attempt to load root path information
         String envPath = System.getProperty(APPS_LOG_KEY);

         if (StringUtils.isBlank(envPath)) {
             try {
                 InitialContext context = new InitialContext();
                 envPath = (String) context.lookup("java:comp/env/" + APPS_LOG_KEY);
             }
             catch (Exception e) {
                 //no-op
             }
         }

         if (StringUtils.isBlank(envPath)) {
             File root = getAppsRoot();
             if (!root.exists()) {
                 if(initialization) {
                    log.warn("Apps root directory does not exist for log configuration. Reverting to APPS_HOME/logs.");
                 }
                 StringBuilder buffer = new StringBuilder(getAppsHomePath());
                 buffer.append(File.separator).append("logs");
                 envPath = buffer.toString();
             }
             else {
                 if(initialization) {
                     log.warn("No explicit configuration of Apps log path. Using system default of: '" + envPath + "'.");
                 }
                 StringBuilder buffer = new StringBuilder(getAppsRootPath());
                 buffer.append(File.separator).append("var").append(File.separator).append("logs");
                 envPath = buffer.toString();
             }
         }

         return envPath;
    }

    /**
     * Returns the root path given the current runtime environment.
     * This is not necessarily the same as the effective runtime path
     * which may be changed programatically.
     *
     * @return the root path given the current runtime environment.
     */
    public static String getEnvironmentAppsRootPath() {
        final Logger log = LogManager.getLogger(AppsHome.class);
        // Attempt to load root path information
        String envRootPath = System.getProperty(APPS_ROOT_KEY);

        if (StringUtils.isBlank(envRootPath)) {
            try {
                InitialContext context = new InitialContext();
                envRootPath = (String) context.lookup("java:comp/env/" + APPS_ROOT_KEY);
            }
            catch (Exception e) {
                //no-op
            }
        }

        if (StringUtils.isBlank(envRootPath)) {
            envRootPath = System.getenv(APPS_ROOT_ENV_KEY);
            if (envRootPath != null && !"".equals(envRootPath)) {
                log.info("Apps root set from system property to '" + envRootPath + "'.");
            }
        }

        if (StringUtils.isBlank(envRootPath)) {
            StringBuilder buffer = new StringBuilder();
            if (System.getProperty("os.name", "Linux").indexOf("Windows") == -1) {
                buffer.append(File.separator).append("usr").append(File.separator).append("local").
                        append(File.separator).append("apps");
            }
            else {
                buffer.append("c:").append(File.separator).append("apps");
            }

            envRootPath = buffer.toString();
            log.warn("No explicit configuration of Apps root path. Using system default of: '" + envRootPath + "'.");
        }

        return envRootPath;
    }

    /**
     * Returns the environment configured value for Apps Home. In normal operation, this should be the same as the
     * effective Apps Home. If an entity has explicitly changed the effective Apps Home, this will return the equivalent
     * of the startup value but not the effective value.
     *
     * @return the environment configured value for Apps Home.
     */
    public static String getEnvironmentAppsHomePath() {

        String path = null;
        final Logger log = LogManager.getLogger(AppsHome.class);

        // First attempt JNDI
        try {
            InitialContext context = new InitialContext();
            path = (String) context.lookup("java:comp/env/" + APPS_HOME_KEY);
            if (path != null) {
                log.info("Apps instance home set from JNDI to '" + path + "'.");
            }
        }
        catch (Exception e) {
            //no-op
        }

        // System property
        if (StringUtils.isBlank(path)) {
            path = System.getProperty(APPS_HOME_KEY);
            if (path != null && !"".equals(path)) {
                log.info("Apps instance home set from system property to '" + path + "'.");
            }
        }

        // if nothing is found, attempt to identify by the legacy appsHome paths
        // warning if those are not found

        // JNDI legacy
        if (StringUtils.isBlank(path)) {
            try {
                InitialContext context = new InitialContext();
                path = (String) context.lookup("java:comp/env/appsHome");
                if (path != null && !"".equals(path)) {
                    if(initialization) {
                    log.warn("Apps instance home set from legacy JNDI appsHome setting to '" + path
                            + "'. Please upate configuration to use " + APPS_HOME_KEY);
                    }
                }
            }
            catch (Exception e) {
                //no-op
            }
        }

        // System property legacy
        if (StringUtils.isBlank(path)) {
            path = System.getProperty("appsHome");
            if (path != null && !"".equals(path)) {
                if(initialization) {
                    log.warn("Apps instance home set from legacy appsHome system property to '" + path
                           + "'. Please update configuration to use " + APPS_HOME_KEY);
                }
            }
        }

        //finally, if the app path is not set, derive from the root
        if (StringUtils.isBlank(path)) {
            File rootFilePath;
            if(effectiveAppsRootPath != null) {
                rootFilePath = new File(effectiveAppsRootPath);
            }
            else {
                rootFilePath = new File(getEnvironmentAppsRootPath());
            }

            //this is a temporary measure that may be changed later when AppsGlobals are fully initialized
            //but after AppsHome is initialized - we can't rely on AppsGlobals in this context
            //because it depends on us
            final String name = getName();
            final String type = getType();
            final String instanceId = getInstanceId();

            StringBuilder buffer = new StringBuilder(rootFilePath.getAbsolutePath());
            //careful here, calls into AppsGlobals may result into infinite recursion
            buffer.append(File.separator).append("applications").append(File.separator).
                    append(name).append(File.separator).
                    append(type).append("-").append(instanceId);
            path = buffer.toString();
            if (initialization) {
                log.warn("Attempting to use default apps instance home value in absence of explicit configuration: '" +
                        path + "'.");
            }
        }

        return path;
    }

    public static String getInstanceId() {
    	String instanceId = AppsInstanceLoader.getAppsInstance().getInstanceId();
		return instanceId != null ? instanceId : "0000";
	}

    public static String getType() {
		String type = AppsInstanceLoader.getAppsInstance().getType();
		return type != null ? type : "default";
	}
    
	/**
	 * 系统模块目录。
	 * @return
	 */
	public static File getModules(){
		return new File(getAppsHome(), "modules");
	}

	/**
     * Causes the system to initialize all expected files to the effective Apps Home.
     * If the Apps Home is reset, this method should be called to populate the content
     * into the new directory.
     *
     * This method may be invoked multiple times safely and in most cases, multiple invocations
     * will not alter the apps home directory contents. Only if the effective home value is changed
     * will re-invocations of this method have an impact on the filesystem contents.
     */
    public static synchronized void initialize() {

        final ClassLoader threadLoader = Thread.currentThread().getContextClassLoader();

        if (effectiveAppsRootPath == null) {
            effectiveAppsRootPath = getEnvironmentAppsRootPath();
        }

        if (effectiveAppsHomePath == null) {
            effectiveAppsHomePath = getEnvironmentAppsHomePath();
        }

        if (effectiveAppsLogPath == null) {
            effectiveAppsLogPath = getEnvironmentAppsLogsPath();
        }

        final File home = getAppsHome();

        if (!home.exists()) {
            final String message = "Failed to create home directory during initialization.";

            try {
                if (!home.mkdirs()) {
                    log.fatal(message);
                    throw new RuntimeException(message);
                }
            }
            catch (Exception ex) {
                log.fatal(message, ex);
                throw new RuntimeException(message, ex);
            }
        }

        boolean failure = false;

        if (!home.isDirectory()) {
            log.fatal("Configured apps instance home is not a directory.");
            failure = true;
        }

        if (!home.canRead()) {
            log.fatal("Cannot read from apps instance home directory.");
            failure = true;
        }

        if (!home.canWrite()) {
            log.fatal("Cannot write to apps instance home directory.");
            failure = true;
        }

        if (failure) {
            throw new RuntimeException("Unable to access configured apps instance home at '" + home.getAbsolutePath()
                    + "'. The system cannot operate in this state.");
        }

        //create a configuration file if one does not exist
        File config = getConfigurationFile();
        if (!config.exists()) {
            try {
                createAppsStartupXmlFile(config);
            }
            catch (Exception ex) {
                log.warn("Failed to create application startup file.", ex);
                failure = true;
            }
        }

        failure = !createDirectoryIfNecessary(getStats()) || failure;
        failure = !createDirectoryIfNecessary(getTemp()) || failure;
        failure = !createDirectoryIfNecessary(getAppsLogs()) || failure;
        failure = !createDirectoryIfNecessary(getImages()) || failure;
        failure = !createDirectoryIfNecessary(getImageCache()) || failure;
        failure = !createDirectoryIfNecessary(getEtc()) || failure;
        failure = !createDirectoryIfNecessary(getCache()) || failure;
        failure = !createDirectoryIfNecessary(getWww()) || failure;
//        failure = !createDirectoryIfNecessary(getBlogs()) || failure;
//        failure = !createDirectoryIfNecessary(getBlogImports()) || failure;
        failure = !createDirectoryIfNecessary(getPlugins()) || failure;
        failure = !createDirectoryIfNecessary(getSearch()) || failure;
        failure = !createDirectoryIfNecessary(getSearchText()) || failure;
        failure = !createDirectoryIfNecessary(getCrypto()) || failure;
//        failure = !createDirectoryIfNecessary(getHelp()) || failure;
//        failure = !createDirectoryIfNecessary(getAttachments()) || failure;
//        failure = !createDirectoryIfNecessary(getAttachmentCache()) || failure;
        failure = !createDirectoryIfNecessary(getResources()) || failure;
        failure = !createDirectoryIfNecessary(getJMSData()) || failure;
        failure = !createDirectoryIfNecessary(getModules()) || failure;

//        File spelling = getSpelling();
//        failure = !createDirectoryIfNecessary(spelling) || failure;
//        failure = !populateDirectoryIfNecessary(spelling, "/spelling", threadLoader) || failure;

//        File attachmentImages = getAttachmentImages();
//        failure = !createDirectoryIfNecessary(attachmentImages) || failure;
//        failure = !populateDirectoryIfNecessary(attachmentImages, "/images/attachments", threadLoader) || failure;
//
//        File attachmentImageCache = getAttachmentImageCache();
//        failure = !createDirectoryIfNecessary(attachmentImageCache) || failure;
//        failure = !populateDirectoryIfNecessary(attachmentImageCache, "/images/attachments", threadLoader) || failure;

        File geoIP = getGeoIP();
        failure = !createDirectoryIfNecessary(geoIP) || failure;
        failure = !populateDirectoryIfNecessary(geoIP, "/geoip", threadLoader) || failure;

//        File scripts = getScripts();
//        failure = !createDirectoryIfNecessary(scripts) || failure;

//        copyScripts(threadLoader, scripts);

        File rimages = new File(getResources(), "images");
        failure = !createDirectoryIfNecessary(rimages) || failure;
        
        File rstatus = new File(rimages, "status");
        failure = !createDirectoryIfNecessary(rstatus) || failure;
        failure = !populateDirectoryIfNecessary(rstatus, "/images/status", threadLoader) || failure;

        File rattach = new File(rimages, "attachments");
        failure = !createDirectoryIfNecessary(rattach) || failure;
        failure = !populateDirectoryIfNecessary(rattach, "/images/attachments", threadLoader) || failure;

//        File themes = getThemes();
//        failure = !createDirectoryIfNecessary(themes) || failure;
//        failure = !populateDirectoryIfNecessary(themes, "/themes", threadLoader) || failure;

        // Generate a node ID if we need one
        //create a unique id for this node, store it in appsHome
        try {
            File nodeIdFile = new File(AppsHome.getAppsHome(), "node.id");
            if (!nodeIdFile.exists()) {
                log.info("Generating new node UUID.");
                if (!nodeIdFile.createNewFile()) {
                    log.fatal("Unable to create new node UUID file '" + nodeIdFile.getAbsolutePath() + "'.");
                }
                else {
                    UUID nodeUuid = UUID.randomUUID();
                    InputStream in = new ByteArrayInputStream(nodeUuid.toString().getBytes());
                    OutputStream out = new FileOutputStream(nodeIdFile);
                    InputOutputStreamWrapper wrapper = new InputOutputStreamWrapper(1024, true, in, out);
                    wrapper.doWrap();
                    log.info("New node UUID persisted.");
                }
            }
            else {
                log.info("Node UUID already present, skipping generation.");
            }
        }
        catch (Exception e) {
            log.warn("Unexpected error building node UUID.", e);
            failure = true;
        }

        if (failure) {
            log.warn("Initialization failures encountered. Please check application logs for details. "
                    + "The system may be unstable.");
        }
        else {
            log.info("Initialization completed sucessfully.");
        }

        initialized = true;
    }

//    private static void copyScripts(ClassLoader threadLoader, File scripts) {
//        String copyScriptsProp = System.getProperty(COPY_SCRIPTS_TO_HOME);
//        String notCopiedMessage = COPY_SCRIPTS_TO_HOME + " - Classpath scripts will not be copied to appsHome";
//        
//        if (StringUtils.isNotBlank(copyScriptsProp) && !BooleanUtils.toBoolean(copyScriptsProp)) {
//            log.info("Copy Scripts set to FALSE: " + notCopiedMessage);
//            return;
//        }
//        else if (BooleanUtils.toBoolean(System.getProperty("apps.devMode")) && !BooleanUtils
//                .toBoolean(copyScriptsProp))
//        {
//            log.info("devMode enabled " + notCopiedMessage);
//            return;
//        }
//
//        try {
//            ClassPathUtils.copyPath(threadLoader, CLASSPATH_RESOURCE_ROOT + "/scripts", scripts);
//        }
//        catch (Exception ex) {
//            log.warn("Faiure to copy scripts to '" + scripts.getAbsolutePath() + "'.", ex);
//        }
//    }

    private static boolean populateDirectoryIfNecessary(File dir, String path, ClassLoader threadLoader) {
        boolean success = true;

        if (dir.list() == null || dir.list().length == 0) {
            try {
                ClassPathUtils.copyPath(threadLoader, CLASSPATH_RESOURCE_ROOT + path, dir);
            }
            catch (Exception ex) {
                log.warn("Failed to populate directory at '" + dir.getAbsolutePath() + "'.", ex);
                success = false;
            }
        }

        return success;
    }

    /**
     * Returns true if the directory already exists or was created successfully, false otherwise
     *
     * @param dir the directory to test
     * @return true if the directory already exists or was created successfully, false otherwise
     */
    private static boolean createDirectoryIfNecessary(File dir) {
        boolean success = true;

        if (!dir.exists()) {
            String msg = "Failed to create directory at '" + dir.getAbsolutePath() + "'.";
            try {
                if (!dir.mkdirs()) {
                    log.warn(msg);
                    success = false;
                }
            }
            catch (Exception ex) {
                log.warn(msg, ex);
                success = false;
            }
        }

        return success;
    }

    private static void createAppsStartupXmlFile(File startupFile) throws IOException {
        // autocreate the apps_startup.xml file if it doesn't exist
        FileWriter jhWriter = new FileWriter(startupFile);
        jhWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        jhWriter.write("<apps>\n");
        jhWriter.write("    <setup>false</setup>\n");
        jhWriter.write("</apps>\n");
        jhWriter.flush();
        jhWriter.close();
    }

    /**
     * Returns a file handle to the environment-configured application
     * path to the exploded package (WAR file) loaded by the runtime.
     *
     * Depending on the runtime configuration, this path may not be configured
     * (i.e. development environments).
     *
     * @return a file handle to the environment-configured application path to the exploded package (WAR file)
     * loaded by the runtime.
     */
    public static File getApplication() {
        return new File(getApplicationPath());
    }

    /**
     * Returns a string path to the environment-configured application path to the exploded package (WAR file)
     * loaded by the runtime.
     *
     * @return a string path to the environment-configured application path to the exploded package (WAR file)
     * loaded by the runtime
     */
    public static String getApplicationPath() {
        StringBuilder buffer = new StringBuilder(getAppsRootPath());
        buffer.append(File.separator).append(getName()).append(File.separator);
        buffer.append("application");
        return buffer.toString();
    }

    /**
     * Returns the logical application name. The expectation is that the runtime has this set
     * as "apps.name", either via system property of via environment variable with the latter taking
     * prescedence.
     *
     * @return the logical application name.
     */
    public static String getName() {
    	String name = AppsInstanceLoader.getAppsInstance().getName();
    	if(name != null){
    		return name;
    	}

    	String envName = System.getenv("apps.name");
        return envName == null ? System.getProperty("apps.name", "unknown") : envName;
    }

    /**
     * Returns the etc configuration directory located in the apps home path.
     *
     * @return the etc configuration directory located in the apps home path.
     */
    public static File getEtc() {
        return new File(getAppsHome(), "etc");
    }

    /**
     * Returns the static HTTP resource directory located in the apps home path.
     *
     * @return the static HTTP resource directory located in the apps home path.
     */
    public static File getWww() {
        return new File(getAppsHome(), "www");
    }

    /**
     * Returns the cache directory located in the apps home path.
     *
     * @return the cache directory located in the apps home path.
     */
    public static File getCache() {
        return new File(getAppsHome(), "cache");
    }

    /**
     * Returns the plugin directory located in the apps home path.
     *
     * @return the plugin directory located in the apps home path.
     */
    public static File getPlugins() {
        return new File(getAppsHome(), "plugins");
    }

//    /**
//     * Returns the attachment directory located in the apps home path.
//     *
//     * @return the attachment directory located in the apps home path.
//     */
//    public static File getAttachments() {
//        return new File(getAppsHome(), "attachments");
//    }

//    /**
//     * Returns the attachement cache directory located in the apps home path.
//     *
//     * @return the attachement cache directory located in the apps home path.
//     */
//    public static File getAttachmentCache() {
//        return new File(getAttachments(), "cache");
//    }

//    /**
//     * Returns the attachment image directory located in the apps home path.
//     *
//     * @return the attachment image directory located in the apps home path.
//     */
//    public static File getAttachmentImages() {
//        return new File(getImages(), "attachments");
//    }

//    /**
//     * Returns the attachment image cache directory located in the apps home path.
//     *
//     * @return the attachment image cache directory located in the apps home path.
//     */
//    public static File getAttachmentImageCache() {
//        return new File(getAttachments(), "images");
//    }
//
//    /**
//     * Returns the blogs directory located in the apps home path.
//     *
//     * @return the blogs directory located in the apps home path.
//     */
//    public static File getBlogs() {
//        return new File(getAppsHome(), "blogs");
//    }

//    /**
//     * Returns the blog imports directory located in the apps home path.
//     *
//     * @return the blog imports directory located in the apps home path.
//     */
//    public static File getBlogImports() {
//        return new File(getBlogs(), "imports");
//    }

    /**
     * Returns the crypto directory located in the apps home path.
     *
     * @return the crypto directory located in the apps home path.
     */
    public static File getCrypto() {
        return new File(getAppsHome(), "crypto");
    }

    /**
     * Returns the image directory located in the apps home path.
     *
     * @return the image directory located in the apps home path.
     */
    public static File getImages() {
        return new File(getAppsHome(), "images");
    }

    /**
     * Returns the image cache directory located in the apps home path.
     *
     * @return the image cache directory located in the apps home path.
     */
    public static File getImageCache() {
        return new File(getImages(), "cache");
    }

    /**
     * Returns the resources directory located in the apps home path.
     *
     * @return  the resources directory located in the apps home path.
     */
    public static File getResources() {
        return new File(getAppsHome(), "resources");
    }

//    /**
//     * Returns the spelling directory located in the apps home path.
//     *
//     * @return the spelling directory located in the apps home path.
//     */
//    public static File getSpelling() {
//        return new File(getAppsHome(), "spelling");
//    }

    /**
     * Returns the contextual help directory located in the apps home path.
     *
     * @return the contextual help directory located in the apps home path.
     */
    public static File getHelp() {
        return new File(getAppsHome(),"help");
    }

    /**
     * Returns the GeoIP data directory located in the apps home path.
     *
     * @return the GeoIP data directory located in the apps home path.
     */
    public static File getGeoIP() {
        return new File(getAppsHome(), "geoip");
    }

    /**
     * Returns the search directory located in the apps home path.
     *
     * @return the search directory located in the apps home path.
     */
    public static File getSearch() {
        return new File(getAppsHome(), "search");
    }

    /**
     * Returns the saved search text directory located in the apps home path.
     *
     * @return the saved search text directory located in the apps home path.
     */
    public static File getSearchText() {
        return new File(getSearch(), "text");
    }

    /**
     * Returns the stats directory located in the apps home path.
     *
     * @return the stats directory located in the apps home path.
     */
    public static File getStats() {
        return new File(getAppsHome(), "stats");
    }

    /**
     * Returns the temp directory located in the apps home path.
     *
     * @return the temp directory located in the apps home path.
     */
    public static File getTemp() {
        return new File(getAppsHome(), "temp");
    }

//    /**
//     * Returns the themes directory located in the apps home path.
//     *
//     * @return the themes directory located in the apps home path.
//     */
//    public static File getThemes() {
//        return new File(getAppsHome(), "themes");
//    }

    /**
     * Returns the JMS data directory located in the apps home path.
     *
     * @return the JMS data directory located in the apps home path.
     */
    public static File getJMSData() {
        return new File(getAppsHome(), "jms");
    }

    /**
     * Returns the application configuration file in the apps home path.
     *
     * @return the application configuration file in the apps home path.
     */
    public static File getConfigurationFile() {
        return new File(getAppsHome(), CONFIG_FILE);
    }

    /**
     * Returns the path to the apps license file which may not exist depending on the context of the application.
     *
     * @return the path to the apps license file which may not exist depending on the context of the application.
     */
    public static File getLicenseFile() {
        return new File(getAppsHome(), LICENSE_FILE);
    }

//    /**
//     * Returns the instance-specific copy of the master set of javascript scripts.
//     *
//     * @return the instance-specific copy of the master set of javascript scripts.
//     */
//    public static File getScripts() {
//        return new File(getResources(), "scripts");
//    }

    /**
     * Returns the log directory of the application. This may be overwritten via code or environment values. The
     * file returned will never be null, but may not exist.
     *
     * @return the log directory of the application.
     */
    public static File getAppsLogs() {
        return new File(getAppsLogsPath());
    }

    /**
     * Returns the path to the apps log application within the system. This may be overwritten via code or environment 
     * values. The path returned will never be null, but may not exist.
     * 
     * @return the path to the apps log application within the system
     */
    public static String getAppsLogsPath() {
        if(effectiveAppsLogPath != null) {
            return effectiveAppsLogPath;
        }
        else {
            effectiveAppsLogPath = getEnvironmentAppsLogsPath();
        }

        return effectiveAppsLogPath;
    }

    /**
     * Returns the home directory of the application. This may be overwritten via code or environment values. The 
     * file returned will never be null, but may not exist.
     * 
     * @return the home directory of the application
     */
    public static File getAppsHome() {
        return new File(getAppsHomePath());
    }

    /**
     * Returns the path to the apps home application within the system. This may be overwritten via code or
     * environment values. The path returned will never be null, but may not exist.
     * 
     * @return he path to the apps home application within the system.
     */
    public static String getAppsHomePath() {
        if(effectiveAppsHomePath != null) {
            return effectiveAppsHomePath;
        }
        else {
            effectiveAppsHomePath = getEnvironmentAppsHomePath();
        }

        return effectiveAppsHomePath;
    }

    /**
     * Returns the root of the apps installation on the local system. This may be overridden by environment 
     * configurations. The file returned will never be null, but may not exist.
     *
     * @return the root of the apps installation on the local system
     */
    public static File getAppsRoot() {
        return new File(getAppsRootPath());
    }

    /**
     * Returns the root path for the runtime. This will varry depending
     * on the environment and programmatic changes to the runtime
     * in some cases. The value returned should never be null
     * but may not exist on the filesystem.
     *
     * @return the root path for the runtime
     */
    public static String getAppsRootPath() {
        if (effectiveAppsRootPath != null) {
            return effectiveAppsRootPath;
        }
        else {
            effectiveAppsRootPath = getEnvironmentAppsRootPath();
        }
        return effectiveAppsRootPath;
    }

    /**
     * Returns the log path for the runtime. This will varry depending
     * on the environment and programmatic changes to the runtime
     * in some cases. The value returned should never be null
     * but may not exist on the filesystem.
     *
     * @return the log path for the runtime
     */
    public static String getAppsLogPath() {
        if (effectiveAppsLogPath != null) {
            return effectiveAppsLogPath;
        }
        else {
            effectiveAppsLogPath = getEnvironmentAppsLogsPath();
        }
        return effectiveAppsLogPath;
    }

    /**
     * Returns the log path for JMS data at runtime.
     *
     * @return the log path for JMS data at runtime.
     */
    public static String getJMSDataPath() {
        if (effectiveJMSDataPath != null) {
            return effectiveJMSDataPath;
        }
        else {
            effectiveJMSDataPath = getJMSData().getPath();
        }
        return effectiveJMSDataPath;
    }

    /**
     * Validates the root structure provided to the runtime to determine if the runtime is hosted by a managed
     * installation.
     *
     * @return true iff this is a managed install, false otherwise
     */
    public static boolean isManagedInstall() {
        File root = getAppsRoot();
        //presence of root, log and home directory is hopefully a good enough heuristic
        return !(!root.exists() || !root.isDirectory()) || getAppsLogs().exists() && getAppsHome().exists();
    }

    public static UUID getNodeID() {

        try {
            if (nodeUuid == null) {
                File nodeIdFile = new File(AppsHome.getAppsHome(), "node.id");
                InputStream is = new BufferedInputStream(new FileInputStream(nodeIdFile));
//                InputStream is = new BufferedInputStream(FileUtils.newFileInputStream(nodeIdFile));
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                InputOutputStreamWrapper wrapper = new InputOutputStreamWrapper(1024, true, is, out);
                wrapper.doWrap();
                nodeUuid = UUID.fromString(new String(out.toByteArray()));
            }
            return nodeUuid;
        }
        catch (Exception e) {
            log.warn("Unexpected error retrieving node UUID.", e);
            return null;
        }
    }

    /**
     * Indicates if AppsHome has been initialized - this will almost always be yes
     * except for the case where AppsGlobals has a circular dependency on this class.
     *
     * @return true iff the appsHome has been initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Instances of this class should not be created.
     */
    private AppsHome() {
        //no-op
    }
    
    
    public static void main(String[] args) throws Exception{
    	AppsInstanceLoader.initialize(ResourceUtils.getURL("classpath:apps_build.xml"));
    	AppsHome.initialize();
    	
    	System.out.println(AppsHome.getAppsLogPath());
    	System.out.println(AppsHome.getAppsLogsPath());
    	
    	AppsContextLoader contextLoader = new AppsContextLoader();
    	List<String> contextFiles = contextLoader.getContextFiles();
    	for(String file:contextFiles){
    		System.out.println(file);
    	}
    	
    	//String path = JiveHome.getAppsRootPath();
    	//System.out.println(JiveHome.getAppsHomePath());
    	StopWatch sw = new StopWatch("Test");
    	
    	sw.start("ResourceUtils.getURL()");
    	URL file = ResourceUtils.getURL("classpath*:struts-plugin.xml");
    	sw.stop();
    	
    	System.out.println(file);
    	System.out.println("----------------");
    	
    	sw.start("PathMatchingResourcePatternResolver.getResources()");
    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(AppsHome.class.getClassLoader());
    	Resource[] resources = resolver.getResources("classpath*:struts-plugin.xml");
    	sw.stop();
    	
    	System.out.println(resources);
    	for(Resource r: resources){
    		System.out.println(r.getURI());
    	}
    	System.out.println("----------------");
    	
    	sw.start("getClassLoader().getResources()");
    	Enumeration<URL> resources2 = AppsHome.class.getClassLoader().getResources("struts-plugin.xml");
    	sw.stop();
    	
    	System.out.println(resources2);
    	while(resources2.hasMoreElements()){
    		System.out.println(resources2.nextElement());
    	}
    	System.out.println("----------------");
    	
    	sw.start("Thread.currentThread().getContextClassLoader().getResources()");
    	resources2 = Thread.currentThread().getContextClassLoader().getResources("apps_build.xml");
    	sw.stop();
    	
    	System.out.println(resources2);
    	while(resources2.hasMoreElements()){
    		System.out.println(resources2.nextElement());
    	}
    	System.out.println("----------------");
    	
    	
    	sw.start("ClassLoaderUtil.getResources()");
    	Iterator<URL> iterator = ClassLoaderUtil.getResources("apps_build.xml", AppsHome.class, true);
    	sw.stop();
    	System.out.println(iterator);
    	while(iterator.hasNext()){
    		System.out.println(iterator.next());
    	}
    	System.out.println("----------------");
    	
    	System.out.println(sw.prettyPrint());
    }
}
