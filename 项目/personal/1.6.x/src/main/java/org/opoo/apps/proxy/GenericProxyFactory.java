/*
 * $Id: GenericProxyFactory.java 36 2010-12-22 03:00:59Z alex $
 *
 * Copyright (C) 1999-2009 Jive Software. All rights reserved.
 *
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package org.opoo.apps.proxy;

public interface GenericProxyFactory<K, V> {

    V createProxy(K object);
}
