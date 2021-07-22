/*
 * $Id: GenericIteratorProxy.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

import java.util.Iterator;

/**
 * Creates a Iterator that protects all of its members by using the proxyFactory to
 * create proxies for each of the members.
 *
 */
public class GenericIteratorProxy<E, T> implements Iterator<E> {

    private final Iterator<T> iterator;
    private final GenericProxyFactory<T, E> factory;

    public GenericIteratorProxy(Iterator<T> iterator, GenericProxyFactory<T, E> factory) {
        this.iterator = iterator;
        this.factory = factory;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public E next() {
        return factory.createProxy(iterator.next());
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
