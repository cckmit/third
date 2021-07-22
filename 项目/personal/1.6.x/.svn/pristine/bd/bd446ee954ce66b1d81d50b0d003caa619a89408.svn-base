package org.opoo.apps.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.cache.ehcache.EhcacheLocalCacheFactory;
import org.opoo.apps.event.v1.BlockingEventDispatcher;
import org.opoo.apps.event.v1.Event;
import org.opoo.apps.event.v1.EventDispatcher;
import org.opoo.apps.event.v1.EventListener;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;


/**
 * 缓存工厂。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class CacheFactory {

	/**
	 * 缓存集群监听器。
	 *
	 */
	public static interface ClusteringListener {
		void clusteringStarted();
		void clusteringStopped();
	}
	
	/**
	 * 缓存事件类型。
	 *
	 */
	public static enum EventType {
		DESTROYED, INITIALIZED, CLUSTER_STARTED;
	};

	private static final Log log = LogFactory.getLog(CacheFactory.class);
	public static final String CLUSTER_PROPERTY_NAME = "cache.clustering.enabled";
	public static final String LOCAL_CACHE_PROPERTY_NAME = "cache.clustering.local.class";
	public static final String CLUSTERED_CACHE_PROPERTY_NAME = "cache.clustering.clustered.class";
	private static boolean clusteringEnabled = false;
	private static Map<String, Cache<?, ?>> caches = new ConcurrentHashMap<String, Cache<?, ?>>();
	private static List<ClusteringListener> listeners = new CopyOnWriteArrayList<ClusteringListener>();
	private static CacheStrategy cacheStrategy;
	private static EventDispatcher dispatcher = new BlockingEventDispatcher();

	private static CacheStrategy localCacheFactory;
	private static CacheStrategy clusteredCacheFactory;
	
	public CacheFactory() {
	}

	public static synchronized Cache<?, ?>[] getAllCaches() {
		List<Cache<?, ?>> values = new ArrayList<Cache<?, ?>>(caches.values());
		return values.toArray(new Cache[values.size()]);
	}

	@SuppressWarnings("unchecked")
	public static synchronized <T extends Cache> T createCache(String name) {
		Cache<?, ?> cache = caches.get(name);
		if (cache != null) {
			return (T) cache;
		} else {
			cache = cacheStrategy.createCache(name);
			return (T) wrapCache(cache, name);
		}
	}

	public static void destroyCache(String name) {
		Cache<?, ?> cache = caches.remove(name);
		if (cache != null) {
			cacheStrategy.destroyCache(cache);
		}
	}

	public static void lockKey(Object key, long timeout) {
		cacheStrategy.lockKey(key, timeout);
	}

	public static void unlockKey(Object key) {
		cacheStrategy.unlockKey(key);
	}

	@SuppressWarnings("unchecked")
	private static Cache<?, ?> wrapCache(Cache<?, ?> cache, String name) {
		cache = new CacheWrapper(cache);
		cache.setName(name);
		caches.put(name, cache);
		return cache;
	}

	public static boolean isClusteringEnabled() {
		return clusteringEnabled;
	}

	public static boolean isClusteringConfigured() {
		return AppsGlobals.getSetupProperty(CLUSTER_PROPERTY_NAME, false);
		//return Boolean.valueOf(AppsGlobals.getSetupProperty("cache.clustering.enabled")).booleanValue();
	}

	public static String getUniqueClusterId() {
		if (clusteringEnabled){
			if(cacheStrategy.getClass().getName().contains("coherence")){
				return String.valueOf(com.tangosol.net.CacheFactory.getCluster().getLocalMember().getId());
			}else{
				return UUID.randomUUID().toString();
			}
		}else{
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	public static synchronized void setClusteringEnabled(boolean enabled) throws Exception {
		if (enabled == clusteringEnabled) {
			return;
		}
		AppsGlobals.setSetupProperty(CLUSTER_PROPERTY_NAME, String.valueOf(enabled));
		// 由集群切换到非集群
		if (!enabled) {
			clusteringEnabled = false;
			CacheStrategy clusteredFactory = cacheStrategy;
			cacheStrategy = localCacheFactory;
			// (CacheStrategy)applicationContext.getBean("localCacheFactory");
			CacheWrapper wrapper;
			for (String cacheName : caches.keySet()) {
				wrapper = (CacheWrapper) caches.get(cacheName);
				wrapper.setWrappedCache(cacheStrategy.createCache(cacheName));
			}

			clusteredFactory.stopCluster();
			fireClusteringStopped();
		} else {
			//DatabaseProperties.getInstance().init();
			startClustering();
		}
	}

	public static synchronized void clearCaches() {
		for (Cache<?, ?> cache : caches.values()) {
			cache.clear();
		}
	}

	public static synchronized void destroyCaches() {
		for (String key : caches.keySet()) {
			destroyCache(key);
		}
	}

	public static boolean isSeniorClusterMember() {
		if (!isClusteringEnabled()) {
			return true;
		}
		return cacheStrategy.isSeniorClusterMember();
	}

	public static void doClusterTask(AbstractClusterTask task) {
		log.debug("执行集群任务：" + clusteringEnabled);
		if (clusteringEnabled) {
			synchronized (CacheFactory.class) {
				if (clusteringEnabled) {
					cacheStrategy.doClusterTask(task);
				}
			}
		}
	}

	public static Collection<?> doSynchronousClusterTask(AbstractClusterTask task, boolean includeLocalMember) {
		if (!clusteringEnabled)
			return Collections.emptyList();
		return cacheStrategy.doSynchronousClusterTask(task, includeLocalMember);
	}

	public static synchronized void shutdownClusteringService() {
		log.debug("Shutting down clustered cache service.");
		cacheStrategy.stopCluster();
	}

	public static void addClusteringListener(ClusteringListener listener) {
		listeners.add(listener);
	}

	public static void removeClusteringListener(ClusteringListener listener) {
		listeners.remove(listener);
	}

	private static void fireClusteringStarted() {
		for (ClusteringListener listener : listeners) {
			listener.clusteringStarted();
		}
	}

	private static void fireClusteringStopped() {
		for (ClusteringListener listener : listeners) {
			listener.clusteringStopped();
		}
	}

	public static void saveCacheSettings() {
		for (Cache<?, ?> toSave : caches.values()) {
			setMaxSizeProperty(toSave.getName(), toSave.getMaxCacheSize());
			setMaxLifetimeProperty(toSave.getName(), toSave.getMaxLifetime());
		}
	}

	public static void setMaxSizeProperty(String cacheName, int size) {
		cacheName = cacheName.replaceAll(" ", "");
		String propName = "cache." + cacheName + ".size";
		AppsGlobals.setSetupProperty(propName, Integer.toString(size));
	}

	public static void setMaxLifetimeProperty(String cacheName, long lifetime) {
		cacheName = cacheName.replaceAll(" ", "");
		String propName = "cache." + cacheName + ".maxLifetime";
		AppsGlobals.setSetupProperty(propName, Long.toString(lifetime));
	}

	public static int getMaxSizeFromProperty(String cacheName, int defaultSize) {
		String propName = "cache." + cacheName.replaceAll(" ", "") + ".size";
		String sizeProp = AppsGlobals.getSetupProperty(propName);
		if (sizeProp != null) {
			try {
				return Integer.parseInt(sizeProp);
			} catch (NumberFormatException nfe) {
				log.warn("Unable to parse " + propName + " using default value of " + defaultSize);
			}
			return defaultSize;
		} else {
			return defaultSize;
		}
	}

	public static long getMaxLifetimeFromProperty(String cacheName, long defaultLifetime) {
		String propName = "cache." + cacheName.replaceAll(" ", "") + ".maxLifetime";
		String lifetimeProp = AppsGlobals.getSetupProperty(propName);
		if (lifetimeProp != null) {
			try {
				return Long.parseLong(lifetimeProp);
			} catch (NumberFormatException nfe) {
				log.warn("Unable to parse " + propName + " using default value of " + defaultLifetime);
			}
			return defaultLifetime;
		} else {
			return defaultLifetime;
		}
	}

	public static synchronized void initialize() {
		cacheStrategy = localCacheFactory;
		if(cacheStrategy == null){
			cacheStrategy = new EhcacheLocalCacheFactory();
		}
//		 if(null == cacheFactoryStrategy)
//	            cacheFactoryStrategy = new CoherenceLocalCacheFactory();
//	        com.tangosol.net.CacheFactory.setConfigurableCacheFactory(configurableFactory);
	}

	public static synchronized void destroy() {
		dispatcher.dispatchEvent(new Event<EventType, Object>(EventType.DESTROYED, new Object()));
	}

	public static synchronized void startup() {
		if (clusteringEnabled) {
			return;
		}

		String enabled = AppsGlobals.getSetupProperty(CLUSTER_PROPERTY_NAME);
		if (Boolean.valueOf(enabled).booleanValue()) {
			startClustering();
		}

		Thread t = new Thread("Cache Stats") {
			private boolean destroyed = false;

			private EventListener<Event<EventType, Object>> listener = new EventListener<Event<EventType, Object>>() {
				public void handle(Event<EventType, Object> event) {
					destroyed = true;
					CacheFactory.dispatcher.removeEventListener(CacheFactory.EventType.DESTROYED, listener);
					interrupt();
				}
			};

			public void run() {
				CacheFactory.dispatcher.addEventListener(CacheFactory.EventType.DESTROYED, listener);

				while (!destroyed) {
					try {
						CacheFactory.cacheStrategy.updateCacheStats(CacheFactory.caches);
					} catch (Exception e) {
						log.error(e);
					}
					try {
						sleep(10000L);
					} catch (InterruptedException ie) {
					}
				}
				log.debug("Cache stats thread terminated.");
			}
		};
		t.setDaemon(true);
		t.start();
	}

	@SuppressWarnings("unchecked")
	private static void startClustering() {
		clusteringEnabled = false;
		boolean clusterStarted = false;
		try {
			cacheStrategy = clusteredCacheFactory;
			clusterStarted = cacheStrategy.startCluster();
			if (clusterStarted) {
				CacheWrapper wrapper;
				for (String cacheName : caches.keySet()) {
					wrapper = (CacheWrapper) caches.get(cacheName);
					wrapper.setWrappedCache(cacheStrategy.createCache(cacheName));
				}
				
				
				clusteringEnabled = true;
				log.debug("集群启动：" + clusteringEnabled);
				fireClusteringStarted();
			}
		} catch (Exception e) {
			log.error("Unable to start clustering - continuing in local mode", e);
		}
		if (!clusterStarted){
			cacheStrategy = localCacheFactory;
		}
	}

	/**
	 * @param localCacheFactory the localCacheFactory to set
	 */
	public static void setLocalCacheFactory(CacheStrategy localCacheFactory) {
		CacheFactory.localCacheFactory = localCacheFactory;
	}

	/**
	 * @param clusteredCacheFactory the clusteredCacheFactory to set
	 */
	public static void setClusteredCacheFactory(CacheStrategy clusteredCacheFactory) {
		CacheFactory.clusteredCacheFactory = clusteredCacheFactory;
	}

}
