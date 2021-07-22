/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.util.List;

import cn.redflagsoft.base.bean.Peoples;
import cn.redflagsoft.base.dao.PeoplesDao;

/**
 * 
 * @author ymq
 *
 */
public class PeoplesHibernateDao extends AbstractBaseHibernateDao<Peoples, Long> implements PeoplesDao{
	@SuppressWarnings("unchecked")
	public List<Peoples> findPeoplesByTypeAndFstPeople(int type,Long fstPeople){
		String qs = "select a from Peoples a where a.type=? and a.fstPeople=?";
		return getQuerySupport().find(qs, new Object[]{type,fstPeople});
	}
	
	@SuppressWarnings("unchecked")
	public List<Peoples> fingBySndPeople(Long sndPeople){
		String qs = "select a from Peoples a where a.sndPeople=?";
		return getQuerySupport().find(qs, sndPeople);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findDistinctAllSndPeople(){
		String qs = "select distinct a.sndPeople from Peoples a";
		return getQuerySupport().find(qs);
	}
}
