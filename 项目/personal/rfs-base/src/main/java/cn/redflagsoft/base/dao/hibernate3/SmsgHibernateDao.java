/*
 * $Id: SmsgHibernateDao.java 5933 2012-07-21 09:50:00Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.dao.SmsgDao;

/**
 * @author py
 *
 */
public class SmsgHibernateDao extends ObjectHibernateDao<Smsg> implements SmsgDao{
	
//	@SuppressWarnings("unchecked")
//	public List<Object> smsgRead(Clerk clerk,Long smsgId){
//		String hql = "select new map(s.frId as frId,s.frName as frName," +
//				 "s.frAddr as frAddr, s.title as title,s.content as content," +
//				 "s.keyword as keyword,r.toId as toId,r.toName as toName,r.toAddr as toAddr,r.id as smsgReceiverId) " +
//				 "from Smsg s,SmsgReceiver r where s.id = r.smsgId and r.toId =? and r.smsgId =? ";
//		Object whe[] = new Object[]{clerk.getId(),smsgId}; 
//		return this.getQuerySupport().find(hql, whe);
//	}
//	
//	
//	@SuppressWarnings("rawtypes")
//	public Smsg smsgReadForClerk(Clerk clerk,Long smsgId){
//		String hql = "select s from Smsg s,SmsgReceiver r where s.id = r.smsgId and r.toId =? and r.smsgId =? ";
//		Object whe[] = new Object[]{clerk.getId(),smsgId}; 
//		List list = this.getQuerySupport().find(hql,whe);
//		if(list != null && list.size() > 0){
//			return (Smsg)list.get(0);
//		}
//		return null;
//	}
	
	@SuppressWarnings("unchecked")
	public int getUserSmsgCount(long userId, byte msgKind, byte readStatus){
		String qs = "select count(*) from Smsg s, SmsgReceiver r where s.id=r.smsgId " +
				"and s.kind=? and r.toId=? and r.sendStatus=? " +
				"and r.status=? and s.bizStatus=? " +
				"and (s.expirationTime is null or s.expirationTime>=sysdate())";
		List<Number> countList;
		if(readStatus == SmsgReceiver.READ_STATUS_已读 || readStatus == SmsgReceiver.READ_STATUS_未读){
			qs += " and r.readStatus=?";
			countList = getHibernateTemplate().find(qs, new Object[]{msgKind, userId, SmsgReceiver.SEND_STATUS_已发送, 
					SmsgReceiver.STATUS_有效, Smsg.BIZ_STATUS_已发布, readStatus});
		}else{
			countList = getHibernateTemplate().find(qs, new Object[]{msgKind, userId, SmsgReceiver.SEND_STATUS_已发送,
					SmsgReceiver.STATUS_有效, Smsg.BIZ_STATUS_已发布});
		}
		return countList.iterator().next().intValue();
	}
	
	/**
	 * 获取指定用户收到的指定类型消息的数量，按阅读状态进行分组。
	 * 
	 * @param userId
	 * @param msgKind
	 * @return key 阅读状态，value 消息数量
	 */
	public Map<Byte, Integer> getUserSmsgCount(long userId, byte msgKind){
		/**
		 * select s from Smsg s,SmsgReceiver r where s.id=r.smsgId \
						and r.status = 1 and r.sendStatus = 1 and s.bizStatus = 9 \
						and (s.expirationTime is null or s.expirationTime >= sysdate())
		 */
		
		String qs = "select r.readStatus, count(*) from Smsg s, SmsgReceiver r where s.id=r.smsgId ";
		if(msgKind >= 0){
			qs += "and s.kind=? ";
		}
		qs += "and r.toId=? and r.sendStatus=? " +
				"and r.status=? and s.bizStatus=? " +
				"and (s.expirationTime is null or s.expirationTime>=?)" +
				"group by r.readStatus";
		Object[] values = null;
		if(msgKind >= 0){
			values = new Object[]{msgKind, userId, SmsgReceiver.SEND_STATUS_已发送, SmsgReceiver.STATUS_有效, Smsg.BIZ_STATUS_已发布, new Date()};
		}else{
			values = new Object[]{userId, SmsgReceiver.SEND_STATUS_已发送, SmsgReceiver.STATUS_有效, Smsg.BIZ_STATUS_已发布, new Date()};
		}
		
		Map<Byte, Integer> map = Maps.newHashMap();
		@SuppressWarnings("unchecked")
		List<Object[]> list = getHibernateTemplate().find(qs, values);
		for(Object[] objs: list){
			byte readStatus = ((Number) objs[0]).byteValue();
			int count = ((Number) objs[1]).intValue();
			map.put(readStatus, count);
		}
//		if(!map.containsKey(SmsgReceiver.READ_STATUS_已读)){
//			map.put(SmsgReceiver.READ_STATUS_已读, 0);
//		}
//		if(!map.containsKey(SmsgReceiver.READ_STATUS_未读)){
//			map.put(SmsgReceiver.READ_STATUS_未读, 0);
//		}
		return map;
	}
}