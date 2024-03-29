/*
 * $Id: VersionableCreationTimeComparator.java 4396 2011-05-13 07:14:20Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Comparator;

import org.opoo.ndao.domain.Versionable;

/**
 * 通过创建时间进行比较的比较器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.1
 */
public class VersionableCreationTimeComparator implements Comparator<Versionable> {
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Versionable o1, Versionable o2) {
		if(o1 != null && o2 != null && o1.getCreationTime() != null && o2.getCreationTime() != null){
			return o1.getCreationTime().compareTo(o2.getCreationTime());
		}
		return 0;
	}

}
