/*
 * $Id: CreationTimeAscComparator.java 6447 2015-06-11 07:03:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Comparator;

import cn.redflagsoft.base.bean.VersionableBean;

/**
 * 按创建时间正序的比较器。
 * 
 * @author lcj
 *
 */
public class CreationTimeAscComparator implements Comparator<VersionableBean> {

	public int compare(VersionableBean o1, VersionableBean o2) {
		long t1 = 0L;
		long t2 = 0L;
		
		if(o1.getCreationTime() == null){
			t1 = o1.getCreationTime().getTime();
		}
		if(o2.getCreationTime() == null){
			t2 = o2.getCreationTime().getTime();
		}
		return (int) (t1 - t2);
	}
}
