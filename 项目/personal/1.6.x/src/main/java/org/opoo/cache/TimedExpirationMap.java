package org.opoo.cache;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 通过 Map 实现的缓存对象，可设置过期时间。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <K>
 * @param <V>
 * @see java.util.concurrent.ConcurrentHashMap
 */
public class TimedExpirationMap<K, V> implements Cache<K, V> {
	private static class ExpirableValueWrapper<K, V> {
		final K key;
		final V value;
		final long timestamp = System.currentTimeMillis();
		volatile boolean ignore;

		ExpirableValueWrapper(K key, V value) {
			ignore = false;
			this.key = key;
			this.value = value;
		}
	}

	private final AtomicLong maxLifetime = new AtomicLong();
	private final AtomicLong lastChecked = new AtomicLong();
	private final AtomicBoolean expiring = new AtomicBoolean();
	private final long expirationPeriod;
	private final ConcurrentMap<K, ExpirableValueWrapper<K, V>> map = new ConcurrentHashMap<K, ExpirableValueWrapper<K, V>>();
	private final Queue<ExpirableValueWrapper<K, V>> expirationQueue = new ConcurrentLinkedQueue<ExpirableValueWrapper<K, V>>();
	private String name;

	public TimedExpirationMap(String name, long lifetime, long expirationPeriod) {
		this.name = name;
		maxLifetime.set(lifetime);
		this.expirationPeriod = expirationPeriod;
	}

	public int size() {
		expireEntries();
		return map.size();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean containsKey(Object key) {
		expireEntries();
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public V get(Object key) {
		expireEntries();
		ExpirableValueWrapper<K, V> wrapper = (ExpirableValueWrapper<K, V>) map.get(key);
		if (wrapper != null)
			return wrapper.value;
		else
			return null;
	}

	public V put(K key, V value) {
		expireEntries();
		ExpirableValueWrapper<K, V> wrapper = new ExpirableValueWrapper<K, V>(key, value);
		ExpirableValueWrapper<K, V> oldVal = map.put(key, wrapper);
		expirationQueue.offer(wrapper);
		return processWrapper(oldVal);
	}

	public V remove(Object key) {
		expireEntries();
		return processWrapper(map.remove(key));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxCacheSize() {
		return 0;
	}

	public void setMaxCacheSize(int i) {
	}

	public long getMaxLifetime() {
		return maxLifetime.get();
	}

	public void setMaxLifetime(long maxLifetime) {
		this.maxLifetime.set(maxLifetime);
	}

	public int getCacheSize() {
		return 0;
	}

	public long getCacheHits() {
		return 0L;
	}

	public long getCacheMisses() {
		return 0L;
	}

	private V processWrapper(ExpirableValueWrapper<K, V> wrapper) {
		if (wrapper != null) {
			wrapper.ignore = true;
			return wrapper.value;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map<? extends K, ? extends V> t) {
		for (Object obj : t.entrySet()) {
			Map.Entry<K, V> en = (Map.Entry<K,V>) obj;
			put(en.getKey(), en.getValue());
		}
	}

	public void clear() {
		expirationQueue.clear();
		map.clear();
	}

	public Set<K> keySet() {
		expireEntries();
		return Collections.unmodifiableSet(map.keySet());
	}

	public Collection<V> values() {
		expireEntries();
		final Collection<ExpirableValueWrapper<K, V>> values = map.values();
		final Iterator<ExpirableValueWrapper<K, V>> iterator = values.iterator();
		Collection<V> vs = new AbstractCollection<V>() {
			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					public boolean hasNext() {
						return iterator.hasNext();
					}

					public V next() {
						ExpirableValueWrapper<K, V> next = iterator.next();
						return next != null ? next.value : null;
					}

					public void remove() {
						iterator.remove();
					}
				};
			}

			@Override
			public int size() {
				return values.size();
			}
		};

		return Collections.unmodifiableCollection(vs);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		expireEntries();
		final Set<Entry<K, ExpirableValueWrapper<K, V>>> set = map.entrySet();
		final Iterator<Entry<K, ExpirableValueWrapper<K, V>>> iterator = set.iterator();
		AbstractSet<Entry<K, V>> set2 = new AbstractSet<Map.Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new Iterator<Entry<K, V>>() {
					public boolean hasNext() {
						return iterator.hasNext();
					}

					public Entry<K, V> next() {
						final Entry<K, ExpirableValueWrapper<K, V>> next = iterator.next();
						if(next == null){
							return null;
						}
						
						return new Entry<K, V>() {
							public K getKey() {
								return next.getKey();
							}

							public V getValue() {
								return next.getValue().value;
							}

							public V setValue(V value) {
								V v = next.getValue().value;
								ExpirableValueWrapper<K,V> newV = new ExpirableValueWrapper<K,V>(next.getKey(), value);
								next.setValue(newV);
								return v;
							}
						};
					}

					public void remove() {
						iterator.remove();
					}
				};
			}

			@Override
			public int size() {
				return set.size();
			}
		};

		return Collections.unmodifiableSet(set2);
	}

	private void expireEntries() {
		long now = System.currentTimeMillis();
		if (now - lastChecked.get() >= expirationPeriod && expiring.compareAndSet(false, true))
			try {
				lastChecked.set(now);
				ExpirableValueWrapper<K, V> value = null;
				do {
					if ((value = (ExpirableValueWrapper<K, V>) expirationQueue.peek()) == null
							|| now - value.timestamp < maxLifetime.get())
						break;
					expirationQueue.poll();
					if (!value.ignore)
						map.remove(value.key);
				} while (true);
			} finally {
				expiring.set(false);
			}
	}
}
