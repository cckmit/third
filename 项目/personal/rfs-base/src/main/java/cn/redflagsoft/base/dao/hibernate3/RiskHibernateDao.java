/*
 * $Id: RiskHibernateDao.java 6228 2013-05-30 06:39:22Z lcj $
 * RiskHibernateDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.beans.PropertyDescriptor;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.dao.RiskDao;
import cn.redflagsoft.base.event2.ObjectNameChangeListener;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;
import cn.redflagsoft.base.util.BatchHelper;
import cn.redflagsoft.base.util.EqualsUtils;
import cn.redflagsoft.base.util.RiskMonitorableUtils;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class RiskHibernateDao extends AbstractBaseHibernateDao<Risk,Long> implements RiskDao,ObjectNameChangeListener{
	private static final Log log = LogFactory.getLog(RiskHibernateDao.class);
//	private EntityClassDiscover classDiscover = new EntityClassDiscover();
	private EntityObjectLoader entityObjectLoader;
	
	/**
	 * @return the entityObjectLoader
	 */
	public synchronized EntityObjectLoader getEntityObjectLoader() {
		if(entityObjectLoader == null){
			entityObjectLoader = Application.getContext().get("entityObjectLoader", EntityObjectLoader.class);
		}
		return entityObjectLoader;
	}

	@Override
	protected void initDao() throws Exception {
		super.initDao();
		//classDiscover.setSessionFactory(getSessionFactory());
		//初始化载入缓存
		if(AppsGlobals.getProperty("risk.loadInfoCacheOnStartup", false)){
			loadRiskAndPutIntoCache();
		}
	}

	/**
	 * 
	 */
	void loadRiskAndPutIntoCache() {
		if(isEnableCache() && getCache() != null){
			
			//将所有在用的 risk 按每批次 30 条的速度缓存起来
			ResultFilter filter = ResultFilter.createPageableResultFilter(0, 30);
			filter.setCriterion(Restrictions.logic(Restrictions.eq("ruleType", RiskRule.RULE_TYPE_监察))
					.and(Restrictions.eq("status", Risk.STATUS_ON_USE)));
			
			ObjectFinder<Risk> finder = new ObjectFinder<Risk>(){
				public int getObjectCount(ResultFilter rf) {
					return getQuerySupport().getInt("select count(*) from Risk r", rf.getCriterion());
				}
				public List<Risk> findObjects(ResultFilter rf) {
					return getQuerySupport().find("select r from Risk r ", rf);
				}
			};
			
			ObjectHandler<Risk, Object> handler = new ObjectHandler<Risk, Object>(){
				public Object handle(Risk risk) {
					getCache().put(buildCacheKey(risk.getId()), risk);
					return null;
				}
			};
			
			BatchHelper.batchHandleObject(finder, handler, filter, false);
		}
	}

	/**
	 * 注意:本方法使用SQL聚合函数查询,元素中存储的是二维Object[],字段包含[ObjectId, Grade]
	 */
	public List<Object[]> findRiskByType(int objectType) {
		String qs = "select a.objectID, max(a.grade) from Risk a where a.objectType=? group by a.objectID";
		return getQuerySupport().find(qs, objectType);
	}

	public List<RFSObject> findObjectWithRisk(int objectType,
			short lifeStage, byte grade, Long manager) {
		String qs = "select distinct a from RFSObject a,Risk b where a.manager=? and a.lifeStage=? and b.objectType=? and b.grade=?";
		return getHibernateTemplate().find(qs, new Object[]{manager, lifeStage, objectType, grade});
	}

	/**
	 * 注: 本方法采用反谢调用,用于修改风险监控 objectAttr, 根据 objectId. sql 如下
	 * update tablename a set a.objectAttr=? where a.id=?
	 * @param sql
	 * @param objectId
	 * @param objectAttr
	 * @return
	 */
	public int modfilyObjectAttr(String sql, Long objectId, String objectAttr) {
		return getQuerySupport().executeUpdate(sql, new Object[]{objectAttr, objectId});
	}
	
	/**
	 * 回调更新被监控对象的的被监控属性。
	 */
	public void updateObjectAttr(Long objectId, int objectType, String objectAttr, Object value) {
		if (value == null) {
			log.warn("updateObjectAttr 时修改的objectAttr值  is null ... ");
			return;
		}
		/*
		ClassMetadata meta = classDiscover.findClassMetadata(objectType);
		if(meta != null) {
			Type type = meta.getPropertyType(objectAttr);
			String classname = meta.getEntityName();
			String idp = meta.getIdentifierPropertyName();
			String sql = "update " + classname + " set " + objectAttr + "=? where " + idp + "=?";
			log.debug("QueryString: " + sql);
			getQuerySupport().executeUpdate(sql, new Object[]{convertToValue(value, type.getReturnedClass()), objectId});
			
			
		}else{
			log.debug("找不到对象objectType=" + objectType);
		}*/
		
		EntityObjectLoader loader = getEntityObjectLoader();
		if(loader == null){
			log.warn("updateObjectAttr时找不到EntityObjectLoader");
			return;
		}
		if(log.isDebugEnabled()){
			log.debug("Load risk object(type=" + objectType + ", id=" + objectId + ")");
		}
		RFSEntityObject object = loader.getEntityObject(objectType, objectId);
		if(object != null){
			try {
				PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(object, objectAttr);
				Class<?> type = descriptor.getPropertyType();
				Object val = RiskMonitorableUtils.convertToValue(value, type);
				Object oldValue = descriptor.getReadMethod().invoke(object);
				//如果值变化了才修改
				if(!EqualsUtils.equals(val, oldValue)){
					descriptor.getWriteMethod().invoke(object, val);
					getHibernateTemplate().merge(object);
					if(log.isDebugEnabled()){
						log.debug(String.format("更新被监控对象‘%s(%s)’的属性‘%s’的值为‘%s’。", 
								object, objectType, objectAttr, val));
					}
				}
			} catch (Exception e) {
				log.error("更新被监控对象被监控属性值出错", e);
			}
		}
	}
	
