package org.opoo.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * 用来计算缓存大小。
 * 注意，并不是精确值。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class CacheSizes {

	private static final int MAP_SIZE = 36;
	private static final int OBJECT_SHELL_SIZE = 8;
//	private static final int OBJREF_SIZE = 4;
	private static final int LONG_FIELD_SIZE = 8;
	private static final int INT_FIELD_SIZE = 4;
	private static final int SHORT_FIELD_SIZE = 2;
	private static final int CHAR_FIELD_SIZE = 2;
	private static final int BYTE_FIELD_SIZE = 1;
	private static final int BOOLEAN_FIELD_SIZE = 1;
	private static final int DOUBLE_FIELD_SIZE = 8;
	private static final int FLOAT_FIELD_SIZE = 4;
	private static final int DATE_FIELD_SIZE = 12;

	private CacheSizes() {
	}

	/**
	 * 对象的基本缓存大小。
	 * @return
	 */
	public static int sizeOfObject() {
		return OBJECT_SHELL_SIZE;
	}

	/**
	 * 指定字符串的缓存大小。
	 * 
	 * @param string
	 * @return
	 */
	public static int sizeOfString(String string) {
		if (string == null){
			return 0;
		}else{
			return sizeOfObject() + sizeOfLong() + 3 * sizeOfInt() + string.length() * sizeOfChar();
		}
	}

	/**
	 * 指定字符串数组的缓存大小。
	 * @param string
	 * @return
	 */
	public static int sizeOfString(String string[]) {
		if (string == null){
			return 0;
		}
		int size = sizeOfObject();
		for(String aString: string){
			size += sizeOfString(aString);
		}
		return size;
	}

	public static int sizeOfInt() {
		return INT_FIELD_SIZE;
	}

	public static int sizeOfChar() {
		return CHAR_FIELD_SIZE;
	}

	public static int sizeOfBoolean() {
		return BOOLEAN_FIELD_SIZE;
	}

	public static int sizeOfLong() {
		return LONG_FIELD_SIZE;
	}

	public static int sizeOfDouble() {
		return DOUBLE_FIELD_SIZE;
	}

	public static int sizeOfByte() {
		return BYTE_FIELD_SIZE;
	}

	public static int sizeOfDate() {
		return DATE_FIELD_SIZE;
	}

	public static int sizeOfMap() {
		return MAP_SIZE;
	}
	
	public static int sizeOfFloat(){
		return FLOAT_FIELD_SIZE;
	}
	
	public static int sizeOfShort(){
		return SHORT_FIELD_SIZE;
	}

	public static int sizeOfStringMap(Map<String, String> map) {
		if (map == null){
			return 0;
		}
		int size = MAP_SIZE;
		Collection<String> values = map.values();
		for(String value: values){
			size += sizeOfString(value);
		}

		Set<String> keys = map.keySet();
		for (String key: keys) {
			size += sizeOfString(key);
		}
		return size;
	}
	public static <K,V> int sizeOfMap(Map<K,V> map){
		if (map == null) {
			return 0;
		}
		int size = MAP_SIZE;
		size += sizeOfList(map.keySet());
		size += sizeOfList(map.values());
		return size;
	}

	public static int sizeOfLongList(int listSize) {
		if (listSize <= 0){
			return 0;
		}else{
			return listSize * (sizeOfLong() + sizeOfObject()) + MAP_SIZE;
		}
	}

	public static <E> int sizeOfList(Collection<E> list) {
		if (list == null)
			return 0;
		int size = MAP_SIZE;
		for(E obj: list){
			if(obj == null){
				continue;
			}
			if (obj instanceof String) {
				size += sizeOfString((String) obj);
				continue;
			}
			if (obj instanceof Long) {
				size += sizeOfLong() + sizeOfObject();
				continue;
			}
			if (obj instanceof Integer) {
				size += sizeOfInt() + sizeOfObject();
				continue;
			}
			if (obj instanceof Boolean){
				size += sizeOfBoolean() + sizeOfObject();
				continue;
			}
			if(obj instanceof Cacheable){
				size += ((Cacheable) obj).getCachedSize();
				continue;
			}
			
			size += sizeOfString(obj.toString());
		}

		return size;
	}
}
