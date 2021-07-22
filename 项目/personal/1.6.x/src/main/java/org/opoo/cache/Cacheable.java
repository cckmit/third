package org.opoo.cache;

import java.io.Serializable;


/**
 * 可缓存的。
 * 实现这个接口的类表示可被缓存。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Cacheable extends Serializable {
	/**
	 * 获取对象缓存时的大小。
	 * 这个值计算而得，不是精确的。<p>
	 * 
	 * Returns the approximate size of the Object in bytes. The size should be
     * considered to be a best estimate of how much memory the Object occupies
     * and may be based on empirical trials or dynamic calculations.<p>
	 * 
	 * @return the size of the Object in bytes.
	 */
	int getCachedSize();
}
