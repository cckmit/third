/*
 * $Id: JXLSImportView.java 6213 2013-05-21 08:43:03Z lf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.io.jxls;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReadMessage;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;
import org.opoo.apps.Model;

import cn.redflagsoft.base.io.ExcelImportView;

/**
 * @author Alex Lin
 *
 */
public class JXLSImportView extends ExcelImportView {
	private static final Log log = LogFactory.getLog(JXLSImportView.class);
	
	private String config;

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.io.ImportView#doImport(java.io.InputStream)
	 */
	public Model doImport(InputStream inputStream) throws Exception {
        ReaderConfig.getInstance().setSkipErrors( true );
		InputStream configXML = new BufferedInputStream(new FileInputStream(buildConfigPath()));//new BufferedInputStream(ProjectImportSample.class.getResourceAsStream(xmlConfig));
		XLSReader mainReader = ReaderBuilder.buildFromXML(configXML);
		InputStream inputXLS = new BufferedInputStream(inputStream);//new BufferedInputStream(ProjectImportSample.class.getResourceAsStream(dataXLS));
		
		Model model = new Model();
		model.setRows(new ArrayList<Object>());
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("model", model);

		XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
		
		List<?> messages = readStatus.getReadMessages();
		if(messages != null && messages.size() > 0){
			for(int i = 0, n = messages.size() ; i < n ; i++){
				XLSReadMessage msg = (XLSReadMessage)messages.get(i);
				String message = msg.getMessage();
				Exception exception = msg.getException();
				model.addError(message, exception != null ? exception.getMessage() : "");
				
				if(log.isDebugEnabled()){
					log.error(message, exception);
				}
			}
		}
		return model;
	}
	private String buildConfigPath(){
		String path = AppsHome.getAppsHomePath() + config;
		if(log.isDebugEnabled()){
			log.debug("配置文件：" + path);
		}
		return path;
	}
	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}
	/**
	 * @param config the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
	}

}
