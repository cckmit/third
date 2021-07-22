/*
 * $Id: CautionLifeStageUpdater.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.service.LifeStageUpdater;
import cn.redflagsoft.base.service.impl.AbstractLifeStageUpdater;

/**
 * @author ZF
 *
 */
public class CautionLifeStageUpdater extends AbstractLifeStageUpdater<Caution> implements LifeStageUpdater<Caution> {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.AbstractLifeStageUpdater#copyToLifeStage(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.LifeStage)
	 */
	@Override
	protected void copyToLifeStage(Caution t, LifeStage lifeStage) {
		lifeStage.setSn(t.getCode());
		lifeStage.setManagerId(t.getDutyClerkID());
		lifeStage.setManagerName(t.getDutyClerkName());

		lifeStage.setRefObjectId(t.getId());
		lifeStage.setRefObjectName(t.getName());
		lifeStage.setRefObjectType(t.getObjectType());
		//lifeStage.setString0(t.getGrade() + "");
		//lifeStage.setString1(t.getGrade()+"");

		lifeStage.setString2(t.getType() + "");
		lifeStage.setString3(t.getTypeName());

		lifeStage.setString4(t.getStatus() + "");
		lifeStage.setString5(t.getStatusName());
		//lifeStage.setString6(t.getProperty() + "");
		//lifeStage.setString7(t.getPropertyName());
	}
	
	
}
