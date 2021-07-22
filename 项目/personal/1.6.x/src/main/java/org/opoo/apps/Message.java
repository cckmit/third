/**
 * 
 */
package org.opoo.apps;

import java.util.Map;

/**
 * 消息类接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public interface Message extends java.io.Serializable {
	
	/**
	 * 添加一个字段错误信息。
	 * 
	 * @param fieldName
	 * @param msg
	 */
	void addError(String fieldName, String msg);

	/**
	 * 添加一组字段错误信息。
	 * 
	 * @param errors
	 */
	void setErrors(Map<String, String> errors);
	
	/**
	 * 获取全部字段错误信息。
	 * 
	 * @return
	 */
	Map<String, String> getErrors();

	/**
	 * 设置标识是否成功。
	 * @param success
	 */
	void setSuccess(boolean success);

	/**
	 * 获取成功标识。
	 * @return
	 */
	boolean isSuccess();

	/**
	 * 设置全局错误消息。
	 * @param msg
	 */
	void setMessage(String msg);

	/**
	 * 获取全局错误消息。
	 * @return
	 */
	String getMessage();
}
