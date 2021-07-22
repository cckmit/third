package org.opoo.apps.util;

import java.util.HashMap;

public class BooleanMap extends HashMap<Integer, Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4634492971343651914L;
	
	
//	private void checkKey(Integer key){
//		if(key == null){
//			throw new IllegalArgumentException("标识存储位置不能为空。");
//		}
//		int i = key.intValue();
//		if(i < 1 || i > 63){
//			throw new IllegalArgumentException("标识存储位置必须在 1 到 63 之间。");
//		}
//	}
//	
//	
//	@Override
//	public Boolean put(Integer key, Boolean value) {
//		checkKey(key);
//		return super.put(key, value);
//	}
//
//
//	@Override
//	public void putAll(Map<? extends Integer, ? extends Boolean> m) {
//		for(Map.Entry<? extends Integer, ? extends Boolean> en: m.entrySet()){
//			put(en.getKey(), en.getValue());
//		}
//	}

	/**
	 * 判断存储在指定位置的标志是否为 true。
	 * 
	 * @param position
	 * @return 未设置或者设置为false的返回为false。
	 */
	public boolean isTrue(int position){
		Boolean b = get(position);
		return b == null ? false : b.booleanValue();
	}
	

	
	/**
	 * 将 Map 中数据转为 long 数据。
	 * 注意，只能转 key 值为 1 到 63的部分数据。
	 * 
	 * 这个功能用于多个 boolean 型数据存储在数据库中的一个 long 型字段，节省存储空间。
	 * @return
	 */
	public long longValue(){
		StringBuffer sb = new StringBuffer();
		for(int i = 1 ; i <= 63 ; i++){
			sb.append(isTrue(i) ? "1" : "0");			
		}
		String s = sb.reverse().toString();
//		System.out.println(s);
		return Long.parseLong(s, 2);
	}
	
	
	public static BooleanMap valueOf(long l){
		BooleanMap map = new BooleanMap();
		
		String string = Long.toBinaryString(l);
		char[] chars = string.toCharArray();
		int position = 1;
		for(int i = chars.length - 1 ; i >= 0 ; i--){
			if(chars[i] == '1'){
				map.put(position, true);
			}
			//map.put(position++, chars[i] == '1');
			position++;
		}
		return map;
	}
	
	
	//1-62
	public static void main(String[] args){
		String string = Long.toBinaryString(Long.MAX_VALUE);
		System.out.println(string.length());
		
		BooleanMap map = new BooleanMap();
		map.put(10, true);
		map.put(30, true);
		map.put(63, false);
		System.out.println(map.longValue());
		string = Long.toBinaryString(map.longValue());
		System.out.println(string);
		System.out.println(map.isTrue(10));
		
		long x = map.longValue();
		BooleanMap map2 = BooleanMap.valueOf(x);
		System.out.println(map2.isTrue(63));
	}
}
