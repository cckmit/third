/**
 * 
 */
package org.opoo.apps.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 使用WebUtils来封装HttpServletRequest。
 * 
 * @author Alex
 */
public class CustomParamHttpServletRequest extends HttpServletRequestWrapper {

	/**
	 * @param request
	 */
	public CustomParamHttpServletRequest(HttpServletRequest request) {
		super(request);
	}
	
	public int getIntParameter(String name){
		return WebUtils.getInt(this, name);
	}
	public int getIntParameter(String name, int defaultValue){
		return WebUtils.getInt(this, name, defaultValue);
	}
	
	public long getLongParameter(String name){
		return WebUtils.getLong(this, name);
	}
	
	public Date getDateParameter(String name){
		return WebUtils.getDate(this, name);
	}

}
