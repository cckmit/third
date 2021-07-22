package org.opoo.apps.proxy;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class DynamicCollectionProxy<K,E> extends AbstractCollection<E> implements Serializable{

	private static final long serialVersionUID = 7659418019629335064L;
	
	private final Collection<K> col;
	private final GenericProxyFactory<K, E> proxyFactory;
	public DynamicCollectionProxy(Collection<K> col, GenericProxyFactory<K, E> proxyFactory) {
		this.col = col;
		this.proxyFactory = proxyFactory;
	}

	@Override
	public Iterator<E> iterator() {
		final Iterator<K> iterator = col.iterator();
		return new GenericIteratorProxy<E,K>(iterator, proxyFactory);
	}

	@Override
	public int size() {
		return col.size();
	}
}
