package org.opoo.apps.web.struts2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.exception.AttachmentException;
import org.opoo.apps.license.annotation.ProductModule;
import org.opoo.apps.service.AttachmentManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �ļ����ص�ACTION��
 * ��Ҫ���AttachmentManagerʹ�á�
 * @author Alex Lin
 *
 */
@ProductModule(edition="common", module="core")
public class FileDownloadAction extends ActionSupport {
	public static final String PROP_EXPORT_FILE_NAME_ENCODING = "FileDownloadAction.exportFileName.encoding";
	public static final String PROP_FILE_NAME_ENCODING = "FileDownloadAction.fileName.encoding";
	
	private static final Log log = LogFactory.getLog(FileDownloadAction.class);
	private static final long serialVersionUID = 1L;
	
	private Attachment attachment;
	private AttachmentManager attachmentManager;
	private String format;
	private boolean readOnly = false;
	private String fileName = null;
	private Long id;
	
	private InputStream inputStream;
	
	public InputStream getInputStream() throws IOException{
//		try {
//			if(format != null){
//				return attachment.readInputStream(format);
//			}
//			return attachment.readInputStream();
//		} catch (Exception e) {
//			throw new AttachmentException("�������ݶ�ȡ����: " + id, e);
//		}
		//return ServletActionContext.getServletContext().getResourceAsStream(path);
		
		
		return inputStream;
	}
	
	
	public long getContentLength(){
		long size = attachment.getFileSize();
		if(log.isDebugEnabled()){
			log.debug("���ʵ��ļ����ȣ�" + size);
		}
		if(size > 0){
			return size;
		}
		return -1;
	}
	
	
	public String execute() throws Exception{
		attachment = attachmentManager.getAttachment(id);
		if(attachment == null){
			//ServletActionContext.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
			throw new AttachmentException("�Ҳ���������id = " + id);
			//return ERROR;
		}
		if(format != null 
					&& !format.equals(attachment.getOriginalFormat())
					&& !format.equals(attachment.getProtectedFormat())
					&& !format.equals(attachment.getReadableFormat())){
			throw new AttachmentException("Ҫ���ʵ��ļ����Ͳ����ڣ�" + format);
		}
		
		//������Ķ��ļ����Ȳ鿴�Ƿ����ֻ���ļ�������鿴�ܱ����ļ�������ȡԭʼ�ļ���
		if(readOnly && format == null){
			if(attachment.getReadableFormat() != null){
				format = attachment.getReadableFormat();
			}else if(attachment.getProtectedFormat() != null){
				format = attachment.getProtectedFormat();
			}else{
				format = null;
			}
		}
		
		if(format == null){
			format = attachment.getOriginalFormat();
		}
		
		if(log.isDebugEnabled()){
			log.debug("���ʵ��ļ����ͣ�" + format);
		}
		
		//inputstream
		if(format != null){
			inputStream = attachment.readInputStream(format);
		}else{
			inputStream = attachment.readInputStream();
		}
		
		return SUCCESS;
	}
	
	
	public String getFileName() throws UnsupportedEncodingException{
		if(fileName != null){
			fileName = parseFileName(fileName);
//			return new String(fileName.getBytes(), "ISO8859-1");
			return encodeFileName(fileName, PROP_EXPORT_FILE_NAME_ENCODING);
		}
		String name = attachment.getFileName();
		if(name != null){
			if(format != null && !attachment.getOriginalFormat().equals(format)){
				name += "." + format;
			}
//			return new String(name.getBytes(), "ISO8859-1");
			return encodeFileName(name, PROP_FILE_NAME_ENCODING);
		}
		return attachment.getFileName();
	}
	
	private static String encodeFileName(String fileName, String prop) throws UnsupportedEncodingException{
		String[] ea = getFileNameEncoding(prop);
		if(ea == null){
			return fileName;
		}
		if(log.isDebugEnabled()){
			log.debug("Encode file name: " + ea[0] + " -> " + ea[1] + " : " + fileName);
		}
		String from = ea[0];//GBK,,?
		if(StringUtils.isBlank(from) || "?".equals(from)){
			return new String(fileName.getBytes(), ea[1]);
		}else{
			return new String(fileName.getBytes(from), ea[1]);
		}
	}
	
	//UTF-8->GBK
	private static String[] getFileNameEncoding(String propName){
		String property = AppsGlobals.getProperty(propName);
		if(StringUtils.isBlank(property)){
			return null;
		}
		String[] arr = property.split("->");
		if(arr.length != 2){
			return null;
		}
		if(StringUtils.isBlank(arr[1])){
			return null;
		}
		return arr;
	}
	private static boolean isEncodingOriginEmpty(String[] ea){
		return StringUtils.isBlank(ea[0]) || "?".equals(ea[0]);
	}
	
	
	public String getContentType(){
		//return attachment.getContentType();	
		
		//if(attachment != null && attachment.getFileType() != null){
			//todo:����filetype��������
		//}
		
		return attachment.getContentType();
	}
	
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the attachment
	 */
	public Attachment getAttachment() {
		return attachment;
	}


	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}


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


	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}


	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}


	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}


	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	private static String parseFileName(String fileName){
		if(fileName == null){
			return null;
		}
		if(org.opoo.util.StringUtils.countUnquoted(fileName, '#') == 2){
			int p1 = fileName.indexOf('#');
			int p2 = fileName.indexOf('#', p1 + 1);
			//System.out.println(p1 + ", " + p2);
			String s1 = fileName.substring(0, p1);
			String s2 = fileName.substring(p1+1, p2);
			String s3 = fileName.length() >= p2 ? fileName.substring(p2 + 1) : "";
			fileName = s1 + formatNowToString(s2) + s3;
		}
		if(log.isDebugEnabled()){
			log.debug("�����ļ�����" + fileName);
		}
		return fileName;
	}
	
	private static String formatNowToString(String pattern){
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(new Date());
		} catch (Exception e) {
			log.debug("�޷���ʽ��ʱ��: " + pattern, e);
		}
		return "";
	}
	
	public static void main(String[] args){
		String s = "�ҵĹ���(#yyyy_MM_dd-HH_mm#).xls";
		String name = parseFileName(s);
		System.out.println(name);
		
		String property = "s->UTF-8";
		String[] arr = property.split("->");
		System.out.println(arr.length);
		for(String sss: arr){
			System.out.println("::" + sss);
			System.out.println("?".equals(sss));
		}
		System.out.println(isEncodingOriginEmpty(arr));
	}
}
