/*
 * $Id: GenericMapProxy.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 */
public class GenericMapProxy<K,V> extends AbstractMap<K,V> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1552858933536564307L;
	private Map<K,V> proxiedMap;

    /**
     * Constructor for serialization use only.
     */
    public GenericMapProxy() {
    }

    public GenericMapProxy(Map<K,V> map, GenericProxyFactory<V,V> proxyFactory) {

        proxiedMap = new HashMap<K,V>();

        for (K key : map.keySet()) {
            V o = proxyFactory.createProxy(map.get(key));

            if (o != null) {
                proxiedMap.put(key, o);
            }
        }

        proxiedMap = Collections.unmodifiableMap(proxiedMap);
    }

    public Set<Map.Entry<K,V>> entrySet() {
        return proxiedMap.entrySet();
    }

    public int size() {
        return proxiedMap.size();
    }
}
