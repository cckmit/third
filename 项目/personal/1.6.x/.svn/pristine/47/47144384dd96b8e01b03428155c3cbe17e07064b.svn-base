package org.opoo.apps.module;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.dom4j.Document;


/**
 * 模块头信息。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ModuleMetaData {
	public static enum Scope{
		cluster, local;
	};
	
	
	File getModuleDirectory();

	Module<?> getModule();

	ModuleClassLoader getClassLoader();

	Document getConfig();

	ResourceBundle getModuleResourceBundle(Locale locale);

	Map<String, String> getModuleProperties();

	String getName();

	String getDescription();

	String getAuthor();

	String getVersion();

	String getMinServerVersion();
	
	String getMaxServerVersion();

	//String getDatabaseKey();

	//int getDatabaseVersion();

	//LicenseType getLicense();

	boolean isReadmeExists();

	boolean isSmallLogoExists();

	boolean isLargeLogoExists();

	//boolean isChangelogExists();

	//ModuleDbBean getModuleDbBean();

	boolean isInstalled();

	boolean isUninstalled();
	
	/**
	 * 获取模块信息目录。
	 * @return
	 */
	File getModuleInfDirectory();
	
	/**
	 * 获取模块主配置文件。
	 * @return
	 */
	File getModuleConfiguration();
	
	/**
	 * 
	 * @return
	 */
	Scope getScope();
}
