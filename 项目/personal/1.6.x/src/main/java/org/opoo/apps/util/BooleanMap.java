package org.opoo.apps.util;

import java.util.HashMap;

public class BooleanMap extends HashMap<Integer, Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4634492971343651914L;
	
	
//	private void checkKey(Integer key){
//		if(key == null){
//			throw new IllegalArgumentException("��ʶ�洢λ�ò���Ϊ�ա�");
//		}
//		int i = key.intValue();
//		if(i < 1 || i > 63){
//			throw new IllegalArgumentException("��ʶ�洢λ�ñ����� 1 �� 63 ֮�䡣");
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
	 * �жϴ洢��ָ��λ�õı�־�Ƿ�Ϊ true��
	 * 
	 * @param position
	 * @return δ���û�������Ϊfalse�ķ���Ϊfalse��
	 */
	public boolean isTrue(int position){
		Boolean b = get(position);
		return b == null ? false : b.booleanValue();
	}
	

	
	/**
	 * �� Map ������תΪ long ���ݡ�
	 * ע�⣬ֻ��ת key ֵΪ 1 �� 63�Ĳ������ݡ�
	 * 
	 * ����������ڶ�� boolean �����ݴ洢�����ݿ��е�һ�� long ���ֶΣ���ʡ�洢�ռ䡣
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
