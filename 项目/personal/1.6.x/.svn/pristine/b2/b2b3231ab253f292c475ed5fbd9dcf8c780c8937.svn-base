package org.opoo.apps.web.struts2;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;

/**
 * 单文件上传的Action。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SingleFileUploadAction extends AbstractFileUploadAppsAction {
	private static final long serialVersionUID = 572146812454l;
	private static final Log log = LogFactory.getLog(SingleFileUploadAction.class);
	
	private File upfile;
	private String upfileContentType;
	private String upfileFileName;
	private String actualName;

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
		log.debug("set actualName: " + actualName);
	}

	

	public String getUpfileContentType() {
		return upfileContentType;
	}

	public void setUpfileContentType(String upfileContentType) {
		this.upfileContentType = upfileContentType;
	}

	public File getUpfile() {
		return upfile;
	}

	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}

	public String getUpfileFileName() {
		return upfileFileName;
	}

	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}
	

	@Override
	public String execute() {
		try {
			if(log.isDebugEnabled()){
				log.debug("Parameters: " + ActionContext.getContext().getParameters());
			}
			model = upload(upfile, upfileFileName, upfileContentType);
		} catch (Exception e) {
			model.setException(e);
			log.error(e);
			return SUCCESS;
		}
		
		return SUCCESS;
	}
}
