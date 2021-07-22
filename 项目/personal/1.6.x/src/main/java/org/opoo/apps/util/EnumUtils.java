package org.opoo.apps.util;


public class EnumUtils {
	
	/**
	 * 
	 * @param <T>
	 * @param enumType
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumType, int ordinal) {
		T[] values = enumType.getEnumConstants();
		for(T t: values){
			if(t.ordinal() == ordinal){
				return t;
			}
		}
		throw new IllegalArgumentException("Specified ordinal does not relate to a valid enum in "
				+ enumType + "[" + ordinal + "]");
	}
}
