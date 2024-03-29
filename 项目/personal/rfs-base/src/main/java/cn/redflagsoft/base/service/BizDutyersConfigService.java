/*
 * $Id: BizDutyersConfigService.java 6415 2014-07-08 03:52:45Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizDutyersConfig;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BizDutyersConfigService {
	
	/**
	 * 根据配置ID查找配置
	 * @param id
	 * @return
	 */
	BizDutyersConfig getBizDutyersConfig(Long id);

	/**
	 * 根据业务定义和当前用户查找满足要求的三级责任人配置，以当前责任人作为责任人。
	 * 
	 * @param bizDef
	 * @param currentUserID
	 * @return
	 */
	BizDutyersConfig getBizDutyersConfigByUserId(BizDef bizDef, Long currentUserID);

	/**
	 * 根据业务定义查找满足要求的第一个三级责任人配置。
	 * @param bizDef
	 * @param currentUserID 
	 * @return
	 */
	BizDutyersConfig getBizDutyersConfigFirstMatch(BizDef bizDef, Long currentUserID);
	
	/**
	 * 添加三级责任人规则
	 * @param bdc
	 * 
	 */
	BizDutyersConfig saveBizDutyersConfig(BizDutyersConfig bdc);
	
	/**
	 * 删除三级责任人规则
	 * @param ids
	 */
	int deteleBizDutyersConfig(List<Long> ids);
	
	//修改三级责任人规则
	
	/**
	 * 修改三级责任人规则
	 * @param bizDutyersConfig
	 * 
	 */
	BizDutyersConfig updateBizDutyersConfig(BizDutyersConfig bizDutyersConfig);
	
	/**
	 * 根据当前责任人Id查询
	 * @param currentDutyerId
	 * @return
	 */
	BizDutyersConfig getBizDutyersConfigByDutyerId(Long currentDutyerId);

	/**
	 * @param bizDef
	 * @param dutyEntityID
	 * @return
	 */
	BizDutyersConfig getBizDutyersConfigByDutyEntityID(BizDef bizDef, Long dutyEntityID);
}
