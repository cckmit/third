/*
 * $Id: DutyersInfoManager.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizInstance;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * 三级责任人管理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface DutyersInfoManager {

	/**
	 * 获取责任人信息。
	 * 
	 * @param bizDef 业务定义
	 * @param object 相关对象
	 * @param currentUser 当前用户ID
	 * @return
	 */
	DutyersInfo getDutyersInfo(BizDef bizDef, RFSObject object, Long currentUserID);
	
	/**
	 * 设置责任人信息。
	 * 
	 * @param biz 业务实例
	 * @param duetyers 责任人信息
	 */
	void setDutyersInfo(BizInstance biz, DutyersInfo dutyers);
	
	/**
	 * 获取并设置责任人信息。
	 * @param biz
	 * @param bizDef
	 * @param object
	 * @param userID
	 * @return
	 * @see #getDutyersInfo(BizDef, RFSObject, Long)
	 * @see #setDutyersInfo(BizInstance, DutyersInfo)
	 */
	DutyersInfo findAndSetDutyers(BizInstance biz, BizDef bizDef, Object object, Long userID);
}
