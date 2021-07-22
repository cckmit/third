/*
 * Dao.java
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.ndao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.support.Aggregation;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;


/**
 * �°��DAO����
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.5.2
 */
public interface Dao<T extends Domain<K>, K extends Serializable> extends DataAccessor<T, K> {
    /**
     * �������
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T save(T entity);
    /**
     * ���¶���
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T update(T entity);
    /**
     * ������߸��¶��󣬸�������ID�����жϣ�������£����򱣴档
     * @param entity T
     * @return T
     * @throws DataAccessException
     */
    T saveOrUpdate(T entity);
    /**
     * ɾ������
     * @param entity T
     * @return int
     * @throws DataAccessException
     */
    int delete(T entity);
    /**
     * ����IDɾ������
     * @param id K
     * @return int
     * @throws DataAccessException
     */
    int remove(K id);
    /**
     * ����һ��IDɾ���������
     * @param ids K[]
     * @return int
     * @throws DataAccessException
     */
    int remove(K[] ids);
    /**
     * ��������ɾ������
     *
     * @param criterion Criterion
     * @return int
     * @throws DataAccessException
     */
    int remove(Criterion criterion);
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
