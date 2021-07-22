package org.opoo.apps.web.struts2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.service.AttachmentManager;

/**
 * 多文件同时上传的Action。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MultiFileUploadAction_BACKUP extends AbstractAppsAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(MultiFileUploadAction_BACKUP.class);
	private AttachmentManager attachmentManager;
	
	private File[] upfile;
	private String[] upfileContentType;
	private String[] upfileFileName;
	
	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}
	public File[] getUpfile() {
		return upfile;
	}
	public void setUpfile(File[] upfile) {
		this.upfile = upfile;
	}
	public String[] getUpfileContentType() {
		return upfileContentType;
	}
	public void setUpfileContentType(String[] upfileContentType) {
		this.upfileContentType = upfileContentType;
	}
	public String[] getUpfileFileName() {
		return upfileFileName;
	}
	public void setUpfileFileName(String[] upfileFileName) {
		this.upfileFileName = upfileFileName;
	}
	
	public String execute() {
		List<Attachment> result = new ArrayList<Attachment>();
		for(int i = 0 ; i < upfile.length ; i++){
			if(log.isDebugEnabled()){
				log.debug("上传文件名：" + upfileFileName[i]);
				log.debug("上传文件类型：" + upfileContentType[i]);
			}
			//Attachment a = new Attachment();
			//a.setContentType(upfileContentType[i]);
			//a.setFileName(upfileFileName[i]);
			//a.setFileType(FileUtils.getExtensionName(upfileFileName[i]));
			try {
				//InputStream is = new BufferedInputStream(new FileInputStream(upfile[i]));
				Attachment a = attachmentManager.store(upfile[i], upfileFileName[i], upfileContentType[i]);
				result.add(a);
			} catch (Exception e) {
				model.setException(e);
				log.error(e);
				return SUCCESS;
			}
		}
		
		model.setMessage("上传成功");
		model.setRows(result);
		return SUCCESS;
	}
}
