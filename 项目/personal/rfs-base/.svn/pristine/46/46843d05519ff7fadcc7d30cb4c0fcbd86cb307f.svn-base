package cn.redflagsoft.base.service.impl;

import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.service.LifeStageUpdater;

public class IssueLifeStageUpdater extends AbstractLifeStageUpdater<Issue>
		implements LifeStageUpdater<Issue> {

	@Override
	protected void copyToLifeStage(Issue t, LifeStage ls) {
		Assert.notNull(t, "问题对象不能为空。");
		Assert.notNull(t.getDescription(), "问题对象属性不能为空");
		ls.setTypeName(t.getTypeName());
		ls.setType(t.getType());
		ls.setString0(t.getGrade() + "");
		ls.setString1(t.getGradeName());
		ls.setRefObjectId(t.getRefObjectId());
		ls.setRefObjectName(t.getRefObjectName());
		ls.setRefObjectType(t.getRefObjectType());
		
		String description = t.getDescription();
		if(description != null && description.length() > 0){
			if(description.length() > 100){
				ls.setName(description.substring(0, 100));
			}else{
				ls.setName(description);
			}
		}
	}

}
