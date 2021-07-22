package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.util.Assert;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectOrgClerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.dao.ObjectOrgClerkDao;
import cn.redflagsoft.base.service.ObjectOrgClerkService;

import com.google.common.collect.Maps;

public class ObjectOrgClerkServiceImpl implements ObjectOrgClerkService {
	private static final Log log = LogFactory.getLog(ObjectOrgClerkServiceImpl.class);
	
	private Map<Integer, String> types = Maps.newTreeMap();

	private ObjectOrgClerkDao objectOrgClerkDao;
	
	public ObjectOrgClerkDao getObjectOrgClerkDao() {
		return objectOrgClerkDao;
	}

	public void setObjectOrgClerkDao(ObjectOrgClerkDao objectOrgClerkDao) {
		this.objectOrgClerkDao = objectOrgClerkDao;
	}

	public ObjectOrgClerk saveObject(ObjectOrgClerk objectOrgClerk) {
		int type = objectOrgClerk.getType();
		if(!hasRegistered(type)){
			throw new IllegalArgumentException("关系类型未注册：" + type);
		}
		return objectOrgClerkDao.save(objectOrgClerk);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkManager#registerType(int, java.lang.String)
	 */
	public void registerType(int type, String description) throws IllegalArgumentException {
		if(type <= 100){
			throw new IllegalArgumentException("被注册的关系类型值必须大于100.");
		}
		if(hasRegistered(type)){
			throw new IllegalArgumentException("该关系类型（" + type + "）已经注册，其描述是：" + types.get(type));
		}
		types.put(type, description);
		log.info("注册对象单位人员关联关系：" + type  + " -> " + description);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkManager#hasRegistered(int)
	 */
	public boolean hasRegistered(int type) {
		return types.containsKey(type);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkManager#getRegisteredTypes()
	 */
	public List<Integer> getRegisteredTypes() {
		return Collections.unmodifiableList(new ArrayList<Integer>(types.keySet()));
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkManager#getRegisteredTypeDescription(int)
	 */
	public String getRegisteredTypeDescription(int type) throws IllegalArgumentException {
		if(type <= 100){
			throw new IllegalArgumentException("被注册的关系类型值必须大于100.");
		}
		if(!hasRegistered(type)){
			throw new IllegalArgumentException("关系类型未注册：" + type);
		}
		return types.get(type);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkService#createObject(int, cn.redflagsoft.base.bean.RFSEntityObject, cn.redflagsoft.base.bean.Org, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk, cn.redflagsoft.base.bean.Clerk, java.lang.String, java.lang.String, java.lang.Long, java.util.Date, cn.redflagsoft.base.bean.Clerk, java.lang.String)
	 */
	public ObjectOrgClerk createObject(int type, RFSEntityObject object,
			Org org, Clerk clerk1, Clerk clerk2, Clerk clerk3,
			String accordingTo, String accordingToFileNo,
			Long accordingToFileId, Date changeTime, Clerk recorder, String remark) {
		if(!hasRegistered(type)){
			throw new IllegalArgumentException("关系类型未注册：" + type);
		}
		Assert.notNull(object, "相关对象不能为空");
		
		ObjectOrgClerk obj = new ObjectOrgClerk();
		obj.setType(type);
		obj.setAccordingTo(accordingTo);
		obj.setAccordingToFileId(accordingToFileId);
		obj.setAccordingToFileNo(accordingToFileNo);
		obj.setChangeTime(changeTime != null ? changeTime : new Date());
		if(clerk1 != null){
			obj.setClerk1Id(clerk1.getId());
			obj.setClerk1Name(clerk1.getName());
		}
		if(clerk2 != null){
			obj.setClerk2Id(clerk2.getId());
			obj.setClerk2Name(clerk2.getName());
		}
		if(clerk3 != null){
			obj.setClerk3Id(clerk3.getId());
			obj.setClerk3Name(clerk3.getName());
		}
		if(org != null){
			obj.setOrgId(org.getId());
			obj.setOrgName(org.getAbbr());
		}
		obj.setObjectId(object.getId());
		obj.setObjectType(object.getObjectType());
		if(object instanceof RFSObjectable){
			obj.setObjectName(((RFSObjectable)object).getName());
		}
		if(recorder != null){
			obj.setRecorderId(recorder.getId());
			obj.setRecorderName(recorder.getName());
		}
		obj.setStatus((byte) 1);
		obj.setRemark(remark);

		return objectOrgClerkDao.save(obj);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkService#findObjectOrgClerks(int, int, long)
	 */
	public List<ObjectOrgClerk> findObjectOrgClerks(int type, int objectType, long objectId) {
		ResultFilter filter = buildResultFilter(type, objectType, objectId);
		return objectOrgClerkDao.find(filter);
	}
	private ResultFilter buildResultFilter(int type, int objectType, long objectId){
		Logic logic = Restrictions.logic(Restrictions.eq("type", type))
				.and(Restrictions.eq("objectType", objectType))
				.and(Restrictions.eq("objectId", objectId));
		Order order = Order.desc("changeTime").add(Order.desc("creationTime"));
		ResultFilter filter = new ResultFilter();
		filter.setCriterion(logic);
		filter.setOrder(order);
		return filter;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkService#findObjectOrgClerks(int, cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	public List<ObjectOrgClerk> findObjectOrgClerks(int type, RFSEntityObject object) {
		return findObjectOrgClerks(type, object.getObjectType(), object.getId());
	}
	
	public List<ObjectOrgClerk> findObjectOrgClerks(int type){
		SimpleExpression eq = Restrictions.eq("type", type);
		Order order = Order.desc("changeTime").add(Order.desc("creationTime"));
		ResultFilter filter = new ResultFilter();
		filter.setCriterion(eq);
		filter.setOrder(order);
		return objectOrgClerkDao.find(filter);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectOrgClerkService#getLastObjectOrgClerk(int, int, long)
	 */
	public ObjectOrgClerk getLastObjectOrgClerk(int type, int objectType, long objectId) throws NotFoundException {
		ResultFilter filter = buildResultFilter(type, objectType, objectId);
		filter.setFirstResult(0);
		filter.setMaxResults(1);
		List<ObjectOrgClerk> list = objectOrgClerkDao.find(filter);
		if(list == null || list.isEmpty()){
			String s = String.format("找不到对象单位人员关系对象，关系类型=%s，对象类型=%s，对象ID=%s", type, objectType, objectId);
			throw new NotFoundException(s);
		}
		return list.iterator().next();
	}
	
	public int deleteObject(ObjectOrgClerk objectOrgClerk){
		int delete = objectOrgClerkDao.delete(objectOrgClerk);
		return delete;
	}

	public ObjectOrgClerk getObjectOrgClerkById(long id) {
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1);
		filter.setCriterion(Restrictions.eq("id", id));
		List<ObjectOrgClerk> list = objectOrgClerkDao.find(filter);
		if(!list.isEmpty()) {
			return list.iterator().next();
		}
		return null;
	}
}
