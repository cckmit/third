package org.opoo.ndao.domain;

import org.opoo.ndao.Domain;

/**
 * 实体类的基类。
 * 所有实体（持久类）的超类，ID根据泛型可以取不同的数据类型。
 * 通常ID的类型是Long或者String。
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
