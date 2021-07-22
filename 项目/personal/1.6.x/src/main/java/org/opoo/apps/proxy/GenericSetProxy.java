/*
 * $Id: GenericSetProxy.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GenericSetProxy<K,E> extends AbstractSet<E> {
	
	private final Set<E> proxiedSet;
	
	public GenericSetProxy(Set<K> set, GenericProxyFactory<K, E> factory) {
		proxiedSet = new HashSet<E>();
		for (K o : set) {
			E e = factory.createProxy(o);
			proxiedSet.add(e);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return proxiedSet.iterator(); 
	}

	@Override
	public int size() {
		return proxiedSet.size();
	}

}
