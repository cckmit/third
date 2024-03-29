/*
 * $Id: EntityGroupScheme.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.entity;

import java.util.List;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.service.EntityGroupService;


/**
 * 
 * @deprecated using OrgGroupScheme
 */
public class EntityGroupScheme extends AbstractScheme {
	private EntityGroupService entityGroupService;
	private List<Long> ids;
	private Long groupId;
	
	public EntityGroupService getEntityGroupService() {
		return entityGroupService;
	}

	public void setEntityGroupService(EntityGroupService entityGroupService) {
		this.entityGroupService = entityGroupService;
	}
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Object doCreate() throws SchemeException {
		return entityGroupService.saveEntityGroup(groupId, ids);
	}

	public Object doScheme() throws SchemeException {
		return this.doCreate();
	}
}
