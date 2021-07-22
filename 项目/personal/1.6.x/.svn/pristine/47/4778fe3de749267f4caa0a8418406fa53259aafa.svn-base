package org.opoo.apps.web.struts2;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Model;
import org.opoo.apps.file.handler.FileHandlerChainManager;
import org.opoo.apps.file.handler.FileInfo;
import org.opoo.apps.file.handler.SimpleFileInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionContext;

public abstract class AbstractFileUploadAppsAction extends AbstractAppsAction implements InitializingBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4016741058167887967L;
	private static final Log log = LogFactory.getLog(AbstractFileUploadAppsAction.class);
	private FileHandlerChainManager fileHandlerChainManager;
	
	private String utf8;
	private String gbk;
	private String chains;

	/**
	 * @return the utf8
	 */
	public String getUtf8() {
		return utf8;
	}
	/**
	 * @param utf8 the utf8 to set
	 */
	public void setUtf8(String utf8) {
		this.utf8 = utf8;
	}
	/**
	 * @return the gbk
	 */
	public String getGbk() {
		return gbk;
	}
	/**
	 * @param gbk the gbk to set
	 */
	public void setGbk(String gbk) {
		this.gbk = gbk;
	}
	

	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(fileHandlerChainManager, "FileHandlerChainManager不能为空。");
	}
	/**
	 * @return the fileHandlerChainManager
	 */
	public FileHandlerChainManager getFileHandlerChainManager() {
		return fileHandlerChainManager;
	}
	/**
	 * @param fileHandlerChainManager the fileHandlerChainManager to set
	 */
	public void setFileHandlerChainManager(FileHandlerChainManager fileHandlerChainManager) {
		this.fileHandlerChainManager = fileHandlerChainManager;
	}	
	
	
	protected Model upload(File file, String fileName, String contentType){
		if("1".equals(utf8)){
			try {
				fileName = new String(fileName.getBytes("GBK"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}
		if("1".equals(gbk)){
			try {
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
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
			log.debug("上传文件名：" + fileName);
			log.debug("上传文件类型：" + contentType);
			//log.debug("FILE_TYPE: " + ft);
			//log.debug("actualName: " + actualName);
		}
		
		FileInfo fileInfo = new SimpleFileInfo(file, fileName, contentType, 0, ActionContext.getContext().getParameters());
		return fileHandlerChainManager.performChain(chains, fileInfo);
	}
	/**
	 * @return the chains
	 */
	public String getChains() {
		return chains;
	}
	/**
	 * @param chains the chains to set
	 */
	public void setChains(String chains) {
		this.chains = chains;
	}
}
