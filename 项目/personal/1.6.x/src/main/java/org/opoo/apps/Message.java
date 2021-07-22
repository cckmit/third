/**
 * 
 */
package org.opoo.apps;

import java.util.Map;

/**
 * ��Ϣ��ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public interface Message extends java.io.Serializable {
	
	/**
	 * ���һ���ֶδ�����Ϣ��
	 * 
	 * @param fieldName
	 * @param msg
	 */
	void addError(String fieldName, String msg);

	/**
	 * ���һ���ֶδ�����Ϣ��
	 * 
	 * @param errors
	 */
	void setErrors(Map<String, String> errors);
	
	/**
	 * ��ȡȫ���ֶδ�����Ϣ��
	 * 
	 * @return
	 */
	Map<String, String> getErrors();

	/**
	 * ���ñ�ʶ�Ƿ�ɹ���
	 * @param success
	 */
	void setSuccess(boolean success);

	/**
	 * ��ȡ�ɹ���ʶ��
	 * @return
	 */
	boolean isSuccess();

	/**
	 * ����ȫ�ִ�����Ϣ��
	 * @param msg
	 */
	void setMessage(String msg);

	/**
	 * ��ȡȫ�ִ�����Ϣ��
	 * @return
	 */
	String getMessage();
}
