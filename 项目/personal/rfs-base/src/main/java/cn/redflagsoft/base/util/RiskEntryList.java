/*
 * $Id: RiskEntryList.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.redflagsoft.base.bean.RiskEntry;

/**
 * 自定义的RiskEntry的List类。
 * 
 * @author Alex Lin
 *
 */
public class RiskEntryList extends ArrayList<RiskEntry> implements List<RiskEntry>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2961160336346901372L;

	public RiskEntryList() {
		super();
	}

	public RiskEntryList(Collection<? extends RiskEntry> c) {
		super(c);
	}
	
	/**
	 * 
	 * @param initialCapacity
	 */
	public RiskEntryList(int initialCapacity) {
		super(initialCapacity);
	}
	
	
	/**
	 * 
	 * @param re
	 * @return
	 */
	public boolean addRiskEntry(RiskEntry re){
		return add(re);
	}
	
	
	/**
	 * 根据对象属性查找对应的RiskEntry。
	 * 
	 * @param attr
	 * @return
	 */
	public RiskEntry findRiskEntryByObjectAttr(String attr){
		for(RiskEntry re: this){
			if(attr.equals(re.getObjectAttr())){
				return re;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public String toString(){
		return RiskMonitorableUtils.riskEntriesToString(this);
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static RiskEntryList valueOf(String string){
		List<RiskEntry> list = RiskMonitorableUtils.stringToRiskEntries(string);
		if(list == null){
			return null;
		}
		return new RiskEntryList(list);
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static RiskEntryList valueOfList(List<RiskEntry> list){
		if(list == null){
			return null;
		}
		if(list instanceof RiskEntryList){
			return (RiskEntryList) list;
		}
		return new RiskEntryList(list);
	}
}
