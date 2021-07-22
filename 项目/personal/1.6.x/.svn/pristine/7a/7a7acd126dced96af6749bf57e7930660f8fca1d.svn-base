package org.opoo.apps.cache.coherence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.cache.Cache;

import com.tangosol.net.cache.CacheLoader;
import com.tangosol.net.cache.LocalCache;

/**
 * ±¾µØ»º´æ¡£
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class CoherenceCache extends LocalCache implements Cache {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8937238248298182467L;

	private static final Log log = LogFactory.getLog(CoherenceCache.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();

	private static final String FLUSH_DELAY_PROP = "cache.local.flushDelay";

	private String name = "";

	public CoherenceCache() {
		initCalculator();
	}

	public CoherenceCache(int maxSize) {
		super(maxSize);
		initCalculator();
	}

	public CoherenceCache(int maxSize, int maxLifetime) {
		super(maxSize, maxLifetime);
		initCalculator();
	}

	public CoherenceCache(int maxSize, int maxLifetime, CacheLoader loader) {
		super(maxSize, maxLifetime, loader);
		initCalculator();
	}

	public CoherenceCache(String name, int maxSize, long maxLifetime) {
		super(maxSize > 0 ? maxSize : Integer.MAX_VALUE, maxLifetime >= 0L ? (int) maxLifetime : 0);
		init(maxSize, name);
		initCalculator();
	}

	public CoherenceCache(String name, int maxSize, long maxLifetime, CacheLoader cacheLoader) {
		super(maxSize > 0 ? maxSize : Integer.MAX_VALUE, maxLifetime >= 0L ? (int) maxLifetime : 0, cacheLoader);
		init(maxSize, name);
		initCalculator();
	}

	@SuppressWarnings("deprecation")
	private void init(int maxSize, String name) {
		if (maxSize > 0) {
            setLowUnits((int)(maxSize*.9));
        }
		
		String delayProp = AppsGlobals.getSetupProperty(FLUSH_DELAY_PROP);
		if (delayProp != null)
			try {
				long delay = Long.parseLong(delayProp);
				if (delay >= 0L){
					setFlushDelay((int) delay);
				}
			} catch (NumberFormatException nfe) {
				log.warn("Unable to parse " + FLUSH_DELAY_PROP + " using default value of " + delayProp);
			}
		this.name = name;
	}

	@SuppressWarnings("deprecation")
	private void initCalculator() {
		setUnitCalculator(new CacheUnitCalculator(this));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("deprecation")
	public int getMaxCacheSize() {
		return getHighUnits();
	}

	@SuppressWarnings("deprecation")
	public void setMaxCacheSize(int maxSize) {
		setHighUnits(maxSize > 0 ? maxSize : Integer.MAX_VALUE);
		if (maxSize > 0){
			setLowUnits((int) (maxSize * 0.9));
		}
	}

	@SuppressWarnings("deprecation")
	public long getMaxLifetime() {
		return (long) getExpiryDelay();
	}

	@SuppressWarnings("deprecation")
	public void setMaxLifetime(long maxLifetime) {
		setExpiryDelay(maxLifetime >= 0L ? (int) maxLifetime : 0);
	}

	@SuppressWarnings("deprecation")
	public int getCacheSize() {
		return getUnits();
	}

	/* (non-Javadoc)
	 * @see com.tangosol.net.cache.LocalCache#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		if(IS_DEBUG_ENABLED){
			log.debug(name + " Cache hit: ; key: " + key);
		}
		return super.get(key);
	}

	/* (non-Javadoc)
	 * @see com.tangosol.net.cache.OldCache#put(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Object put(Object key, Object value) {
		if(IS_DEBUG_ENABLED){
			log.debug(getName() + " Cache put: " + key);
		}
		return super.put(key, value);
	}

	
}
