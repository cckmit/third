/*
 * $Id: WorkDefinitionServiceImpl.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.WorkDef;
import cn.redflagsoft.base.bean.WorkDefinition;
import cn.redflagsoft.base.dao.WorkDefinitionDao;
import cn.redflagsoft.base.service.WorkDefProvider;
import cn.redflagsoft.base.service.WorkDefinitionService;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkDefinitionServiceImpl implements WorkDefinitionService,WorkDefProvider {
	private WorkDefinitionDao workDefinitionDao;
	
	
	public WorkDefinitionDao getWorkDefinitionDao() {
		return workDefinitionDao;
	}

	public void setWorkDefinitionDao(WorkDefinitionDao workDefinitionDao) {
		this.workDefinitionDao = workDefinitionDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkDefinitionService#getWorkDefinition(short)
	 */
	public WorkDefinition getWorkDefinition(int workType) {
		return workDefinitionDao.get(workType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkDefinitionService#remove(short)
	 */
	public void remove(int workType) {
		workDefinitionDao.remove(workType);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkDefinitionService#updateWorkDefinition(cn.redflagsoft.base.bean.WorkDefinition)
	 */
	public WorkDefinition updateWorkDefinition(WorkDefinition workDefinition) {
		return workDefinitionDao.update(workDefinition);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.WorkDefProvider#getWorkDef(short)
	 */
	public WorkDef getWorkDef(int workType) {
		return getWorkDefinition(workType);
	}
}
