package org.opoo.ndao.domain;

import org.opoo.ndao.Domain;

/**
 * 主键是 String 类型的域对象接口。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface StringKeyDomain extends Domain<String> {
	void setKey(String key);
	String getKey();
}
