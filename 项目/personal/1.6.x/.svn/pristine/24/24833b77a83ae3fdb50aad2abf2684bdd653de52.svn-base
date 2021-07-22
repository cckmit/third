package org.opoo.apps.web.struts2;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.bean.core.Attachment;

/**
 * 多文件同时上传的Action。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MultiFileUploadAction extends AbstractFileUploadAppsAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(MultiFileUploadAction.class);
	
	private File[] upfile;
	private String[] upfileContentType;
	private String[] upfileFileName;
	
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
			try {
				Model m = upload(upfile[i], upfileFileName[i], upfileContentType[i]);
				Serializable data = m.getData();
				if(data!= null && data instanceof Attachment){
					result.add((Attachment) data);
				}
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
