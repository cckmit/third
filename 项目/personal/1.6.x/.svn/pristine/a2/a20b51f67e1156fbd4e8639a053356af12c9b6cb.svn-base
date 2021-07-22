package org.opoo.apps.cache;

import java.util.Collection;
import java.util.Map;

import org.opoo.cache.Cache;


/**
 * 缓存策略。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface CacheStrategy {
	/**
	 * 启用集群。
	 * @return
	 */
	boolean startCluster();

	/**
	 * 停止集群。
	 */
	void stopCluster();

	/**
	 * 创建缓存。
	 * 
	 * @param <K>
	 * @param <V>
	 * @param sname
	 * @return
	 */
	<K, V> Cache<K, V> createCache(String sname);

	/**
	 * 销毁缓存。
	 * 
	 * @param <K>
	 * @param <V>
	 * @param cache
	 */
	<K, V> void destroyCache(Cache<K, V> cache);

	/**
	 * 是否是高级集群成员。
	 * 
	 * @return
	 */
	boolean isSeniorClusterMember();

	/**
	 * 调用集群任务。
	 * 
	 * @param abstractclustertask
	 */
	void doClusterTask(AbstractClusterTask abstractclustertask);

	/**
	 * 调用集群同步任务。
	 * 
	 * @param abstractclustertask
	 * @param flag
	 * @return
	 */
	Collection<?> doSynchronousClusterTask(AbstractClusterTask abstractclustertask, boolean flag);

	/**
	 * 更新缓存统计信息。
	 * 
	 * @param map
	 */
	void updateCacheStats(Map<String,Cache<?,?>> map);

	/**
	 * 锁定指定对象。
	 * 
	 * @param obj
	 * @param l 超时时间
	 */
	void lockKey(Object obj, long l);

	/**
	 * 接触锁定。
	 * 
	 * @param obj
	 */
	void unlockKey(Object obj);
}
