package org.opoo.apps.web.struts2;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.util.FileUtils;

/**
 * 单文件上传的Action。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SingleFileUploadAction_BACKUP extends AbstractAppsAction {


	private static final long serialVersionUID = 572146812454l;
	private static final Log log = LogFactory.getLog(SingleFileUploadAction_BACKUP.class);
	
	private AttachmentManager attachmentManager;
	
	private File upfile;
	private String upfileContentType;
	private String upfileFileName;
	private String utf8;
	private String gbk;
	private String actualName;
	private String config;

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
		log.debug("set actualName: " + actualName);
	}

	
	public String getGbk() {
		return gbk;
	}

	public void setGbk(String gbk) {
		this.gbk = gbk;
	}

	public String getUtf8() {
		return utf8;
	}

	public void setUtf8(String utf8) {
		this.utf8 = utf8;
		log.debug("set utf8: " + utf8);
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
	
	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	@Override
	public String execute() {
		//String ft = FileUtils.getExtensionName(upfileFileName);
		//if(StringUtils.isBlank(ft)){
		///	ft = "." + upfileFileName.substring(upfileFileName.length() - 3);
		//}
				
		if("1".equals(utf8)){
			try {
				upfileFileName = new String(upfileFileName.getBytes("GBK"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}
		if("1".equals(gbk)){
			try {
				upfileFileName = new String(upfileFileName.getBytes("UTF-8"), "GBK");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}
		
		if(StringUtils.isNotBlank(actualName)){
			upfileFileName = actualName;
		}
		
		if(log.isDebugEnabled()){
			try{
				//log.debug("上传文件名：" + new String(upfileFileName.getBytes("UTF-8"), "GBK"));
				//log.debug("上传文件名：" + new String(upfileFileName.getBytes(), "UTF-8"));
				//log.debug("上传文件名：" + new String(upfileFileName.getBytes("GBK"), "UTF-8"));
				//log.debug("上传文件名：" + new String(upfileFileName.getBytes("GB2312"), "UTF-8"));
				//log.debug("上传文件名：" + new String(upfileFileName.getBytes("iso-8859-1"), "UTF-8"));
			
				//getRequest().setCharacterEncoding("UTF-8");
				//log.debug(getRequest().getParameter("Filename"));
				//log.debug(getRequest().getParameter("upfileFileName"));
				//getRequest().setCharacterEncoding("GBK");
				//log.debug(getRequest().getParameter("Filename"));
				//log.debug(getRequest().getParameter("upfileFileName"));
			}catch(Exception e){
				log.error(e);
			}
			log.debug("上传文件名：" + upfileFileName);
			log.debug("上传文件类型：" + upfileContentType);
			//log.debug("FILE_TYPE: " + ft);
			log.debug("actualName: " + actualName);
		}

		//Attachment a = new Attachment();
		//a.setContentType(upfileContentType);
		//a.setFileName(upfileFileName);
		//a.setFileType(ft);
		try {
			//InputStream is = new BufferedInputStream(new FileInputStream(upfile));
			//attachmentManager.store(a, is);
			
			Attachment a = attachmentManager.store(upfile, upfileFileName, upfileContentType);
			model.setData(a, "文件上传成功");
		} catch (Exception e) {
			model.setException(e);
			log.error(e);
			return SUCCESS;
		}

		
		return SUCCESS;
	}



	public static void main(String[] args) throws UnsupportedEncodingException{
		System.out.println(FileUtils.getExtensionName("askldja.sd.jpg"));
		String s = "未命名2.jpg";
		s = new String(s.getBytes("utf-8"), "gbk");
		System.out.println(FileUtils.getExtensionName(s));
		s = new String(s.getBytes("gbk"), "utf-8");
		System.out.println(s);
		s = new String(s.getBytes("utf-8"), "iso-8859-1");
		System.out.println(s);
		s = new String(s.getBytes("gbk"), "utf-8");
		System.out.println(s);
		
		
		java.util.List<Integer> ii = new java.util.ArrayList<Integer>();
		ii.add(9);
		ii.add(10);
		ii.add(0, 2);
		System.out.println(ii);
		
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
