package org.opoo.apps.id.sequence;

/**
 * 可以分块产生 id 的接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Blockable {
	/**
	 * 获取下一个可用的块id的最大值。
	 * 
	 * @param blockSize
	 * @return
	 */
	long getNext(int blockSize);
}
