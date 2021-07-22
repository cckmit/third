package org.opoo.ndao.domain;

import org.opoo.ndao.Domain;


/**
 * 主键是 Long 型的域对象接口。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface LongKeyDomain extends Domain<Long> {
	void setKey(Long key);
	Long getKey();
}
