package org.opoo.apps.service.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.file.handler.FileHandler;
import org.opoo.apps.file.handler.FileHandlerChain;
import org.opoo.apps.file.handler.FileInfo;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.apps.util.AppsMapUtils;
import org.springframework.util.Assert;

import java.util.Map;

public class AttachmentFileHandler implements FileHandler {
	private final static Log log = LogFactory.getLog(AttachmentFileHandler.class);
	private AttachmentManager attachmentManager;
		
	/**
	 * @return the attachmentManager
	 */
	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}
	/**
	 * @param attachmentManager the attachmentManager to set
	 */
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}



	public Model handle(Model model, FileInfo inputFile, FileHandlerChain chain) throws Exception {
		Assert.notNull(inputFile.getFile(), "上传文件为空。");

		Map<String, Object> parameters = inputFile.getParameters();
		if(log.isDebugEnabled()){
			log.debug("Parameters: " + parameters);
		}

		String pf = AppsMapUtils.getString(parameters, "pf");
		log.info("===受保护模式为: " + pf + "===");

		String rf = AppsMapUtils.getString(parameters, "rf");
		log.info("===只模式为: " + pf + "===");

		Attachment a = attachmentManager.store(inputFile.getFile(), inputFile.getFileName(), inputFile.getContentType(), pf, rf);
		model.setData(a, "文件上传成功");
		//return model;
		return chain.handle(model, inputFile);
	}
}
