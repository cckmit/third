package org.opoo.apps.module;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.dom4j.Document;


/**
 * ģ��ͷ��Ϣ��
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
	 * ��ȡģ����ϢĿ¼��
	 * @return
	 */
	File getModuleInfDirectory();
	
	/**
	 * ��ȡģ���������ļ���
	 * @return
	 */
	File getModuleConfiguration();
	
	/**
	 * 
	 * @return
	 */
	Scope getScope();
}
