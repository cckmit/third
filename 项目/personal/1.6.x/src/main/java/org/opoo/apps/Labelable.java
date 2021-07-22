package org.opoo.apps;

import java.io.Serializable;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Labelable {
	/**
	 * ����label��
	 * @return
	 */
	String getLabel();
	/**
	 * �������ݡ�
	 * @return
	 */
	Serializable getData();
}
