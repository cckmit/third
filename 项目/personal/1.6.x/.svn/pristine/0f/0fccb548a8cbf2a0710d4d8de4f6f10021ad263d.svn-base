package org.opoo.apps.util;

public abstract class ExtendedPropertyUtils {
	/**
	 * 验证属性的有效性。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String validateProperty(String key, String value) {
		if (key == null || value == null || "".equals(key))
			throw new NullPointerException("Key or value cannot be null. Key="
					+ key + ", value=" + value);
		//if (!(key instanceof String) || !(value instanceof String))
		//	throw new IllegalArgumentException("Key and value must be of type String.");
		//if (value.equals(""))
		//	value = " ";
		return value;
	}
}