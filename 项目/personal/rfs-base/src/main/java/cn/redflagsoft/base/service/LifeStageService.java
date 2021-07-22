/*
 * $Id: LifeStageService.java 5056 2011-11-11 01:59:28Z zf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.LifeStage;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface LifeStageService {

	/**
	 * 根据业务对象的ID获取对象相关的LifeStage。
	 * @param id
	 * @return
	 */
	LifeStage getLifeStage(long id);
	
	List<Map<String,Object>> findSummaryLifeStage(ResultFilter resultFilter);
	
	List<LifeStage> findLifeStage(ResultFilter filter);
	
	LifeStage updateLifeStage(LifeStage lifeStage);
}
