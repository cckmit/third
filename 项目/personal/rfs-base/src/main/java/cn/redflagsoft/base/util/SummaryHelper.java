/*
 * $Id: SummaryHelper.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Alex Lin(alex@opoo.org)
 * 
 */
public class SummaryHelper {
	
	private static class GroupFields extends HashMap<String, Object> implements Serializable {
		private static final long serialVersionUID = 7917004014027322192L;

		public GroupFields(Object obj, String... groupFieldNames) {
			if (groupFieldNames != null) {
				for(String f: groupFieldNames){
					put(f, getProperty(obj, f));
				}
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof GroupFields)) {
				return false;
			}
			GroupFields gf = (GroupFields) obj;
			return new EqualsBuilder().append(keySet().toArray(), gf.keySet().toArray())
					.append(values().toArray(), gf.values().toArray()).isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(keySet().toArray()).append(values().toArray()).toHashCode();
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).toString();
		}
	}

	private static class SummaryFields extends HashMap<String,Integer> {
		
		private static final long serialVersionUID = 6124697774973863959L;
		public static final String TOTAL_FIELD = "total";

		private SummaryValueAcceptor acceptor;
		private String[] summaryFieldNames;

		public SummaryFields(Object obj, SummaryValueAcceptor acceptor, String... summaryFieldNames) {
			this.acceptor = acceptor;
			this.summaryFieldNames = summaryFieldNames;
			for(String f: summaryFieldNames){
				put(f, 0);
			}
			put(TOTAL_FIELD, 0);
			addSummaryObject(obj);
		}

		public void addSummaryObject(Object obj) {
			for(String f: summaryFieldNames){
				Object value = getProperty(obj, f);
				if(acceptor.accepts(value)){
					Integer integer = get(f);
//					if (integer == null) {
//						integer = 0;
//					}
					int newValue = integer.intValue() + 1;
					put(f, new Integer(newValue));
				}
			}

			Integer tt = get(TOTAL_FIELD);
			put(TOTAL_FIELD, tt.intValue() + 1);
		}
	}

	public static Object getProperty(Object obj, String prop) {
		try {
			return PropertyUtils.getProperty(obj, prop);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 值是否可以接受
	 *
	 */
	public static interface SummaryValueAcceptor{
		boolean accepts(Object value);
	}
	
	/**
	 * 
	 * @param list
	 * @param summaryFieldNames
	 * @param acceptor
	 * @param groupFieldNames
	 * @return
	 */
	public static List<Map<String, Object>> makeSummaryList(List<Object> list, 
			String[] summaryFieldNames, SummaryValueAcceptor acceptor, 
			String... groupFieldNames) {
		if(list == null){
			return null;
		}
		if(list.isEmpty()){
			return Collections.emptyList();
		}
		
		Map<GroupFields, SummaryFields> map = new HashMap<GroupFields, SummaryFields>();
		for (Object obj : list) {
			GroupFields gf = new GroupFields(obj, groupFieldNames);
			SummaryFields summary = map.get(gf);
			if (summary != null) {
				summary.addSummaryObject(obj);
			} else {
				map.put(gf, new SummaryFields(obj, acceptor, summaryFieldNames));
			}
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Map.Entry<GroupFields, SummaryFields> en : map.entrySet()) {
			Map<String, Object> tmp = new HashMap<String, Object>(en.getKey());
			tmp.putAll(en.getValue());
			result.add(tmp);
		}
		//if(result.size() == 1){
		//	result.get(0).put("total", list.size());
		//}
		return result;
	}
}
