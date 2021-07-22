package org.opoo.cache;

import java.util.Collection;
import java.util.Map;


/**
 * 缓存大小计算器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class CacheSizeCalculator implements Calculator {

	/**
	 * 计算指定对象的缓存大小。
	 */
	public int calculateUnits(Object object) {
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
		if (object instanceof String)
			return CacheSizes.sizeOfString((String) object);
		
		if (object instanceof String[])
			return CacheSizes.sizeOfString((String[]) object);
		
		if (object instanceof Long)
			return CacheSizes.sizeOfLong();
		
		if (object instanceof Long[]) {
			Long array[] = (Long[]) object;
			return CacheSizes.sizeOfObject() + array.length * CacheSizes.sizeOfLong();
		}
		
		if(object instanceof Collection){
			int size = CacheSizes.sizeOfObject();
			for(Object obj: (Collection<?>) object){
				size += calculateUnits(obj);
			}
			return size;
		}
		
		if (object instanceof Map) {
			Map<?,?> map = (Map<?,?>) object;
			int size = CacheSizes.sizeOfObject();
			for(Map.Entry<?,?> en: map.entrySet()){
				Object key = en.getKey();
				Object value = en.getValue();
				size += calculateUnits(key);
				if(key != null){
					size += calculateUnits(value);
				}
			}
			return size;
		}
		
		if (object instanceof Integer)
			return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt();
		
		if (object instanceof Integer[]) {
			Integer array[] = (Integer[]) object;
			return CacheSizes.sizeOfObject() + array.length * (CacheSizes.sizeOfInt() + CacheSizes.sizeOfObject());
		}
		if (object instanceof Boolean)
			return CacheSizes.sizeOfObject() + CacheSizes.sizeOfBoolean();
		
		if (object instanceof long[]) {
			long array[] = (long[]) object;
			return CacheSizes.sizeOfObject() + array.length * CacheSizes.sizeOfLong();
		}
		
		if (object instanceof int[]) {
			int array[] = (int[]) object;
			return CacheSizes.sizeOfObject() + array.length * CacheSizes.sizeOfInt();
		} else {
			//return ExternalizableHelper.toByteArray(object).length;
			return customCalculateUnits(object);
		}
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	protected int customCalculateUnits(Object object){
		return CacheSizes.sizeOfString(object.toString());
	}
}
