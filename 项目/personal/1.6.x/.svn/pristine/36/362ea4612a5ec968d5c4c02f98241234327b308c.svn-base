/*
 * $Id: GenericCollectionProxy.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Creates a Collection that protects all of its members by using the proxyFactory to
 * create proxies for each of the members.
 */
public class GenericCollectionProxy<K,V> extends AbstractCollection<V> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6351350234235327034L;
	private Collection<V> proxiedCollection;

    /**
     * Constructor for serialization use only.
     */
    public GenericCollectionProxy() {
    }

    public GenericCollectionProxy(Collection<K> col, GenericProxyFactory<K,V> proxyFactory) {

        proxiedCollection = new ArrayList<V>();

        for (K aCol : col) {

            V o = proxyFactory.createProxy(aCol);

            if (o != null) {
                proxiedCollection.add(o);
            }
        }
    }

    public int size() {
        return proxiedCollection.size();
    }

    public Iterator<V> iterator() {
        return proxiedCollection.iterator();
    }
}
