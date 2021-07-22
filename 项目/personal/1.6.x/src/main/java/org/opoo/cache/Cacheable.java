package org.opoo.cache;

import java.io.Serializable;


/**
 * �ɻ���ġ�
 * ʵ������ӿڵ����ʾ�ɱ����档
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Cacheable extends Serializable {
	/**
	 * ��ȡ���󻺴�ʱ�Ĵ�С��
	 * ���ֵ������ã����Ǿ�ȷ�ġ�<p>
	 * 
	 * Returns the approximate size of the Object in bytes. The size should be
     * considered to be a best estimate of how much memory the Object occupies
     * and may be based on empirical trials or dynamic calculations.<p>
	 * 
	 * @return the size of the Object in bytes.
	 */
	int getCachedSize();
}
