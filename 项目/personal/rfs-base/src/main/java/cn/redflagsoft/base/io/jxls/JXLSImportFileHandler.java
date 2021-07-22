/*
 * $Id: JXLSImportFileHandler.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.io.jxls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.file.handler.FileHandler;
import org.opoo.apps.file.handler.FileHandlerChain;
import org.opoo.apps.file.handler.FileInfo;

import cn.redflagsoft.base.io.ImportFileHandler;
import cn.redflagsoft.base.io.ImportView;

/**
 * @author Alex Lin
 *
 */
public class JXLSImportFileHandler extends ImportFileHandler implements FileHandler {
	private static final Log log = LogFactory.getLog(JXLSImportFileHandler.class);
	private JXLSImportView importView = new JXLSImportView();
	private String config;
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.handler.FileHandler#handle(org.opoo.apps.Model, org.opoo.apps.file.handler.InputFile, org.opoo.apps.file.handler.FileHandlerChain)
	 */
	public Model handle(Model model, FileInfo inputFile, FileHandlerChain chain) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("FileInfo.parameters: " + inputFile.getParameters());
		}
		importView.setConfig(config);
		return super.handle(model, inputFile, chain);
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

	/**
	 * @return the importView
	 */
	public ImportView getImportView() {
		return importView;
	}

	/**
	 * @param importView the importView to set
	 */
	public void setImportView(ImportView importView) {
		throw new UnsupportedOperationException("����������importView");
	}

	

}
