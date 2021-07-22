package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

/**
 * ���ݷ����ߡ�
 * 
 * �����˶��ض�����ġ���ȡ��������
 * 
 * @author Alex Lin(alex@opoo.org)
 * @version 1.5.2
 * @since 1.5.2
 *
 * @param <T>
 * @param <K>
 */
public interface DataAccessor<T extends Domain<K>, K extends Serializable> {
    /**
     * ����ID��ѯһ������
     *
     * @param id K
     * @return T
     * @throws DataAccessException
     */
    T get(K id);

    /**
     * ��ѯ���ж���
     * @return List
     * @throws DataAccessException
     */
    List<T> find();
    /**
     * ���ݹ���������ѯ���ݼ��ϡ�
     * @param resultFilter ���������
     * @return
     */
    List<T> find(ResultFilter resultFilter);
    
    /**
     * ��ѯID���ϡ�
     * @param resultFilter ���������������Ϊ<code>null</code>
     * @return id����
     */
    List<K> findIds(ResultFilter resultFilter);
    
    /**
     * ��ȡ�ܶ�������
     * @return
     */
    int getCount();
    
    /**
     * ���ݹ���������ѯ����������
     * @param resultFilter ���������
     * @return
     */
    int getCount(ResultFilter resultFilter);
    
    /**
     * ���ݹ���������ѯ��ҳ�Ķ��󼯺ϡ�
     * 
     * @param resultFilter �������������������ҳ������
     * @return
     */
    PageableList<T> findPageableList(ResultFilter resultFilter);
    
    /**
     * ���ݲ�ѯ������ȡ��������
     * 
     * �����ѯ�����Ψһ���������쳣��
     * @param criterion
     * @return
     */
    T get(Criterion criterion);
    
    
    /**
     * �����ѯ��
     * ��ѯ������ݽṹΪMap��
     * @param resultFilter
     * @param groupInfo
     * @return
     */
    List<Map<String, Object>> find(ResultFilter resultFilter, Aggregation aggregation);
}
