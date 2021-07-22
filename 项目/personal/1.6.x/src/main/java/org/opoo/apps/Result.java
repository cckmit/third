package org.opoo.apps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * �������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Result extends Message {
	/**
	 * ������ϡ� ����ʵ���������������Ǳ��ύ��Ӧ��û�м��ϡ�
	 * 
	 * @return
	 */
	List<?> getRows();

	/**
	 * ��Ϣ��
	 * 
	 * @return
	 */
	String getMessage();

	/**
	 * �����Ƿ�ɹ���
	 * 
	 * @return
	 */
	boolean isSuccess();

	/**
	 * ���󼯺ϡ� ����Ӧ�ֶ����ƣ�ֵ��Ӧ������Ϣ��
	 * 
	 * @return
	 */
	Map<String, String> getErrors();

	/**
	 * ��������
	 * 
	 * @return
	 */
	Serializable getData();

	/**
	 * ҳ��С����������ơ�
	 * @return
	 */
	int getLimit();

	/**
	 * ���������
	 * @return
	 */
	int getTotal();

	/**
	 * �������ʼ��š�
	 * 
	 * @return
	 */
	int getStart();

	/**
	 * ������
	 * @return
	 */
	String getDir();

	/**
	 * �����ֶΡ����ԡ�
	 * @return
	 */
	String getSort();

	/**
	 * ���������������
	 * @param data
	 */
	void setData(Serializable data);

	/**
	 * �������
	 * @param rows
	 */
	void setRows(List<?> rows);
}
