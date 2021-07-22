package cn.redflagsoft.base.service;

import java.util.Date;
import java.util.List;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectOrgClerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * 对象单位人员关系服务类。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ObjectOrgClerkService extends ObjectOrgClerkManager{
	
	/**
	 * @param objectOrgClerk
	 * @return
	 */
	ObjectOrgClerk saveObject(ObjectOrgClerk objectOrgClerk);

	/**
	 * 创建对象单位人员关系对象。
	 * 
	 * @param type 关系类型
	 * @param object 对象
	 * @param org 单位
	 * @param clerk1 人员1
	 * @param clerk2 人员2
	 * @param clerk3 人员3
	 * @param accordingTo 依据
	 * @param accordingToFileNo 依据文号
	 * @param accordingToFileId 依据文件ID
	 * @param changeTime 变更时间、发生时间、记录时间
	 * @param recorder 记录提交者
	 * @param remark 备注
	 * @return 对象单位人员关系对象
	 */
	ObjectOrgClerk createObject(int type, RFSEntityObject object, 
			Org org, Clerk clerk1, Clerk clerk2, Clerk clerk3,
			String accordingTo, String accordingToFileNo, Long accordingToFileId,
			Date changeTime, Clerk recorder, String remark);

	/**
	 * 查找指定对象的对应关系的所有关系对象集合，按变更时间倒序排列。
	 * 
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @param objectId 对象ID
	 * @return 关联对象集合
	 */
	List<ObjectOrgClerk> findObjectOrgClerks(int type, int objectType, long objectId);
	
	/**
	 * 查找指定对象的对应关系的所有关系对象集合，按变更时间倒序排列。
	 * 
	 * @see #findObjectOrgClerks(int, int, long)
	 * @param type 关系类型
	 * @param object 对象
	 * @return 关联对象集合
	 */
	List<ObjectOrgClerk> findObjectOrgClerks(int type, RFSEntityObject object);
	
	/**
	 * 查找指定对象的对应关系的所有关系对象的最新一条记录，按变更时间倒序排列。
	 * 
	 * @param type 关系类型
	 * @param objectType 对象类型
	 * @param objectId 对象ID
	 * @return
	 * @throws 无记录时抛出错误
	 */
	ObjectOrgClerk getLastObjectOrgClerk(int type, int objectType, long objectId) throws NotFoundException;
	
	int deleteObject(ObjectOrgClerk objectOrgClerk);
	
	List<ObjectOrgClerk> findObjectOrgClerks(int type);

	
	/**
	 * 通过id查找ObkectOrgClerk对象
	 */
	ObjectOrgClerk getObjectOrgClerkById(long id);
}
