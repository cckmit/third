package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectPeople;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.vo.BatchUpdateResult;


/**
 * 业务对象与人员之间关系的服务接口定义。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ObjectPeopleService {
	/**
	 * 创建关系。
	 * @param op
	 * @return
	 */
	ObjectPeople createObjectPeople(ObjectPeople op);
	
	
	/**
	 * 创建一个关联对象。
	 * @param objectId
	 * @param clerkID
	 * @param type
	 * @param objectType
	 * @return
	 */
	ObjectPeople createObjectPeople(Long objectId, Long clerkID,	int type, int objectType);
	
	/**
	 * 根据人查找对象
	 * @param peopleId
	 * @param type
	 * @return
	 */
	List<RFSObject> findObjectsByPeopleId(Long peopleId, int type);
	
	/**
	 * 根据对象查找人
	 * @param objectId
	 * @param type
	 * @return
	 */
	List<Clerk> findClerksByObjectId(Long objectId, int type);
	
	/**
	 * 根据业务对象、人员和关系类型查询关系的数量。
	 * 
	 * @param objectId 业务对象id
	 * @param peopleId 人员ID
	 * @param type 关系的类型
	 * @param objectType 业务对象的类型
	 * @return
	 */
	int getObjectPeopleCount(Long objectId, Long peopleId, int type, int objectType);
    
	/**
	 * 根据业务对象、人员和关系类型删除关系。
	 * 
	 * @param objectId 业务对象id
	 * @param clerkID 人员id
	 * @param type 关系类型
	 * @param objectType 业务对象的类型
	 */
	void removeObjectPeople(Long objectId, Long clerkID, int type, int objectType);
	
	/**
	 * 添加指定人对指定对象的关注。
	 * 这是 {@link #createObjectPeople(ObjectPeople)}方法的具体用例。
	 * @param object 业务对象
	 * @param clerk 人员
	 * @return 返回添加成功的关系的数量，如果关系已经存在，返回0
	 * @see ObjectPeople#TYPE_FOCUS
	 * @see #createObjectPeople(ObjectPeople)
	 */
	int addObjectFocus(RFSObject object, Clerk clerk);
	
	/**
	 * 删除指定人对指定对象的关注。
	 * 这是 {@link #removeObjectPeople(Long, Long, short, short)} 方法的具体用例。
	 * @param object 业务对象
	 * @param clerk 人员
	 * @return 返回删除成功的关系的数量，如果关系已经存在，返回0
	 * @see ObjectPeople#TYPE_FOCUS
	 * @see #removeObjectPeople(Long, Long, short, short)
	 */
	 int removeObjectFocus(RFSObject object, Clerk clerk);
	 
	 
	/**
	 * 根据人员ID查找对象id集合。
	 * 
	 * @param peopleId 人员id
	 * @param type 关系类型
	 * @return 对象id集合
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type);

	/**
	 * 根据对象id查找人员id集合
	 * @param objectId 对象id
	 * @param type 关系类型
	 * @return 人员id集合
	 */
	List<Long> findPeopleIdsByObjectId(Long objectId, int type);
	
	/**
	 * 为指定ID的人员批量设置对象。
	 * 如果对象ID集合为空，则清空指定人指定类型的关联关系。
	 * 
	 * @param peopleId 人员ID
	 * @param objectIds 对象ID集合
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @return 批量设置结果
	 */
	BatchUpdateResult setObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
	
	/**
	 * 根据人员ID查找对象ID集合。
	 * 
	 * @param peopleId 人员ID
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @return 对象ID集合
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type, int objectType);

	/**
	 * 移除指定的项（集合）。
	 * 
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	int removeObjectsOfPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
	
	
	/**
	 * 为指定ID的人员批量添加对象。
	 * 
	 * <pre>
	 * 要判断指定的关联关系是否已经存在，有两种方式实现这个检查：
	 * 1. 判断每个关系是否存在，如果存在则跳过，如果不存在则新建关系。对每个关系都会有一个查询。
	 * 	 	数据库操作次数为 n + x， 其中(x <= n)，即 n~2n 次数据操作 。
	 * 2. 先删除要添加的所有关系（可能没有），然后再依次新建关系。数据库操作为 n + 1 次。
	 * 
	 * 通常情况下，第二种更优化。
	 * 
	 * 使用第一种时，BatchUpdateResult的updateRows记录已存在的关联数量，createRows记录新建的关联数量。
	 * 使用第二种时，BatchUpdateResult的deleteRows记录删除的关联数量，createRows记录新建的关联数量 
	 * （插入的关联数量 - 删除的记录数）。
	 * </pre>
	 *
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	BatchUpdateResult addObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
}
