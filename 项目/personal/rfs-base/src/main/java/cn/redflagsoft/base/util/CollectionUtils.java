/*
 * $Id: CollectionUtils.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
	 * 批量向集合中增加元素。
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
	 * 集合类型转成Set类型，去掉重复元素。
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
