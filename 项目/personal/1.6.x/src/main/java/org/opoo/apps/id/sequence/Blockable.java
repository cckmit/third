package org.opoo.apps.id.sequence;

/**
 * ���Էֿ���� id �Ľӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Blockable {
	/**
	 * ��ȡ��һ�����õĿ�id�����ֵ��
	 * 
	 * @param blockSize
	 * @return
	 */
	long getNext(int blockSize);
}
