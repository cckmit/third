/*
 * $Id: AbstractLifeStageUpdater.java 5060 2011-11-14 02:53:42Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.LifeStageDao;
import cn.redflagsoft.base.service.LifeStageUpdater;

/**
 * LifeStage 更新器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <T>
 *
 */
public abstract class AbstractLifeStageUpdater<T extends RFSObject> implements LifeStageUpdater<T> {
	private LifeStageDao lifeStageDao;
	
	
	public LifeStageDao getLifeStageDao() {
		return lifeStageDao;
	}

	public void setLifeStageDao(LifeStageDao lifeStageDao) {
		this.lifeStageDao = lifeStageDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.LifeStageUpdater#create(cn.redflagsoft.base.bean.LifeStageable)
	 */
	public LifeStage create(T t) {
		LifeStage ls = new LifeStage(t);
		copyToLifeStage(t, ls);
		return getLifeStageDao().save(ls);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.LifeStageUpdater#delete(cn.redflagsoft.base.bean.LifeStageable)
	 */
	public void delete(T t) {
		getLifeStageDao().remove(t.getId());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.LifeStageUpdater#update(cn.redflagsoft.base.bean.LifeStageable)
	 */
	public LifeStage update(T t) {
		LifeStage ls = getLifeStageDao().get(t.getId());
		if(ls == null){
			throw new IllegalStateException("不存在相关的LifeStage数据，对象ID: " + t.getId());
		}
		//该操作同步了id, name, managerId, managerName, objectType, remark, status, type等属性。		
		ls.copyFrom(t);
		copyToLifeStage(t, ls);
		return getLifeStageDao().update(ls);
	}
	
	
	/**
	 * 复制 LifeStageableObject 中对应的属性到 LifeStage 对象中。
	 * @param t
	 * @param ls
	 */
	protected abstract void copyToLifeStage(T t, LifeStage ls);
}