//	private Object convertToValue(Object value, Class<?> targetClass){
//		return XWorkConverter.getInstance().convertValue(new HashMap<Object,Object>(), value.toString(), targetClass);
//	}

	
	public List<Risk> findTimeRisks() {
		String hql = "from Risk where status=? and pause=? and valueSource=?";
		return getHibernateTemplate().find(hql, 
				new Object[]{Risk.STATUS_ON_USE, 
				Risk.PAUSE_NO, 
				RiskRule.VALUE_SOURCE_TIMER_ADD});
	}

	public List<Risk> findRiskByDutyerId(Long dutyerId) {
		String hql = "select a from Risk a where a.objectType in(102,1001,1002,1003,1004,1005) and dutyerType=1" +
				" and dutyerID=? order by objectType,refType,objectID";
		return getHibernateTemplate().find(hql, dutyerId);
	}
	
	public List<Map<String,Object>> findRiskByTaskTypesAndObjectId(Long objectID) {
		String hql = "select new map(a.name as name,a.grade as grade,a.dutyerID as dutyerID,b.abbr as abbr) " +
		" from Risk a,Org b where a.dutyerID=b.id and a.refType in(select c.type from Task c,ObjectTask d " +
		" where c.sn=d.taskSN and d.objectID=?) and a.refObjectId=?";
		return getQuerySupport().find(hql, new Object[]{objectID, objectID});
	}

	public List<Risk> findRiskByObjectId(final Long objectId, final int objectType) {
		final String sql = "select a.id as id,a.name as name,a.val as value,a.scale_Value as scaleValue,a.scale_Value1 as scaleValue1,a.scale_Value2 as scaleValue2," +
				"a.scale_Value3 as scaleValue3,a.scale_Value4 as scaleValue4,a.scale_Value5 as scaleValue5,a.scale_Value6 as scaleValue6," +
				"grade as grade,a.dutyerID as dutyerID,a.objectID as objectID,a.object_Type as objectType," +
				"a.dutyer_Type as dutyerType,a.jural_Limit as juralLimit,a.conclusion as conclusion,a.value_Sign as valueSign,a.value_Type as valueType," +
				"a.value_Unit as valueUnit,a.value_Source as valueSource,a.ref_Type as refType,a.ref_Object_Type as refObjectType,a.ref_ObjectId as refObjectId," +
				"b.term as refTaskTypeName, c.abbr as dutyerName,a.ruleid as ruleID from risk a left join glossary b on b.code=a.ref_Type and b.category=112 left join org c on c.id=a.dutyerid where ((a.object_Type=? and a.objectId=?) or (a.object_Type=102 and ref_Object_Type=? and ref_ObjectId=?)) order by object_type";
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setInteger(0, objectType);
				query.setLong(1, objectId);
				query.setInteger(2, objectType);
				query.setLong(3, objectId);
				
				query.addScalar("id", Hibernate.LONG);
				query.addScalar("name", Hibernate.STRING);
				query.addScalar("value", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue1", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue2", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue3", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue4", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue5", Hibernate.BIG_DECIMAL);
				query.addScalar("scaleValue6", Hibernate.BIG_DECIMAL);
				query.addScalar("grade", Hibernate.BYTE);
				query.addScalar("dutyerID", Hibernate.LONG);
				query.addScalar("objectID", Hibernate.LONG);
				query.addScalar("objectType", Hibernate.SHORT);
				query.addScalar("dutyerType", Hibernate.SHORT);
				query.addScalar("valueSign", Hibernate.BYTE);
				query.addScalar("valueType", Hibernate.BYTE);
				query.addScalar("valueUnit", Hibernate.BYTE);
				query.addScalar("valueSource", Hibernate.BYTE);
				query.addScalar("refType", Hibernate.SHORT);
				query.addScalar("refObjectType", Hibernate.SHORT);
				query.addScalar("refObjectId", Hibernate.LONG);
				query.addScalar("refTaskTypeName", Hibernate.STRING);
				query.addScalar("dutyerName", Hibernate.STRING);
				query.addScalar("ruleID", Hibernate.LONG);
				
				query.setResultTransformer(Transformers.aliasToBean(Risk.class));
				return query.list();
				
				
	
			}			
		});
		
		//String hql = "select a, b.abbr from Risk a, Org b where a.dutyerID=b.id and ((a.objectType=? and a.objectId=?) or (a.objectType=102 and refObjectType=? and refObjectId=?))";
		//List list = getHibernateTemplate().find(hql, new Object[]{objectId,objectId});
	
	}
	public List<Risk> findTaskRiskByObjectIdAndTaskType(Long objectId, final int objectType,Integer... taskTypes) {		
		if(taskTypes == null || taskTypes.length == 0) return null;	
		 Criterion c = Restrictions.logic(
				 Restrictions.eq("objectType", 102)).and(
				 Restrictions.eq("refObjectType", objectType)).and(
	    		 Restrictions.eq("refObjectId", objectId)).and(
	    		 Restrictions.in("refType", taskTypes)
	    		 );
		 ResultFilter rf = ResultFilter.createEmptyResultFilter();
		 rf.setCriterion(c);		
		 rf.setOrder(Order.asc("refType"));
		return this.find(rf);
					
	}
	
	public Risk get(Long id){
		//log.debug("Get risk: " + id);
		return super.get(id);
	}
	
	@SuppressWarnings("deprecation")
	public Risk update(Risk risk){
		//FIXME 更新被监控对象的被监控属性，可以在其他位置调用，保留此处代码一段时间
		//以保留兼容
		if(risk.isValueChanged() && risk.getObjectAttr() != null){
			updateObjectAttr(risk.getObjectID(), risk.getObjectType(), risk.getObjectAttr(), risk.getValue());
			//updateObjectAttr(objectId, objectType, objectAttr, value)
		}
		return super.update(risk);
	}

	public List<Object[]> findMatterCollect() {
		String hql = "select b.matterID as matterID,b.matterName as matterName,a.dutyerID as dutyerID,count(*) as CF from Risk a,TaskMatter b where a.objectID=b.taskSN and a.objectType=102 group by b.matterID,b.matterName,a.dutyerID order by b.matterID Asc,a.dutyerID Asc";
		return getQuerySupport().find(hql);
	}

	public List<Object[]> getValueAndScaleValueCountGroupByDutyerID() {
		String hql = "select dutyerID,sum(value) as value,sum(scaleValue) as scale from Risk group by dutyerID order by dutyerID Asc";
		return getQuerySupport().find(hql);
	
	}

	public void deleteRiskByTaskSn(final Long taskSN) {	
		final String hql = "delete from Risk where objectType=? and objectID=? ";
	    getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(hql)
				.setInteger(0, ObjectTypes.TASK)
				.setLong(1, taskSN)
				.executeUpdate();
				return null;
			}
	    });
	    //清除缓存
	    if(getCache() != null){
	    	getCache().clear();
	    }
	}
	
	public List<Risk> findRiskByObjectAndObjectType(Long objectID,Integer objectType){
		Criterion c = Restrictions.logic(
				 Restrictions.eq("objectType", objectType)).and(		
	    		 Restrictions.eq("objectID", objectID));
		 ResultFilter rf = ResultFilter.createEmptyResultFilter();
		 rf.setCriterion(c);		
		 rf.setOrder(Order.asc("refType"));
		return this.find(rf);
	}

	public void riskInvalid(RFSObject rfsObject) {
		final int objectType = rfsObject.getObjectType();
		final Long objectId = rfsObject.getId();
		final String sql= "insert into risk_invalid (select * from risk r where r.ref_objectid=? and r.ref_object_type=?)";

		int execute = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query q = session.createSQLQuery(sql).setLong(0, objectId).setInteger(1, objectType);
				return q.executeUpdate();
			}
		});
		

		SimpleExpression eq = Restrictions.eq("refObjectId",objectId);
		SimpleExpression eq2 = Restrictions.eq("refObjectType", objectType);
		int rows2 = remove(Restrictions.logic(eq).and(eq2));
		
		String s = String.format("对象(type=%s, id=%s)作废，转移Risk数据%s项到risk_invalid，删除%s项。", objectType, objectId, execute, rows2);
		log.info(s);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.event2.ObjectNameChangeListener#objectNameChange(cn.redflagsoft.base.bean.RFSObjectable, java.lang.String, java.lang.String)
	 */
	public void objectNameChange(RFSObjectable object, String oldValue,	String newValue) {
		log.debug("对象名称发生了变化，同步更新Risk：" + oldValue + " --> " + newValue);
		
		String sql = "update Risk set REF_OBJECT_NAME=? where REF_OBJECTID=? and REF_OBJECT_TYPE=?";
		int rows = executeSQLUpdate(sql, newValue, object.getId(), object.getObjectType());
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			log.debug("清理了 Risk 缓存");
		}
		log.debug("共更新Risk 的相关对象名称 ‘" + rows + "’个");
	}
	

	
	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.CachedHibernateDao#setCache(org.opoo.cache.Cache)
	 */
	@Override
	public void setCache(Cache<String, Risk> cache) {
		super.setCache(new RiskCache(cache));
		log.debug("Using RiskCache ...");
	}
	
	

	private class RiskCache extends CacheWrapper<String, Risk>{
		/**
		 * @param cache
		 */
		public RiskCache(Cache<String, Risk> cache) {
			super(cache);
		}

		/* (non-Javadoc)
		 * @see org.opoo.cache.CacheWrapper#clear()
		 */
		@Override
		public void clear() {
			log.info("Clear risk cache ...");
			super.clear();
		}
	}
}