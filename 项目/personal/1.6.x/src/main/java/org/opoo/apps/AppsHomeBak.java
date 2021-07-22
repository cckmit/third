package org.opoo.apps;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.ClassPathUtils;
import org.opoo.util.InputOutputStreamWrapper;


/**
 * Ӧ�ó��� Home��
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsHomeBak {

	/**
	 * License �ļ��ļ�����
	 */
	public static final String LICENSE_FILE = "apps.license";
	/**
	 * �����ļ��ļ�����
	 */
	public static final String CONFIG_FILE = "apps_startup.xml";
	/**
	 * ��־����ϵͳ��������
	 */
	public static final String APPS_LOG_KEY = "apps.logs";
	/**
	 * Ӧ�ó����·��ϵͳ��������
	 */
	public static final String APPS_HOME_KEY = "apps.home";
	/**
	 * Ӧ�ó����·��������������
	 */
	public static final String APPS_HOME_ENV_KEY = "APPS_HOME";
	/**
	 * ��Դ��Ŀ¼����
	 */
	public static final String CLASSPATH_RESOURCE_ROOT = "resources";
	
	
	private static UUID nodeUuid = null;
	private static final String startupAppsHomePath = getAppsHomePath();
	private static final String startupLogPath = getAppsLogsPath();
	private static String effectiveAppsHomePath;
	private static String effectiveAppsLogPath;
	private static boolean initialization = true;
	private static boolean initialized = false;
	
	private static final Log log = LogFactory.getLog(AppsHomeBak.class);

	static {
		initialization = false;
	}

	private AppsHomeBak() {
	}

	/**
	 * ��ȡ��������Ӧ�ó����·����
	 * @return
	 */
	public static String getStartupAppsHomePath() {
		return startupAppsHomePath;
	}

	/**
	 * ��ȡ��������Ӧ�ó�����־·����
	 * @return
	 */
	public static String getStartupAppsLogsPath() {
		return startupLogPath;
	}

	/**
	 * ���ÿ��õ�Ӧ�ó����·����
	 * @param path
	 */
	public static synchronized void setEffectiveAppsHomePath(String path) {
		if (path != null) {
			File pathFile = new File(path);
			if (pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() && pathFile.canWrite())
				effectiveAppsHomePath = path;
		} else {
			effectiveAppsHomePath = null;
		}
	}

	/**
	 * ���ÿ��õ�Ӧ�ó�����־·����
	 * @param path
	 */
	public static synchronized void setEffectiveAppsLogsPath(String path) {
		if (path != null) {
			File pathFile = new File(path);
			if (pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() && pathFile.canWrite())
				effectiveAppsLogPath = path;
		} else {
			effectiveAppsLogPath = null;
		}
	}

	/**
	 * �����������õ�Ӧ�ó�����־·����
	 * @return
	 */
	public static String getEnvironmentAppsLogsPath() {
		Log log = LogFactory.getLog(AppsHomeBak.class);
		String envPath = System.getProperty(APPS_LOG_KEY);
		if (envPath == null || "".equals(envPath))
			try {
				InitialContext context = new InitialContext();
				envPath = (String) context.lookup("java:comp/env/apps.logs");
			} catch (Exception e) {
			}
		if (envPath == null || "".equals(envPath)) {
			if (initialization)
				log.warn((new StringBuilder()).append(
						"No explicit configuration of Apps log path. Using system default of: '").append(envPath)
						.append("'.").toString());
			StringBuilder buffer = new StringBuilder(getAppsHomePath());
			buffer.append(File.separator).append("var").append(File.separator).append("logs");
			envPath = buffer.toString();
		}
		return envPath;
	}

	/**
	 * ��ȡ�������õ�Ӧ�ó����·����
	 * @return
	 */
	public static String getEnvironmentAppsHomePath() {
		Log log = LogFactory.getLog(AppsHomeBak.class);
		String envHomePath = System.getProperty(APPS_HOME_KEY);
		if (envHomePath == null || "".equals(envHomePath)){
			try {
				InitialContext context = new InitialContext();
				envHomePath = (String) context.lookup("java:comp/env/apps.home");
				if (envHomePath != null)
					log.info((new StringBuilder()).append("Apps home set from JNDI to '").append(envHomePath).append("'.")
							.toString());
			} catch (Exception e) {
			}
		}
		
		if (envHomePath == null || "".equals(envHomePath)) {
			envHomePath = System.getenv(APPS_HOME_ENV_KEY);
			if (envHomePath != null && !"".equals(envHomePath)){
				log.info((new StringBuilder()).append("Apps home set from system property to '").append(envHomePath)
						.append("'.").toString());
			}
		}
		
//		if(envHomePath == null || "".equals(envHomePath)){
//			envHomePath = (new InitPropLoader()).getAppsHome();
//			if(envHomePath != null && !"".equals(envHomePath)){
//				log.info("Apps home set from apps_init.xml");
//			}
//		}
		
		if (envHomePath == null || "".equals(envHomePath)) {
			StringBuilder buffer = new StringBuilder();
			if (System.getProperty("os.name", "Linux").indexOf("Windows") == -1)
				buffer.append(File.separator).append("usr").append(File.separator).append("local").append(
						File.separator).append("apps");
			else
				buffer.append("c:").append(File.separator).append("apps");
			envHomePath = buffer.toString();
			log.warn((new StringBuilder()).append(
					"No explicit configuration of Apps home path. Using system default of: '").append(envHomePath)
					.append("'.").toString());
		}
		return envHomePath;
	}

	/**
	 * ��ʼ��Ӧ�ó����·����
	 */
	public static synchronized void initialize() {
		Log log = LogFactory.getLog(AppsHomeBak.class);
		ClassLoader threadLoader = Thread.currentThread().getContextClassLoader();
		if (effectiveAppsHomePath == null)
			effectiveAppsHomePath = getEnvironmentAppsHomePath();
		if (effectiveAppsLogPath == null)
			effectiveAppsLogPath = getEnvironmentAppsLogsPath();
		File home = getAppsHome();
		if (!home.exists())
			try {
				home.mkdirs();
			} catch (Exception ex) {
				String message = "Failed to create home directory during initialization.";
				log.fatal(message, ex);
				throw new RuntimeException(message, ex);
			}
		boolean failure = false;
		if (!home.isDirectory()) {
			log.fatal("Configured apps home is not a directory.");
			failure = true;
		}
		if (!home.canRead()) {
			log.fatal("Cannot read from apps home directory.");
			failure = true;
		}
		if (!home.canWrite()) {
			log.fatal("Cannot write to apps home directory.");
			failure = true;
		}
		if (failure)
			throw new RuntimeException((new StringBuilder()).append("Unable to access configured apps home at '")
					.append(home.getAbsolutePath()).append("'. The system cannot operate in this state.").toString());
		File config = getConfigurationFile();
		if (!config.exists()){
			try {
				createAppsStartupXmlFile(config);
			} catch (Exception ex) {
				log.warn("Failed to create apps startup file.", ex);
				failure = true;
			}
		}
		
		//ͳ����ϢĿ¼
		File stats = getStats();
		if (!stats.exists()){
			try {
				stats.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create state directory at '").append(
						stats.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		}
		/*
		File attachments = getAttachments();
		File attachmentCache = getAttachmentCache();
		File attachmentImages = getAttachmentImages();
		File attachmentImageCache = getAttachmentImageCache();
		if (!attachments.exists())
			try {
				attachments.mkdirs();
				ClassPathUtils.copyPath(threadLoader, "resources/images/attachments", attachmentImages);
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create attachment directory at '").append(
						attachments.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		if (!attachmentCache.exists())
			try {
				attachmentCache.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create attachment cache directory at '").append(
						attachmentCache.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		if (!attachmentImages.exists())
			try {
				attachmentImages.mkdirs();
				ClassPathUtils.copyPath(threadLoader, "resources/images/attachments", attachmentImages);
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create attachment images directory at '").append(
						attachmentCache.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		if (!attachmentImageCache.exists())
			try {
				attachmentImageCache.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create attachment image cache directory at '").append(
						attachmentImageCache.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		*/	
			
		File cache = getCache();
		if (!cache.exists())
			try {
				cache.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create cache directory at '").append(
						cache.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
//		File www = getWww();
//		if (!www.exists())
//			try {
//				www.mkdirs();
//			} catch (Exception ex) {
//				log.warn((new StringBuilder()).append("Failed to create www directory at '").append(
//						www.getAbsolutePath()).append("'.").toString(), ex);
//				failure = true;
//			}
		File geoIP = getGeoIP();
		if (!geoIP.exists())
			try {
				geoIP.mkdirs();
				ClassPathUtils.copyPath(threadLoader, "resources/geoip", geoIP);
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create GeoIP directory at '").append(
						geoIP.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File temp = getTemp();
		if (!temp.exists())
			try {
				temp.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create temp directory at '").append(
						temp.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File logs = getAppsLogs();
		if (!logs.exists())
			try {
				logs.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create logs directory at '").append(
						logs.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File images = getImages();
		File imageCache = getImageCache();
		if (!images.exists())
			try {
				images.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create images directory at '").append(
						images.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		if (!imageCache.exists())
			try {
				imageCache.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create image cache directory at '").append(
						imageCache.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File etc = getEtc();
		if (!etc.exists())
			try {
				etc.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create etc directory at '").append(
						etc.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File resources = getResources();
		if (!resources.exists())
			try {
				resources.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create resources directory at '").append(
						resources.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File rimages = new File(resources, "images");
		if (!rimages.exists())
			try {
				if (!rimages.exists())
					rimages.mkdir();
				File rattach = new File(resources, "attachments");
				if (!rattach.exists()) {
					rattach.mkdir();
					ClassPathUtils.copyPath(threadLoader, "resources/images/attachments", rattach);
				}
				File status = new File(rimages, "status");
				if (!status.exists())
					ClassPathUtils.copyPath(threadLoader, "resources/images/status", status);
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create resource images directories at '").append(
						rimages.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}

		File plugins = getPlugins();
		if (!plugins.exists())
			try {
				plugins.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create plugins directory at '").append(
						plugins.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
			
			
		File search = getSearch();
		if (!search.exists())
			try {
				search.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create search directory at '").append(
						search.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		File crypto = getCrypto();
		if (!crypto.exists())
			try {
				crypto.mkdirs();
			} catch (Exception ex) {
				log.warn((new StringBuilder()).append("Failed to create crypto directory at '").append(
						crypto.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
			
		File modules = getModules();
		if(!modules.exists()){
			try{
				modules.mkdirs();
			}catch(Exception ex){
				log.warn((new StringBuilder()).append("Failed to create modules directory at '").append(
						modules.getAbsolutePath()).append("'.").toString(), ex);
				failure = true;
			}
		}
			

		try {
			File nodeIdFile = new File(getAppsHome(), "node.id");
			if (!nodeIdFile.exists()) {
				log.info("Generating new node UUID.");
				nodeIdFile.createNewFile();
				UUID nodeUuid = UUID.randomUUID();
				java.io.InputStream in = new ByteArrayInputStream(nodeUuid.toString().getBytes());
				OutputStream out = new FileOutputStream(nodeIdFile);
				InputOutputStreamWrapper wrapper = new InputOutputStreamWrapper(1024, true, in, new OutputStream[] { out });
				wrapper.doWrap();
				log.info("New node UUID persisted.");
			} else {
				log.info("Node UUID already present, skipping generation.");
			}
		} catch (Exception e) {
			log.warn("Unexpected error building node UUID.", e);
			failure = true;
		}
		if (failure)
			log.warn("Initialization failures encountered. Please check application logs for details. The system may be unstable.");
		else
			log.info("Initialization completed sucessfully.");
		initialized = true;
	}

	/**
	 * ����Ĭ�ϵ��������ļ���
	 * 
	 * @param startupFile
	 * @throws IOException
	 */
	private static void createAppsStartupXmlFile(File startupFile) throws IOException {
		FileWriter jhWriter = new FileWriter(startupFile);
		jhWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		jhWriter.write("<apps>\n");
		jhWriter.write("    <setup>false</setup>\n");
		jhWriter.write("</apps>\n");
		jhWriter.flush();
		jhWriter.close();
	}

	/**
	 * ϵͳ����Ŀ¼��
	 * @return
	 */
	public static File getEtc() {
		return new File(getAppsHome(), "etc");
	}

//	public static File getWww() {
//		return new File(getAppsHome(), "www");
//	}

	/**
	 * ϵͳ����Ŀ¼��
	 */
	public static File getCache() {
		return new File(getAppsHome(), "cache");
	}

	/**
	 * ϵͳ���Ŀ¼��
	 * @return
	 */
	public static File getPlugins() {
		return new File(getAppsHome(), "plugins");
	}
	
	/**
	 * ϵͳģ��Ŀ¼��
	 * @return
	 */
	public static File getModules(){
		return new File(getAppsHome(), "modules");
	}
	

//	public static File getAttachments() {
//		return new File(getAppsHome(), "attachments");
//	}
//
//	public static File getAttachmentCache() {
//		return new File(getAttachments(), "cache");
//	}
//
//	public static File getAttachmentImages() {
//		return new File(getImages(), "attachments");
//	}
//
//	public static File getAttachmentImageCache() {
//		return new File(getAttachments(), "images");
//	}

	/**
	 * ϵͳ�ӽ����㷨��֤��ȴ�ŵ�Ŀ¼��
	 */
	public static File getCrypto() {
		return new File(getAppsHome(), "crypto");
	}

	/**
	 * ϵͳͼƬĿ¼��
	 * @return
	 */
	public static File getImages() {
		return new File(getAppsHome(), "images");
	}

	/**
	 * ϵͳͼ�񻺴�Ŀ¼��
	 * ��������ͼ��
	 * @return
	 */
	public static File getImageCache() {
		return new File(getImages(), "cache");
	}

	/**
	 * ϵͳ��ԴĿ¼��
	 * @return
	 */
	public static File getResources() {
		return new File(getAppsHome(), "resources");
	}

	/**
	 * ϵͳ����IP����λ�ÿ��ļ����Ŀ¼��
	 * @return
	 */
	public static File getGeoIP() {
		return new File(getAppsHome(), "geoip");
	}

	/**
	 * ����������á������ȴ��Ŀ¼��
	 * 
	 * @return
	 */
	public static File getSearch() {
		return new File(getAppsHome(), "search");
	}

	/**
	 * ϵͳͳ�����ݴ��Ŀ¼��
	 * @return
	 */
	public static File getStats() {
		return new File(getAppsHome(), "stats");
	}

	/**
	 * ϵͳ������ʱĿ¼��
	 * @return
	 */
	public static File getTemp() {
		return new File(getAppsHome(), "temp");
	}


	/**
	 * ϵͳ�������ļ���
	 * @return
	 */
	public static File getConfigurationFile() {
		return new File(getAppsHome(), CONFIG_FILE);
	}

	/**
	 * License �ļ���
	 * @return
	 */
	public static File getLicenseFile() {
		return new File(getAppsHome(), LICENSE_FILE);
	}

	/**
	 * ϵͳ��־·����
	 * @return
	 */
	public static File getAppsLogs() {
		return new File(getAppsLogsPath());
	}

	/**
	 * ϵͳ��־·����
	 * @return
	 */
	public static String getAppsLogsPath() {
		if (effectiveAppsLogPath != null) {
			return effectiveAppsLogPath;
		} else {
			effectiveAppsLogPath = getEnvironmentAppsLogsPath();
			return effectiveAppsLogPath;
		}
	}

	/**
	 * Ӧ�ó����Ŀ¼��
	 * @return
	 */
	public static File getAppsHome() {
		return new File(getAppsHomePath());
	}
	/**
	 * Ӧ�ó����·����
	 * @return
	 */
	public static String getAppsHomePath() {
		if (effectiveAppsHomePath != null) {
			return effectiveAppsHomePath;
		} else {
			effectiveAppsHomePath = getEnvironmentAppsHomePath();
			return effectiveAppsHomePath;
		}
	}


//	public static String getAppsLogPath() {
//		if (effectiveAppsLogPath != null) {
//			return effectiveAppsLogPath;
//		} else {
//			effectiveAppsLogPath = getEnvironmentAppsLogsPath();
//			return effectiveAppsLogPath;
//		}
//	}

	/**
	 * Ӧ�ó����Ƿ��Ѱ�װ��
	 */
	public static boolean isManagedInstall() {
		return getAppsLogs().exists() && getAppsHome().exists();
	}

	/**
	 * ��ȡϵͳ�Ľڵ�ID��
	 * @return
	 */
	public static UUID getNodeID() {
		try {
			if (nodeUuid == null) {
				File nodeIdFile = new File(getAppsHome(), "node.id");
				java.io.InputStream is = new BufferedInputStream(new FileInputStream(nodeIdFile));
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputOutputStreamWrapper wrapper = new InputOutputStreamWrapper(1024, true, is,
						new OutputStream[] { out });
				wrapper.doWrap();
				nodeUuid = UUID.fromString(new String(out.toByteArray()));
			}
			return nodeUuid;
		} catch (Exception e) {
			log.error("Unexpected error retrieving node UUID.", e);
		}
		return null;
	}

	/**
	 * Ӧ�ó����Ƿ񱻳�ʼ����
	 * @return
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	public static void main2(String[] args) {
		//AppsHome.initialization = true;
		String s = AppsHomeBak.getAppsLogsPath();
		System.out.println(s);
		AppsHomeBak.initialize();
		System.out.println(System.getenv("M2_HOME"));
		System.out.println(AppsHomeBak.getNodeID());
		//System.out.println(AppsHome.getNodeID());
	}
	
	public static void main(String[] args) throws IOException{
		File file = File.createTempFile("module", ".jar", AppsHomeBak.getTemp());
		System.out.println(file);
		file.delete();
	}
}
