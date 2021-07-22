package org.opoo.ndao.support;

import java.util.List;
import java.util.Map;


/**
 * �򵥲�ѯ֧���ࡣ
 * 
 * ֻҪ������һ�����͵ķ���֧�֡�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SimpleQuerySupport extends QuerySupport {

	/**
	 * ִ�и��¡�
	 * @param queryString ��ѯ��䣨SQL,HQL�ȣ�
	 * @param args ��������
	 * @return �����µĶ�������
	 */
	int executeUpdate(String queryString, Object... args);
	/**
	 * ָ�����¡�
	 * @param queryString ��ѯ��䣨SQL,HQL�ȣ�
	 * @param args ������������
	 * @return �����µĶ�������
	 */
	int executeUpdate(String queryString, Map<String, Object> args);
	
	/**
	 * ִ�в�ѯ��
	 * @param queryString ��ѯ��䣨SQL,HQL�ȣ�
	 * @param args ��������
	 * @return ��ѯ�������
	 */
	<T> List<T> find(String queryString, Object... args);
	
	/**
	 * ִ�в�ѯ��
	 * @param queryString ��ѯ��䣨SQL,HQL�ȣ�
	 * @param args �����������
	 * @return ��ѯ�������
	 */
	<T> List<T> find(String queryString, Map<String, Object> args);
}
