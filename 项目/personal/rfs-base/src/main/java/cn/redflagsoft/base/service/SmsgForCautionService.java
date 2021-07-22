/*
 * $Id: SmsgForCautionService.java 5487 2012-04-09 04:05:21Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Smsg;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SmsgForCautionService {
	
	List<Smsg> createSmsgForCaution(final Caution caution, Risk risk, RiskRule riskRule) throws Exception;
}
