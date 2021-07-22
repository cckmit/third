/*
 * $Id: WorkDefProvider.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.WorkDef;


/**
 * Work定义提供者。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface WorkDefProvider {

	/**
	 * 
	 * @param type
	 * @return
	 */
	WorkDef getWorkDef(int workType);
}
