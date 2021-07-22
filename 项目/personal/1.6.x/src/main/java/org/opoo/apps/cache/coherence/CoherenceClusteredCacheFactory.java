package org.opoo.apps.cache.coherence;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsContext;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.cache.CacheStrategy;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;
import org.springframework.util.StopWatch;

import com.tangosol.net.AbstractInvocable;
import com.tangosol.net.Cluster;
import com.tangosol.net.Invocable;
import com.tangosol.net.InvocationService;
import com.tangosol.net.Member;
import com.tangosol.util.ConcurrentMap;
import com.tangosol.util.WrapperConcurrentMap;


/**
 * ¼¯Èº»º´æ²ßÂÔ¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class CoherenceClusteredCacheFactory implements CacheStrategy {
	private static final Log log = LogFactory.getLog(CoherenceClusteredCacheFactory.class);

	private static Map<String, Map<String, long[]>> cacheStats;
	private static Cluster cluster = null;
	private static InvocationService taskService;
	private static ConcurrentMap lockingMap = new WrapperConcurrentMap(new HashMap<Object,Object>());

	public boolean startCluster() {
		AppsContext ctx = Application.getContext();
		AppsLicenseManager licenseManager = ctx.getLicenseManager();
		int allowedMembers = licenseManager.getAppsLicense().getNumClusterMembers();
		if (allowedMembers > 1) {
			cluster = com.tangosol.net.CacheFactory.ensureCluster();
			cluster.setContextClassLoader(CacheFactory.class.getClassLoader());
			int memberCount = cluster.getMemberSet().size();
			if (memberCount > allowedMembers) {
				com.tangosol.net.CacheFactory.shutdown();
				cluster = null;
				log.error("Error joining clustered cache: your "
                        + "license only allows for " + allowedMembers +
                		" cluster nodes. Using local cache instead.");
				return false;
			}
		// Only 1 cluster member is allowed, so use local cache.
		}else{
			log.error("Error joining clustered cache: your license only allows for " 
					+ allowedMembers + " cluster nodes. Using local cache instead.");
			return false;
		}

		// log.error((new
		// StringBuilder()).append("Error joining clustered cache: your license only allows for ").append(allowedMembers).append(" cluster nodes. Using local cache instead.").toString());
		// return false;
		try {
			lockingMap = com.tangosol.net.CacheFactory.getCache("$lockingMap");
			taskService = //com.tangosol.net.CacheFactory.getInvocationService("Apps Cluster Service");
				(InvocationService) com.tangosol.net.CacheFactory.getService("Apps Cluster Service");
			return true;
		} catch (Exception e) {
			log.error("Unable to start clustering - continuing in local mode", e);
		}
		return false;
	}

	public void stopCluster() {
		cacheStats = null;
		taskService = null;
		com.tangosol.net.CacheFactory.shutdown();
		cluster = null;
		lockingMap = new WrapperConcurrentMap(new HashMap<Object,Object>());
	}

	@SuppressWarnings("unchecked")
	public Cache<?,?> createCache(String name) {
		return new ClusteredCache(name);
	}

	@SuppressWarnings("unchecked")
	public void destroyCache(Cache cache) {
		if (cache instanceof CacheWrapper)
			cache = ((CacheWrapper) cache).getWrappedCache();
		ClusteredCache clustered = (ClusteredCache) cache;
		clustered.destroy();
	}

	public boolean isSeniorClusterMember() {
		return taskService.getInfo().getOldestMember().getUid().equals(cluster.getLocalMember().getUid());
	}

	public void doClusterTask(AbstractClusterTask task) {
		Member current = taskService.getCluster().getLocalMember();
		Set<?> setMembers = taskService.getInfo().getServiceMembers();
		setMembers.remove(current);
		String className = task.getClass().getName();
		String taskName = "task" + className.substring(className.lastIndexOf("."));
		StopWatch sw = new StopWatch();
		// Profiler.begin(taskName);
		sw.start(taskName);
		taskService.execute(buildInvocable(task), setMembers, null);
		// Profiler.end(taskName);
		sw.stop();
		if(log.isDebugEnabled()){
			log.debug(sw.prettyPrint());
		}
	}

	public Collection<? extends Object> doSynchronousClusterTask(AbstractClusterTask task, boolean includeLocalMember) {
		Member current = taskService.getCluster().getLocalMember();
		Set<?> setMembers = taskService.getInfo().getServiceMembers();
		if (!includeLocalMember){
			setMembers.remove(current);
		}
		Map<?,?> map = taskService.query(buildInvocable(task), setMembers);
		return (map == null ? Collections.emptyList() : map.values());
	}

	@SuppressWarnings("unchecked")
	public void updateCacheStats(Map<String, Cache<?,?>> caches) {
		if (caches.size() > 0 && cluster != null) {
			if (cacheStats == null)
				cacheStats = com.tangosol.net.CacheFactory.getCache("opt-$cacheStats");
			String uid = cluster.getLocalMember().getUid().toString();
			Map<String, long[]> stats = new HashMap<String, long[]>();
			 for (String cacheName : caches.keySet()) {
                Cache<?,?> cache = caches.get(cacheName);
                // The following information is published:
                // current size, max size, num elements, cache
                // hits, cache misses.
                long [] info = new long[5];
                info[0] = cache.getCacheSize();
                info[1] = cache.getMaxCacheSize();
                info[2] = cache.size();
                info[3] = cache.getCacheHits();
                info[4] = cache.getCacheMisses();
                stats.put(cacheName, info);
            }
			cacheStats.put(uid, stats);
		}
	}

	public void lockKey(Object key, long timeout) {
		lockingMap.lock(key, timeout);
	}

	public void unlockKey(Object key) {
		lockingMap.unlock(key);
	}

	private static Invocable buildInvocable(final AbstractClusterTask task) {
		return new AbstractInvocable() {
			private static final long serialVersionUID = 8237679398835168695L;
			public void run() {
				task.run();
			}

			public Object getResult() {
				return task.getResult();
			}
		};
	}

}
