package org.opoo.apps.cache.coherence;

import java.util.Collection;
import java.util.Map;

import com.tangosol.net.cache.CacheStore;


/**
 * �����Ļ���洢����
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class DummyCacheStore implements CacheStore {

	public DummyCacheStore() {
	}

	public void store(Object obj, Object obj1) {
	}

	
	public void storeAll(Map map) {
	}

	public void erase(Object obj) {
	}

	public void eraseAll(Collection c) {
	}

	public Object load(Object object) {
		return null;
	}

	public Map loadAll(Collection c) {
		return null;
	}
}