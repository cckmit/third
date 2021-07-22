/*
 * $Id: LifeStageable.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;


/**
 * 该接口表示一个业务对象是不是带有一个生命阶段的对象。
 * 
 * 如果业务对象有对应的生命阶段的对象，则可以将该对象转换为生命阶段的对象。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface LifeStageable {
	
	/**
	 * 将当前对象转化为LifeStage的对象。
	 * @return
	 * 去掉，换成 LifeStageUpdater进行更新。
	 */
	//LifeStage toLifeStage();
}
