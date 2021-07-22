/*
 * $Id ReceiverHibernateDao.java$
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Peoples;

/**
 * 
 * @author ymq
 *
 */
public interface PeoplesDao extends Dao<Peoples, Long> {
	List<Peoples> findPeoplesByTypeAndFstPeople(int type,Long fstPeople);
	List<Peoples> fingBySndPeople(Long sndPeople);
	List<Long> findDistinctAllSndPeople();
}
