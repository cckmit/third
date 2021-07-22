package org.opoo.apps.id;

import java.io.Serializable;


/**
 * ID ��������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T> id������
 */
public interface IdGenerator<T extends Serializable> {
	/**
	 * ��ȡ��һ��ID
	 * @return
	 */
	T getNext();
	
	/**
	 * ��ȡ��ǰID
	 * @return
	 */
	T getCurrent();
}
