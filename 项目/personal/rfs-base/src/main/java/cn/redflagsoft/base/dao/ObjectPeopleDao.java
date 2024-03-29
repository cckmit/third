/*
 * $Id: ObjectPeopleDao.java 4615 2011-08-21 07:10:37Z lcj $
 * ObjectPeopleDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.criterion.Order;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectPeople;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * @author mwx
 *
 */
public interface ObjectPeopleDao extends Dao<ObjectPeople,Long>{
	/**
	 * 根据人查找对象
	 * @param peopleId
	 * @param type
	 * @return
	 */
	List<RFSObject> findObjectsByPeopleId(Long peopleId, int type);
	
	/**
	 * 根据人查找指定类型的对象。
	 * 
	 * <p>
	 * select a from objectClass a, ObjectPeople b where a.id=b.objectID and b.objectType=?
	 * and b.peopleID=? and b.type=?
	 * </p>
	 * 
	 * @param <T> 业务对象
	 * @param objectClass 业务对象类
	 * @param objectType 业务对象类型
	 * @param peopleId 人员id
	 * @param type 关系类型
	 * @return
	 */
	<T extends RFSObject> List<T> findObjectsByPeopleId(Class<T> objectClass, int objectType, long peopleId, int type, Order order);
	
	
	/**
	 * 根据对象查找人
	 * @param objectId
	 * @param type
	 * @return
	 */
	List<Clerk> findClerksByObjectId(Long objectId, int type);
	
	/**
	 * 根据条件获取关系数量。
	 * 
	 * @param objectId
	 * @param peopleId
	 * @param type
	 * @param objectType
	 * @return
	 */
	int getObjectPeopleCount(Long objectId, Long peopleId,int type,int objectType);
    
	/**
	 * 根据条件删除关系。
	 * @param objectId
	 * @param peopleId
	 * @param type
	 * @param objectType
	 * @return 删除的关系数量
	 */
	int removeObjectPeople(final Long objectId, final Long peopleId, final int type,final int objectType);

	/**
	 * 
	 * @param peopleId
	 * @param type
	 * @return
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type);
	
	/**
	 * 
	 * @param peopleId
	 * @param type
	 * @param objectType
	 * @return
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type, int objectType);
	
	/**
	 * 
	 * @param objectId
	 * @param type
	 * @return
	 */
	List<Long> findPeopleIdsByObjectId(Long objectId, int type);
}
