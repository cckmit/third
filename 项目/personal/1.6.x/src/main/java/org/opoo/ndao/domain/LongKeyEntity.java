package org.opoo.ndao.domain;

/**
 * 主对象是 Long 型的实体类基类。
 *
 */
public abstract class LongKeyEntity extends Entity<Long> implements
		LongKeyDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 29015960078986411L;

	public Long getKey() {
		return getId();
	}

	public void setKey(Long key) {
		setId(key);
	}

}
