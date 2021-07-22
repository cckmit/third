package org.opoo.ndao.hibernate3;

import java.io.Serializable;

import org.hibernate.type.Type;


/**
 * SQL查询使用的 Scalar。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Scalar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1625091525507238014L;
	private String columnAlias;
	private Type type;

	public Scalar(String columnAlias, Type type){
		this.columnAlias = columnAlias;
		this.type = type;
	}

	/**
	 * 列名。
	 * @return the columnAlias
	 */
	public String getColumnAlias() {
		return columnAlias;
	}

	/**
	 * 列类型。
	 * @return hibernate类型
	 */
	public Type getType() {
		return type;
	}

}
