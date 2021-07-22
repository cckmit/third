/*
 * $Id: MapBean.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用 Map 封装的数据结构。
 * 该数据结构记录单条查询结果。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MapBean extends LinkedHashMap<String,Object> implements Serializable{
	private static final long serialVersionUID = 2190970985166203521L;
	public MapBean(Map<? extends String, ? extends Object> m) {
		super(m);
	}
}
