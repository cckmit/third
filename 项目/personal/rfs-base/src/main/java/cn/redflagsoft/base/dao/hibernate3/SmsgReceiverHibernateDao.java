/*
 * $Id: SmsgReceiverHibernateDao.java 5933 2012-07-21 09:50:00Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;


import java.util.List;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.dao.SmsgReceiverDao;

/**
 * @author py
 *
 */
public class SmsgReceiverHibernateDao extends AbstractBaseHibernateDao<SmsgReceiver,Long> implements SmsgReceiverDao{
	@SuppressWarnings("rawtypes")
	public SmsgReceiver smsgReadForClerk(Clerk clerk,Long smsgId){
		String hql = "select r from Smsg s,SmsgReceiver r where s.id = r.smsgId and r.toId =? and r.smsgId =? ";
		Object whe[] = new Object[]{clerk.getId(),smsgId}; 
		List list = this.getQuerySupport().find(hql,whe);
		if(list != null && list.size() > 0){
			return (SmsgReceiver)list.get(0);
		}
		return null;
	}
}