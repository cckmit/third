/*
 * $Id: AbstractLifeStageUpdater.java 5060 2011-11-14 02:53:42Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.LifeStageDao;
import cn.redflagsoft.base.service.LifeStageUpdater;

/**
 * LifeStage ��������
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
			throw new IllegalStateException("��������ص�LifeStage���ݣ�����ID: " + t.getId());
		}
		//�ò���ͬ����id, name, managerId, managerName, objectType, remark, status, type�����ԡ�		
		ls.copyFrom(t);
		copyToLifeStage(t, ls);
		return getLifeStageDao().update(ls);
	}
	
	
	/**
	 * ���� LifeStageableObject �ж�Ӧ�����Ե� LifeStage �����С�
	 * @param t
	 * @param ls
	 */
	protected abstract void copyToLifeStage(T t, LifeStage ls);
}
