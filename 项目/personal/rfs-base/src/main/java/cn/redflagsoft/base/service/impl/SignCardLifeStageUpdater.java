package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.service.LifeStageUpdater;

public class SignCardLifeStageUpdater extends AbstractLifeStageUpdater<SignCard>
		implements LifeStageUpdater<SignCard> {

	@Override
	protected void copyToLifeStage(SignCard t, LifeStage ls) {
		ls.setSn(t.getCode());
		ls.setName(t.getLabel());
		ls.setTypeName(t.getGradeName());
		ls.setRefObjectType(t.getObjectType());
		ls.setRefObjectId(t.getId());
		ls.setRefObjectName(t.getLabel());
		ls.setManagerId(t.getRvDutyerID());
		ls.setManagerName(t.getRvDutyerName());
	}

}
