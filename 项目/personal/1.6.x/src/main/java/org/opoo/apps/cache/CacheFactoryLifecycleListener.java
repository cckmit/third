package org.opoo.apps.cache;

import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.apps.lifecycle.event.ApplicationStateChangeEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 缓存管理器、生命周期监听器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class CacheFactoryLifecycleListener implements EventListener<ApplicationStateChangeEvent>, InitializingBean, DisposableBean {

	/**
	 * 
	 */
	public void handle(ApplicationStateChangeEvent event) {
		ApplicationState state = event.getNewState();
		if(state == ApplicationState.RUNNING){
			CacheFactory.startup();
		}else if(state == ApplicationState.SHUTDOWN){
			CacheFactory.shutdownClusteringService();
		}
	}

	/**
	 * 
	 */
	public void afterPropertiesSet() throws Exception {
		CacheFactory.initialize();
	}

	/**
	 * 
	 */
	public void destroy() throws Exception {
		CacheFactory.destroy();
	}

	/**
	 * @param localCacheFactory the localCacheFactory to set
	 */
	public void setLocalCacheFactory(CacheStrategy localCacheFactory) {
		CacheFactory.setLocalCacheFactory(localCacheFactory);
	}

	/**
	 * @param clusteredCacheFactory the clusteredCacheFactory to set
	 */
	public void setClusteredCacheFactory(CacheStrategy clusteredCacheFactory) {
		CacheFactory.setClusteredCacheFactory(clusteredCacheFactory);
	}
}
