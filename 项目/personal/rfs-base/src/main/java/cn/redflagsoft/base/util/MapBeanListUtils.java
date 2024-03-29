/*
 * $Id: MapBeanListUtils.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.MapBean;
import cn.redflagsoft.base.bean.MapBeanList;
import cn.redflagsoft.base.bean.MapBeanList.GroupLevel;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class MapBeanListUtils {
	private static final Log log = LogFactory.getLog(MapBeanListUtils.class);
	
	
	/**
	 * @deprecated using getFlatMapBeanList2
	 * @param mapBeanLists
	 * @return
	 */
	public static List<MapBean> getFlatMapBeanList(MapBeanList... mapBeanLists){
		return MapBeanListUtils.getFlatMapBeanList(null, mapBeanLists);
	}
	/**
	 * @deprecated using getFlatMapBeanList2
	 * @param f
	 * @param mapBeanLists
	 * @return
	 */
	public static List<MapBean> getFlatMapBeanList(String f, MapBeanList... mapBeanLists){
		if(mapBeanLists == null){
			return null;
		}
		if(mapBeanLists.length == 0){
			return Collections.emptyList();
		}
		
		if(mapBeanLists.length == 1){
			return mapBeanLists[0];
		}
		
		if(mapBeanLists.length > 3){
			throw new IllegalArgumentException("不支持的查询");
		}
		
		MapBeanList multi = null;
		MapBeanList single = null;
		MapBean all = null;
		for (MapBeanList list : mapBeanLists) {
			String[] fields = list.getGroupByFields();
			if(fields == null || fields.length == 0){
				if(list.size() == 1){
					all = list.get(0);
				}
			}else if(fields.length == 1){
				single = list;
			}else if(fields.length > 1){
				multi = list;
			}
		}
		
		
		//组装结果
		List<MapBean> result = new ArrayList<MapBean>();
		//String f = null;
		if(CollectionUtils.isEmpty(multi)){
			if(CollectionUtils.isNotEmpty(single)){
				result.addAll(single);
				if(f == null){
					f = single.getGroupByFields()[0];
				}
			}
		}else{
			if(f == null){
				f = multi.getGroupByFields()[0];
			}
			if(CollectionUtils.isNotEmpty(single)){
				for (MapBean s : single) {
					Object key = s.get(f);
					for(MapBean m: multi){
						Object v = m.get(f);
						if((key == null && v == null) || (key != null && key.equals(v))){
							result.add(m);
						}
					}
					s.put(f, "小计");
					s.put("xiaoji", 1);
					result.add(s);
				}
			}
		}
		
		if(all != null){
			if(f != null){
				all.put(f, "总计");
			}
			all.put("zongji", 1);
			result.add(all);
		}
		
		return result;
	}
	
	
	/**
	 * 获取平板结构的分组查询结果。
	 * 
	 * @param summaryFields
	 * @param mapBeanLists
	 * @return
	 */
	public static List<MapBean> getFlatMapBeanList2(Map<Integer,String> summaryFields, MapBeanList... mapBeanLists){
		if(mapBeanLists == null){
			return null;
		}
		if(mapBeanLists.length == 0){
			return Collections.emptyList();
		}
		
		if(mapBeanLists.length == 1){
			return mapBeanLists[0];
		}
		
//		if(log.isDebugEnabled()){
//			System.out.println("------------排序前------------");
//			for(MapBeanList list: mapBeanLists){
//				for(MapBean map: list){
//					System.out.println(map);
//				}
//				System.out.println("------------------------");
//			}
//		}
		
		
		Arrays.sort(mapBeanLists, MAP_BEAN_LIST_COMPARATOR);
		validateSortedList(mapBeanLists);
		
		
		//组装结果
		if(summaryFields != null){
			setSummaryInfo(summaryFields, mapBeanLists);
		}
		
		if(log.isDebugEnabled()){
			System.out.println("------------排序后------------");
			for(MapBeanList list: mapBeanLists){
				for(MapBean map: list){
					System.out.println(map);
				}
				System.out.println("------------------------");
			}
		}
		
		List<MapBean> result = new ArrayList<MapBean>();
		populate(result, mapBeanLists, -1, null);
		return result;
	}

	/**
	 * 设置小计、合计和统计。
	 * @param summaryFields
	 * @param mapBeanLists
	 */
	private static void setSummaryInfo(Map<Integer,String> summaryFields, MapBeanList... mapBeanLists){
		for (MapBeanList list : mapBeanLists) {
			if(list.getGroupLevel() == GroupLevel.ZongJi){
				setSummaryField(list, summaryFields, "总计");
			}else if(list.getGroupLevel() == GroupLevel.HeJi){
				setSummaryField(list, summaryFields, "合计");
			}else if(list.getGroupLevel() == GroupLevel.XiaoJi){
				setSummaryField(list, summaryFields, "小计");
			}
		}
//		if(mapBeanLists.length >= 3){
//			setSummaryField(mapBeanLists[1], summaryFields, "小计");
//			for(int i = 2 ; i < mapBeanLists.length - 1 ; i++){
//				setSummaryField(mapBeanLists[i], summaryFields, "合计");
//			}
//		}
//		setSummaryField(mapBeanLists[mapBeanLists.length - 1], summaryFields, "总计");
	}
	/**
	 * 设置指定List的小计、合计和统计。
	 * @param list
	 * @param summaryFields
	 * @param summaryValue
	 */
	private static void setSummaryField(MapBeanList list, Map<Integer,String> summaryFields, String summaryValue){
		if(summaryFields != null){
			String f = summaryFields.get(getGroupByFieldsLength(list));
			if(f != null){
				for(MapBean bean: list){
					bean.put(f, summaryValue);
				}
			}
		}
	}
	
	/**
	 * 组装查询的平面结果。
	 * 
	 * @param result
	 * @param lists
	 * @param index
	 * @param parentGroupByFieldValues
	 * @return
	 */
	private static int populate(List<MapBean> result, MapBeanList[] lists, int index, Object[] parentGroupByFieldValues){
		MapBeanList curr = null;//lists[index];
		MapBeanList prev = null;
		int count = 0;
		if(index == -1){
			index = lists.length - 1;
		}
		curr = lists[index];
		if(index > 0){
			prev = lists[index - 1];
		}
		
		for(MapBean cb: curr){
			Object[] groupByFieldValues = getGroupByFieldValues(cb, curr.getGroupByFields());
			if(isGroupByFieldsEquals(parentGroupByFieldValues, groupByFieldValues)){
				if(prev != null){
					int c = populate(result, lists, index - 1, groupByFieldValues);
					cb.put("__Lv", lists.length - index);
					cb.put("__Rn", c);
				}
				result.add(cb);
				count++;
			}
		}
		return count;
	}
	
	
	/**
	 * 判断当前分组值数组是否属于指定的上级分组。
	 * 
	 * @param parentGroupByFieldValues
	 * @param groupByFieldValues
	 * @return
	 */
	private static boolean isGroupByFieldsEquals(Object[] parentGroupByFieldValues, Object[] groupByFieldValues) {
		//Assert.notNull(parentGroupByFieldValues, "parentGroupByFieldValues is NULL!!!!!!!!!!!!!!");
		Assert.notNull(groupByFieldValues, "groupByFieldValues is NULL!!!!!!!!!!!!!!");
		
		if(ArrayUtils.isEmpty(parentGroupByFieldValues)){
			return true;
		}
		if(ArrayUtils.isEmpty(groupByFieldValues)){
			return true;
		}
		if(parentGroupByFieldValues.length < groupByFieldValues.length){
			for(int i = 0 ; i < parentGroupByFieldValues.length ; i++){
				if(parentGroupByFieldValues[i] == null && groupByFieldValues[i] != null){
					return false;
				}
				if(!parentGroupByFieldValues[i].equals(groupByFieldValues[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 获取分组字段值数组。
	 * @param bean
	 * @param groupByFields
	 * @return
	 */
	private static Object[] getGroupByFieldValues(MapBean bean, String[] groupByFields){
		if(groupByFields == null){
			return null;
		}
		Object[] values = new Object[groupByFields.length];
		for(int i = 0 ; i < values.length ; i++){
			values[i] = bean.get(groupByFields[i]);
//			if(log.isDebugEnabled()){
//				log.debug(groupByFields[i] + " : " + values[i]);
//			}
		}
		return values;
	}
	
	/**
	 * 验证list是否正确。不能重复。
	 * @param mapBeanLists
	 */
	private static void validateSortedList(MapBeanList... mapBeanLists){
		MapBeanList previous = null;
		for(MapBeanList list: mapBeanLists){
			if(previous != null){
				int pfc = getGroupByFieldsLength(previous);
				int cfc = getGroupByFieldsLength(list);
				if(pfc == cfc){
					throw new IllegalArgumentException("分组定义重复，无法组装查询结果");
				}
				if(pfc < cfc){
					throw new IllegalArgumentException("查询结果排序错误");
				}
				if(cfc >= 1){
					String[] cfs = list.getGroupByFields();
					String[] pfs = previous.getGroupByFields();
					for(int i = 0 ; i < cfs.length ; i++){
						if(!cfs[i].equals(pfs[i])){
							throw new IllegalArgumentException("各次查询分组顺序不一致："
									+ Arrays.toString(cfs) + " :: "
									+ Arrays.toString(pfs));
						}
					}
				}
			}
			previous = list;
		}
	}
	/**
	 * 获取分组字段数组长度。
	 * @param list
	 * @return
	 */
	private static int getGroupByFieldsLength(MapBeanList list){
		String[] fields = list.getGroupByFields();
		return fields != null ? fields.length : 0;
	}
	
	private static final Comparator<MapBeanList> MAP_BEAN_LIST_COMPARATOR = new Comparator<MapBeanList>(){
		public int compare(MapBeanList o1, MapBeanList o2) {
			int fc1 = getGroupByFieldsLength(o1);
			int fc2 = getGroupByFieldsLength(o2);
			return fc2 - fc1;
		}
	};
	
	
	
	
	
	public static void main(String[] args){
		MapBeanList list0 = new MapBeanList(new ArrayList<MapBean>(), "c1", "c2", "c3", "c4");
		list0.add(mb("宝安", "道路", "新建", "高速", 10));
		list0.add(mb("宝安", "道路", "新建", "快速", 20));
		list0.add(mb("宝安", "道路", "改建", "高速", 30));
		list0.add(mb("宝安", "道路", "改建", "快速", 40));
		list0.add(mb("南山", "道路", "新建", "高速", 50));
		list0.add(mb("南山", "道路", "新建", "快速", 60));
		list0.add(mb("南山", "道路", "改建", "高速", 70));
		list0.add(mb("南山", "道路", "改建", "快速", 80));
		
		MapBeanList list1 = new MapBeanList(new ArrayList<MapBean>(), "c1", "c2", "c3");
		list1.add(mb("宝安", "道路", "新建", "", 30));
		list1.add(mb("宝安", "道路", "改建", "", 70));
		list1.add(mb("南山", "道路", "新建", "", 110));
		list1.add(mb("南山", "道路", "改建", "", 150));
		
		MapBeanList list2 = new MapBeanList(new ArrayList<MapBean>(), "c1", "c2");
		list2.add(mb("宝安", "道路", "", "", 100));
		list2.add(mb("南山", "道路", "", "", 260));
		
		MapBeanList list3 = new MapBeanList(new ArrayList<MapBean>(), "c1");
		list3.add(mb("宝安", "", "", "", 100));
		list3.add(mb("南山", "", "", "", 260));
		
		MapBeanList list4 = new MapBeanList(new ArrayList<MapBean>());
		list4.add(mb("", "", "", "", 360));
		
		Map<Integer, String> summaryFields = new HashMap<Integer, String>();
		summaryFields.put(0, "c1");
		summaryFields.put(1, "c2");
		summaryFields.put(2, "c3");
		summaryFields.put(3, "c4");
		
		
		List<MapBean> list = MapBeanListUtils.getFlatMapBeanList2(summaryFields, list2, list4, list0, list3, list1);
		for (MapBean mapBean : list) {
			System.out.println(mapBean);
		}
	}
	
	private static MapBean mb(String c1, String c2, String c3, String c4, Number d1){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("c1", c1);
		map.put("c2", c2);
		map.put("c3", c3);
		map.put("c4", c4);
		map.put("d1", d1);
		return new MapBean(map);
	}
}
