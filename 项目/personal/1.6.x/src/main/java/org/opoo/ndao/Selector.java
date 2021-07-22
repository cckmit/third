package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;


/**
 * ͨ�ò�ѯ����
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5.1
 * @version $Id: Selector.java 13 2010-11-26 05:04:02Z alex $
 */
public interface Selector {
	
	/**
	 * ����ID��ѯָ�����͵Ķ���
	 * 
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
	 * @param id �����id
	 * @return ���ҳ��Ķ������û�в�ѯ������null��
	 */
	<T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, K id);
	
	/**
	 * ��ѯָ�����͵Ķ��󼯺ϡ�
	 * 
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz);
	
	/**
	 * ����������ѯָ�����͵Ķ��󼯺ϡ�
	 * 
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
	 * @param filter ������
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<T> find(Class<T> clazz, ResultFilter filter);

	/**
	 * ����������ѯָ�����͵Ķ����ID���ϡ�
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
	 * @param idPropertyName id���Ե����ƣ�ͨ���ǡ�id��
	 * @param filter ������
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> List<K> findIds(Class<T> clazz, String idPropertyName, ResultFilter filter);
    
	/**
	 * ��ѯָ�����͵Ķ����������
	 * 
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
	 * @return
	 */
	<T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz);
    
    /**
     * ���ݹ���������ѯ����������
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
     * @param filter ������
     * @return
     */
	<T extends Domain<K>, K extends Serializable> int getCount(Class<T> clazz, ResultFilter filter);
    
    /**
     * ���ݹ���������ѯ��ҳ�Ķ��󼯺ϡ�
     * 
	 * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
     * @param filter �������������������ҳ������
     * @return
     */
	<T extends Domain<K>, K extends Serializable> PageableList<T> findPageableList(Class<T> clazz, ResultFilter filter);
    
    /**
     * ���ݲ�ѯ������ȡ����ָ�����͵Ķ���
     * 
     * �����ѯ�����Ψһ���������쳣��
     * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
     * @param criterion ��ѯ����
     * @return
     */
	<T extends Domain<K>, K extends Serializable> T get(Class<T> clazz, Criterion criterion);
    
    
    /**
     * �����ѯ����ѯ������ݽṹΪMap��
     * 
     * @param <T> ��������
	 * @param <K> �������������
	 * @param clazz ������
     * @param filter ��������
     * @param aggregation ������Ϣ
     * @return
     */
	<T extends Domain<K>, K extends Serializable> List<Map<String, Object>> find(Class<T> clazz, ResultFilter filter, Aggregation aggregation);
}
