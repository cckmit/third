/*
 * $Id: MapBeanList.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Map数据结构组成的集合。
 * 
 * <p>如果查询该集合时有分组字段，将记录分组字段（属性）。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MapBeanList extends ArrayList<MapBean> {
	private static final long serialVersionUID = 3082960597078942706L;
	private final String[] groupByFields;
	private GroupLevel groupLevel = GroupLevel.MingXi;
//	public MapBeanList(Collection<? extends MapBean> c) {
//		super(c);
//		this.groupByFields = null;
//	}
	public MapBeanList(Collection<? extends MapBean> c, String... groupByFields) {
		super(c);
		this.groupByFields = groupByFields;
	}
	public MapBeanList(Collection<? extends MapBean> c, GroupLevel lv, String... groupByFields) {
		super(c);
		this.groupByFields = groupByFields;
		this.groupLevel = lv;
	}
	
	public String[] getGroupByFields() {
		return groupByFields;
	}
	
//	public MapBeanList(List<Map<String,Object>> c) {
//		for(Map<String,Object> m: c){
//			add(new MapBean(m));
//		}
//	}
	
	/**
	 * @param groupLevel the groupLevel to set
	 */
	public void setGroupLevel(GroupLevel groupLevel) {
		this.groupLevel = groupLevel;
	}
	/**
	 * @return the groupLevel
	 */
	public GroupLevel getGroupLevel() {
		return groupLevel;
	}

	public static enum GroupLevel{
		/**
		 * 总计
		 */
		ZongJi,
		/**
		 * 合计
		 */
		HeJi,
		/**
		 * 小计
		 */
		XiaoJi,
		/**
		 * 明细
		 */
		MingXi;
	}
}
