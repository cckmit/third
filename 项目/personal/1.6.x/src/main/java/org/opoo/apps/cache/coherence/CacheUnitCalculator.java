package org.opoo.apps.cache.coherence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheSizeCalculator;
import org.opoo.cache.CacheSizes;
import org.opoo.cache.Cacheable;

import com.tangosol.net.cache.OldCache.UnitCalculator;
import com.tangosol.util.Binary;
import com.tangosol.util.ExternalizableHelper;

/**
 * ª∫¥Ê¥Û–°º∆À„∆˜°£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("deprecation")
public class CacheUnitCalculator extends CacheSizeCalculator implements UnitCalculator {
	private final Cache<?,?> owner;

	public CacheUnitCalculator(Cache<?,?> owner) {
		this.owner = owner;
	}

	public int calculateUnits(Object key, Object value) {
		if (StringUtils.isBlank(owner.getName()))
			return 1;
		else
			return calculateUnits(key) + calculateUnits(value);
	}

	public int oldCalculateUnits(Object object) {
		if (object == null)
			return 0;
		if (object instanceof Cacheable)
			return ((Cacheable) object).getCachedSize();
		if (object instanceof Cacheable[]) {
			Cacheable array[] = (Cacheable[]) object;
			int size = CacheSizes.sizeOfObject();
			for (Cacheable cacheable : array) {
				size += cacheable.getCachedSize();
			}
			return size;
		}
		if (object instanceof Binary)
			return ((Binary) object).length();
		if (object instanceof String)
			return CacheSizes.sizeOfString((String) object);
		if (object instanceof String[])
			return CacheSizes.sizeOfString((String[]) (String[]) object);
		if (object instanceof Long)
			return CacheSizes.sizeOfLong();
		if (object instanceof List) {
			List<?> list = (List<?>) object;
			int size = CacheSizes.sizeOfObject();
			for (Object obj : list) {
				size += calculateUnits(obj);
			}

			return size;
		}
		if (object instanceof Map) {
			Map<?,?> map = (Map<?,?>) object;
			int size = CacheSizes.sizeOfObject();
			Iterator<?> it = map.keySet().iterator();
			do {
				if (!it.hasNext())
					break;
				Object key = it.next();
				size += calculateUnits(key);
				Object value = map.get(key);
				if (key != null)
					size += calculateUnits(value);
			} while (true);
			return size;
		}
		if (object instanceof Set) {
			Set<?> set = (Set<?>) object;
			int size = CacheSizes.sizeOfObject();
			for (Object obj : set) {
				size += calculateUnits(obj);
			}

			return size;
		}
		if (object instanceof Integer)
			return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt();
		if (object instanceof Integer[]) {
			Integer array[] = (Integer[]) (Integer[]) object;
			return CacheSizes.sizeOfObject() + array.length * (CacheSizes.sizeOfInt() + CacheSizes.sizeOfObject());
		}
		if (object instanceof Boolean)
			return CacheSizes.sizeOfObject() + CacheSizes.sizeOfBoolean();
		if (object instanceof long[]) {
			long array[] = (long[]) (long[]) object;
			return CacheSizes.sizeOfObject() + array.length * CacheSizes.sizeOfLong();
		}
		if (object instanceof int[]) {
			int array[] = (int[]) (int[]) object;
			return CacheSizes.sizeOfObject() + array.length * CacheSizes.sizeOfInt();
		} else {
			return ExternalizableHelper.toByteArray(object).length;
		}
	}

	public String getName() {
		return "CacheUnitCalculator";
	}

	/* (non-Javadoc)
	 * @see org.opoo.cache.CacheSizeCalculator#customCalculateUnits(java.lang.Object)
	 */
	@Override
	protected int customCalculateUnits(Object object) {
		if (object instanceof Binary)
			return ((Binary) object).length();
		
		return ExternalizableHelper.toByteArray(object).length;
	}
}