/*
 * $Id: Checkable.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

/**
 * 可标记为选中的对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Checkable<K extends Serializable> {
	/**
	 * 对象的主键。
	 * @return
	 */
	K getId();
	
	/**
	 * 是否选中。
	 * 
	 * @return
	 */
	boolean isChecked();

	/**
	 * 设置选中状态。
	 * @param checked
	 */
	void setChecked(boolean checked);
}
