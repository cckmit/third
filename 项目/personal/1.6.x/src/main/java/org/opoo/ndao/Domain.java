package org.opoo.ndao;

/**
 * �����
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public interface Domain<K extends java.io.Serializable> extends java.io.Serializable {
	/**
	 * ��ȡ������
	 * @return
	 */
	K getId();
	
	/**
	 * ����������
	 * @param k
	 */
	void setId(K k);
}
