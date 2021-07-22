package org.opoo.apps;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Order;

/**
 * ��ѯ�������ϡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface QueryParameters extends java.io.Serializable{
	/**
	 * ��ѯ�������ϡ�
	 * 
	 * @return
	 */
	List<QueryParameter> getParameters();
	
	
	Map<String, ?> getRawParameters();

	//Serializable getId();
	
	//String getMethodName();
	
	/**
	 * ��ʼ��¼�����š�
	 */
	int getStart();
	/**
	 * ����¼����
	 * @return
	 */
	int getMaxResults();
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	Order getOrder();
	
	/**
	 * �����ֶΡ�
	 * @return
	 */
	String getSort();
	
	/**
	 * ������
	 * @return
	 */
	String getDir();
}
