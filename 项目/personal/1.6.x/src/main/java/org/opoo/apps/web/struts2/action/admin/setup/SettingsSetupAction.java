package org.opoo.apps.web.struts2.action.admin.setup;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsGlobals;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 * TODO 其他设置尚未完成
 */
public class SettingsSetupAction extends SetupActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7546606563330991529L;
	public static final String FORMAT_STYLE_DATE = "formatStyle.dateFormat";
	public static final String FORMAT_STYLE_DATETIME = "formatStyle.dateTimeFormat";
	public static final String FORMAT_STYLE_SHORTDATATIME = "formatStyle.shortDateTimeFormat";
	

	private String shortDateTimeFormat;
	private String dateTimeFormat;
	private String dateFormat;
	/**
	 * @return the dateTimeFormat
	 */
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}
	/**
	 * @param dateTimeFormat the dateTimeFormat to set
	 */
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	
	/**
	 * @return the shortDateTimeFormat
	 */
	public String getShortDateTimeFormat() {
		return shortDateTimeFormat;
	}
	/**
	 * @param shortDateTimeFormat the shortDateTimeFormat to set
	 */
	public void setShortDateTimeFormat(String shortDateTimeFormat) {
		this.shortDateTimeFormat = shortDateTimeFormat;
	}
	
	/**
	 * 
	 */
	public String doDefault(){
		if(StringUtils.isBlank(dateFormat)){
			dateFormat = AppsGlobals.getSetupProperty(FORMAT_STYLE_DATE);
		}
		if(StringUtils.isBlank(dateFormat)){
			dateFormat = "yyyy-MM-dd";
		}
		
		if(StringUtils.isBlank(dateTimeFormat)){
			dateTimeFormat = AppsGlobals.getSetupProperty(FORMAT_STYLE_DATETIME);
		}
		if(StringUtils.isBlank(dateTimeFormat)){
			dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		}
		
		if(StringUtils.isBlank(shortDateTimeFormat)){
			shortDateTimeFormat = AppsGlobals.getSetupProperty(FORMAT_STYLE_SHORTDATATIME);
		}
		if(StringUtils.isBlank(shortDateTimeFormat)){
			shortDateTimeFormat = "yyyy-MM-dd HH:mm";
		}
		
		return ActionSupport.INPUT;
	}
	
	
	public void validateExecute(){
		if(StringUtils.isBlank(dateFormat)){
			addActionError("日期格式不能为空。");
		}else{
			try {
				SimpleDateFormat f = new SimpleDateFormat(dateFormat);
				f.format(new Date());
			} catch (Exception e) {
				addActionError("日期格式不正确：" + e.getMessage());
			}
		}
		
		if(StringUtils.isBlank(dateTimeFormat)){
			addActionError("日期时间格式不能为空。");
		}else{
			try {
				SimpleDateFormat f = new SimpleDateFormat(dateTimeFormat);
				f.format(new Date());
			} catch (Exception e) {
				addActionError("日期时间格式不正确：" + e.getMessage());
			}
		}
		
		if(StringUtils.isBlank(shortDateTimeFormat)){
			addActionError("精简日期时间格式不能为空。");
		}else{
			try {
				SimpleDateFormat f = new SimpleDateFormat(shortDateTimeFormat);
				f.format(new Date());
			} catch (Exception e) {
				addActionError("精简日期时间格式不正确：" + e.getMessage());
			}
		}
	}
	
	public String execute(){
		AppsGlobals.setSetupProperty(FORMAT_STYLE_DATE, dateFormat);
		AppsGlobals.setSetupProperty(FORMAT_STYLE_DATETIME, dateTimeFormat);
		AppsGlobals.setSetupProperty(FORMAT_STYLE_SHORTDATATIME, shortDateTimeFormat);
		
		return "next";
	}
}
