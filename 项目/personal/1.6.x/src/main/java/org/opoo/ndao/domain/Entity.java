package org.opoo.ndao.domain;

import org.opoo.ndao.Domain;

/**
 * ʵ����Ļ��ࡣ
 * ����ʵ�壨�־��ࣩ�ĳ��࣬ID���ݷ��Ϳ���ȡ��ͬ���������͡�
 * ͨ��ID��������Long����String��
 *
 * @param <K>
 */
@SuppressWarnings("serial")
public abstract class Entity<K extends java.io.Serializable> implements Domain<K> {

	private K id;
	
	public Entity(K id){
		setId(id);
	}
	
	public Entity(){
		
	}
	
	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}
}
