/*
 * $Id: EnumSelectDataHandler.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.DefaultLabelDataBean;
import cn.redflagsoft.base.bean.SelectDataSource;

/**
 * 处理枚举类型的选择器数据源。
 * 
 * <p>枚举类型的数据格式为：<code><b>1:有效;2:无效;3:不知道</b></code>
 * <p>多个数据项使用半角或全角分号分隔，一个数据项中值和标签之间使用冒号分隔。枚举
 * 类型最终解析为键值对数据类型的集合。
 * 
 * <p>排序条件无效。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EnumSelectDataHandler implements SelectDataHandler<Labelable> {

	public boolean supports(SelectDataSource dataSource) {
		return SelectDataSource.CAT_枚举  == dataSource.getCat();
	}
	/**
	 * 格式：  1:有效;2:无效;3:不知道
	 */
	public List<Labelable> findSelectData(SelectDataSource dataSource, ResultFilter filter) {
		return parseToList(dataSource.getSource());
	}
	
	public static List<Labelable> parseToList(String str){
		List<Labelable> list = new ArrayList<Labelable>();
		StringTokenizer st = new StringTokenizer(str, ";； ");
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			Labelable labelable = parseToLabelable(token);
			if(labelable != null){
				list.add(labelable);
			}
		}
		return list;
	}
	
	public static Labelable parseToLabelable(String str){
		String[] arr = str.split(":");
		if(arr.length >= 2 && StringUtils.isNotBlank(arr[0]) && StringUtils.isNotBlank(arr[1])){
			return new DefaultLabelDataBean(arr[1].trim(), arr[0].trim());
		}
		return null;
	}
	
	public static String labelableListToString(List<Labelable> list){
		StringBuffer sb = new StringBuffer();
		for(Labelable lb:list){
			if(sb.length() > 0){
				sb.append(";");
			}
			sb.append(lb.getData()).append(":").append(lb.getLabel());
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args){
		List<Labelable> list = parseToList(" 1:有效;2:无效;3:不知道");
		System.out.println(list);
	}

}
