package org.opoo.apps.id.sequence;

import org.springframework.orm.hibernate3.HibernateTemplate;


/**
 * 使用Hibernate实现的快ID查询。
 * 
 *
 */
public class HibernateBlockable extends HibernateSysIdAccessor implements Blockable {

	HibernateBlockable(HibernateTemplate hibernateTemplate, String name){
		super(hibernateTemplate, name);
	}

	public long getNext(int blockSize) {
		return getCurrentId(blockSize) + blockSize -1;
	}
}
