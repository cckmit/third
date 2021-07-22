package org.opoo.apps.dv.office.conversion.jive.modules.docconverter;

import java.io.File;

import org.apache.log4j.Logger;
import org.opoo.apps.AppsHome;
import org.opoo.apps.dv.util.Utils;

import com.jivesoftware.office.config.ConfigManager;
import com.jivesoftware.office.config.Configuration;
import com.jivesoftware.office.config.ConversionConfig.FileCacheConfig;
import com.jivesoftware.office.config.ConversionConfig.SecurityManagerConfig;
import com.jivesoftware.office.config.impl.ConfigManagerImpl;
import com.jivesoftware.office.manager.ConversionCommandType;


/**
 * 实现 jive.modules.docconverter 中的 ConfigManager 接口，该类增加了
 * 一些方法，以方便在 spring 容器中配置。
 * @author lcj
 *
 */
public class JiveConfigManagerImpl implements ConfigManager {
	private static final Logger logger = Logger.getLogger(JiveConfigManagerImpl.class);
	private ConfigManagerImpl configManager;
	
	public JiveConfigManagerImpl(String configFileLocation, String overrideExecFileLocation) throws Exception {
		if(!Utils.isDevMode()){
			File file = new File(AppsHome.getAppsHome(), "docconverter-config.xml");
			if(file.exists() && file.canRead()){
				logger.info("Using config file in apps.hme for docconverter: " + file);
				System.setProperty(configFileLocation, file.getAbsolutePath());
			}
		}
		configManager = new ConfigManagerImpl(configFileLocation, overrideExecFileLocation);
	}
	
	public Configuration getPDF2SWFConfig(){
		return getCommandConfig(ConversionCommandType.PDF2SWF);
	}
	public Configuration getPDF2SWFPBMConfig(){
		return getCommandConfig(ConversionCommandType.PDF2SWFPBM);
	}
	public Configuration getIMAGERConfig(){
		return getCommandConfig(ConversionCommandType.IMAGER);
	}
	public Configuration getOFFICETOPDFConfig(){
		return getCommandConfig(ConversionCommandType.OFFICETOPDF);
	}
	public Configuration getTEXTEXTRACTConfig(){
		return getCommandConfig(ConversionCommandType.TEXTEXTRACT);
	}
	public Configuration getCommandConfig(ConversionCommandType command) {
		return configManager.getCommandConfig(command);
	}
	public FileCacheConfig getFileCacheConfig() {
		return configManager.getFileCacheConfig();
	}
	public SecurityManagerConfig getSecurityManagerConfig() {
		return configManager.getSecurityManagerConfig();
	}
}
