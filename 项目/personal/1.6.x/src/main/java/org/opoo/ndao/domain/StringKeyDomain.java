package org.opoo.ndao.domain;

import org.opoo.ndao.Domain;

/**
 * ������ String ���͵������ӿڡ�
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface StringKeyDomain extends Domain<String> {
	void setKey(String key);
	String getKey();
}
