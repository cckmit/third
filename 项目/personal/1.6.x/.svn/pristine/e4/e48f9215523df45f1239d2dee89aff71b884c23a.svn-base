/*
 * $Id: GenericListProxy.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a list that protects all of its members by using the proxyFactory to
 * create proxies for each of the members.
 */
public class GenericListProxy<K,E> extends AbstractList<E> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -614718090134740576L;
	private List<E> proxiedList;

    /**
     * Constructor for serialization use only.
     */
    public GenericListProxy() {
    }

    public GenericListProxy(List<K> list, GenericProxyFactory<K, E> proxyFactory) {
        proxiedList = new ArrayList<E>();

        for (K aList : list) {
            E o = proxyFactory.createProxy(aList);

            if (o != null) {
                proxiedList.add(o);
            }
        }
    }

    public E get(int i) {
        return proxiedList.get(i);
    }

    public int size() {
        return proxiedList.size();
    }
}
