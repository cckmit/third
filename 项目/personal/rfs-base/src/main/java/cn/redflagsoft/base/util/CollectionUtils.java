/*
 * $Id: CollectionUtils.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CollectionUtils {
	
	/**
	 * �����򼯺�������Ԫ�ء�
	 * 
	 * @param <E>
	 * @param c
	 * @param objects
	 */
	public static <E> boolean add(Collection<? super E> c, E... elements){
		return Collections.addAll(c, elements);
	}
	
	/**
	 * 
	 * @param <E>
	 * @param c
	 * @param collections
	 */
	public static <E> boolean addAll(Collection<? super E> c, Collection<? extends E>... collections){
		boolean result = false;
		for(Collection<? extends E> o: collections){
			result |= c.addAll(o);
		}
		return result;
	}
	
	
	/**
	 * ��������ת��Set���ͣ�ȥ���ظ�Ԫ�ء�
	 * @param <E>
	 * @param c
	 * @return
	 */
	public static <E> Set<E> toSet(Collection<E> c){
		Set<E> set = new HashSet<E>();
		for(E e: c){
			if(!set.contains(e)){
				set.add(e);
			}
		}
		return set;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		List<Long> list = new ArrayList<Long>();
		CollectionUtils.add(list, 10L, 20L, 30L, 40L, 50L);
		System.out.println(list);
		
		List<Long> list2 = new ArrayList<Long>();
		CollectionUtils.add(list2, 100L, 200L, 300L, 400L, 500L);
		System.out.println(list2);
		
		CollectionUtils.addAll(list, list2);
		System.out.println(list);
	}
}
