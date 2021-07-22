/*
 * $Id: ObjectHibernateDao.java 5329 2012-02-15 06:06:08Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectRiskGrade;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.support.TypedObjectClassDiscover;

/**
 * @author Alex Lin
 *
 */
public class ObjectHibernateDao<T extends RFSObject> extends AbstractBaseHibernateDao<T, Long> implements ObjectDao<T>{
	private static final Log log = LogFactory.getLog(ObjectHibernateDao.class);
	//private DiscriminatorStrategy discriminatorStrategy = new ClassMetadataDiscriminatorStrategy();
	private TypedObjectClassDiscover typedObjectClassDiscover;// = new EntityClassDiscover(); 
	
	
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.dao.hibernate3.AbstractHibernateDao#setIdGeneratorProvider(org.opoo.apps.id.IdGeneratorProvider)
	 */
	@Override
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
		//super.setIdGeneratorProvider(idGeneratorProvider);
		setIdGenerator(idGeneratorProvider.getIdGenerator("object"));
	}
	
	private Class<T> entityClass;// = RFSObject.class;

	/* (non-Javadoc)
	 * @see org.opoo.ndao.hibernate3.HibernateDao#getEntityClass()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class<T> getEntityClass() {
		if(entityClass == null){
			entityClass = getParameterType();
		}
		if(entityClass == null){
			entityClass = (Class<T>) RFSObject.class;
		}
		
		return entityClass;//RFSObject.class;
	}
	
	@SuppressWarnings("unchecked")
	public void setEntityClassName(String className){
		if(className != null){
			try {
				entityClass = ClassUtils.forName(className, getClass().getClassLoader());
			} catch (Exception e) {
				entityClass = (Class<T>) RFSObject.class;
				//System.out.println(e);
				log.debug(e.getMessage());
			}
		}
	}
	
	protected Class<T> getParameterType(){
		Type genType = getClass().getGenericSuperclass();
        if(genType != null && genType instanceof ParameterizedType){
        	Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        	if(params.length >= 1 && params[0] instanceof Class<?>){
        		@SuppressWarnings("unchecked")
				Class<T> cls = (Class<T>) params[0];
        		if(RFSObject.class.isAssignableFrom(cls)){
        			return cls;
        		}
        	}
        }
        return null;
	}
	
	
	/**
	 * 这个方法有问题，需要在子项目中执行。
	 * 
	 * 根据条件查询对象列表。
	 * 其中objectType与entityName的最用比较类似，有其中之一即可。
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> findObjectByRisk(final Integer objectType, final Byte grade, final Short lifeStage, final Byte managerType, final Long manager, final Byte riskType, final Short year,final int key) {
		Class<?> cls = typedObjectClassDiscover.findClass(objectType);
		if(cls == null){
			//此时查询会增加许多外连接
			cls = RFSObject.class;
		}
		
		String s = "select key from(" +
				"select distinct a.id as key, (select b.grade from risk b where b.object_type=102 and b.objectid=a.active_tasksn) as riskGrade from rfs_object a,TS_PROJECT2 b " +
				" where a.id=b.id ";
		if(key!=1){
			s=s+" and a.id in(select oe.objectid from object_entity oe where oe.tp=200 and oe.obj_type=1001 and entityid=0)";
		}
		//注意，基础工程没有这个表
		if(year != null){
			//s += ", ts_project_plan b";
		}
		
		s += " and a.obj_type=?";
		
		if(year != null){
			//s += " and a.id=b.project and b.years=?";
			s += " and b.plan_years=?";
		}
		
		if(lifeStage != null){
			s += " and a.LIFE_STAGE=?";
		}

		if(managerType != null && manager != null){
			s += " and a.MANAGER_TYPE=? and a.MANAGER=?";
		}
		
		//if(riskType != null){
		//	s += " and b.TYPE=?";
		//}
		
		s += ") where 1=1 ";
		if(grade == 0){
			s += " and (riskGrade=? or riskGrade is null)";
		}else{
			s += " and riskGrade=?";
		}
				
//		String sql = "select key from (select a.ID as key, max(b.GRADE) as riskGrade from RFS_Object a left join Risk b on a.id=b.ref_objectID" +
//				" where a.obj_type=?"; 
//
//
//		if(lifeStage != null){
//			sql += " and a.LIFE_STAGE=?";
//		}
//
//		if(managerType != null && manager != null){
//			sql += " and a.MANAGER_TYPE=? and a.MANAGER=?";
//		}
//		if(riskType != null){
//			sql += " and b.TYPE=?";
//		}
//		sql += " group by a.ID) bb where bb.riskGrade=?";
//		if(grade == 0){
//			sql += " or bb.riskGrade is null";
//		}
		
		final String sql0 = s;
		final List<?> keys = getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql0);
				int i = 0;
				query.setInteger(i++, objectType);
				if(year != null){
					query.setShort(i++, year);
				}
				if(lifeStage != null){
					query.setShort(i++, lifeStage);
				}
				if(managerType != null && manager != null){
					query.setByte(i++, managerType);
					query.setLong(i++, manager);
				}
				//if(riskType != null){
				//	query.setByte(i++, riskType);
				//}
				query.setByte(i++, grade);
				return query.addScalar("key", Hibernate.LONG).list();
			}
		});
		
		//System.out.println(keys);
		if(log.isDebugEnabled()){
			log.debug("查询到keys：" + keys);
		}
		
		
		//当查不到KEY时，也不需要再查询数据表。
		if(keys == null || keys.isEmpty()){
			return new ArrayList<T>();
		}
		
		//Class<?> cls = discriminatorStrategy.getEntityClassByDiscriminatorValue(String.valueOf(objectType), RFSObject.class);
		//if(cls == null){
			//此时查询会增加许多外连接
		//	cls = RFSObject.class;
		//}
		final String qs = "from " + cls.getName() + " where id in (:ids)";
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(qs).setParameterList("ids", keys).list();
			}}
		);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findObjectByRisk3(final Integer objectType, final Byte grade, final Short lifeStage, final Byte managerType, final Long manager, final Byte valueType) {
		String sql = "select * from (select a.ID as key, a.LIFE_STAGE as lifeStage, a.MANAGER as manager, a.MANAGER_TYPE as managerType, max(b.GRADE) as riskGrade from RFS_Object a left join Risk b on a.id=b.objectID" +
				" where a.obj_type=?"; 

		if(lifeStage != null){
			sql += " and a.LIFE_STAGE=?";
		}

		if(managerType != null && manager != null){
			sql += " and a.MANAGER_TYPE=? and a.MANAGER=?";
		}
		if(valueType != null){
			sql += " and b.VALUE_TYPE=?";
		}
		sql += " group by a.ID, a.LIFE_STAGE, a.MANAGER, a.MANAGER_TYPE) bb where bb.riskGrade=?";
		
		final String sql0 = sql;
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql0);
				int i = 0;
				query.setInteger(i++, objectType);
				if(lifeStage != null){
					query.setInteger(i++, lifeStage);
				}
				if(managerType != null && manager != null){
					query.setByte(i++, managerType);
					query.setLong(i++, manager);
				}
				if(valueType != null){
					query.setByte(i++, valueType);
				}
				query.setByte(i++, grade);
				
				
				return query.addScalar("key", Hibernate.LONG)
					.addScalar("lifeStage", Hibernate.SHORT)
					.addScalar("manager", Hibernate.LONG)
					.addScalar("managerType", Hibernate.BYTE)
					.addScalar("riskGrade", Hibernate.BYTE)
					.addScalar("valueType", Hibernate.BYTE)
					.setResultTransformer(Transformers.aliasToBean(RFSObject.class))
					.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<T> findObjectByRisk2(Integer objectType, Short lifeStage, Byte grade, Byte managerType, Long manager, Byte valueType) {
		//String qs = "select distinct a from " + getEntityName() + " a, Risk b where a.id=b.objectID and a.riskGrade=b.grade";
		//Logic logic = Restrictions.logic(Restrictions.eq("b.objectType", objectType));
		
		String qs = "from " + getEntityName() + "a";
		Logic logic = Restrictions.logic(Restrictions.sql("1=1"));
		if(lifeStage != null){
			logic.and(Restrictions.eq("a.lifeStage", lifeStage));
		}
		if(grade != null){
			logic.and(Restrictions.eq("a.riskGrade", grade));
		}
		if(managerType != null && manager != null){
			logic.and(Restrictions.eq("a.managerType", managerType));
			logic.and(Restrictions.eq("a.manager", manager));
		}
		ResultFilter rf = new ResultFilter();
		rf.setCriterion(logic);
		return getQuerySupport().find(qs, rf);
		
		///////////////
//		
//		String qs = "select distinct a from " + getEntityName() + " a, Risk b where a.lifeStage=? and b.grade=?";
//		if(objectType != null) {
//			qs += " and b.objectType=" + objectType;
//		}
//		if(managerType != null) {
//			qs += " and a.managerType=" + managerType;
//		}
//		if(manager != null) {
//			qs += " and a.manager=" + manager;
//		}
//		return getHibernateTemplate().find(qs, new Object[]{manager, lifeStage});
	}
	
	
	public static void main(String[] args){
		ObjectHibernateDao<?> dao = new ObjectHibernateDao<RFSObject>();
		System.out.println(dao.getEntityClass());
		System.out.println(dao.getIdClass());
		System.out.println(dao.getClass().getGenericSuperclass());
	}


	@SuppressWarnings("unchecked")
	public List<ObjectRiskGrade> findObjectGroupByRiskGrade(Integer objectType, Byte valueType) {
		String qs = "select new cn.redflagsoft.base.bean.ObjectRiskGrade(a.id, a.lifeStage, a.manager, a.managerType, max(b.grade)) from RFSObject a, Risk b" +
				" where a.id=b.objectID and a.manager<>0 and a.managerType<>0 and b.refObjectType=?" +
				(valueType != null ? " and b.valueType=?" : "") +
				" group by a.id, a.lifeStage, a.manager, a.managerType";
		Object[] values = valueType != null ? new Object[]{objectType, valueType} : new Object[]{objectType};
		
		//String qs = "select new cn.redflagsoft.base.bean.ObjectRiskGrade(a.id, a.lifeStage, a.manager, a.managerType, max(b.grade)) from RFSObject a, Risk b" +
		//		" where a.id=b.objectID and b.objectType=? group by a.id, a.lifeStage, a.manager, a.managerType";
		//String qs = "from " + getEntityName() + " where riskGrade<>0 and manager<>0 and managerType<>0";
		log.debug("原始的QL: " + qs);
		return getHibernateTemplate().find(qs, values);
	}
	
	/**
	 * 添加查找 ObjectGroup 方法, by ymq 2008-12-30
	 */
	public List<RFSObject> findObjectGroupByRiskGrade(final Integer objectType, final Integer riskType, final Short objectStatus) {
		return findObjectGroupByRiskGrade(objectType, riskType, objectStatus, (Short)null,0,null);
	}
	
	/**
	 * 
	 * @param objectType
	 * @param riskType
	 * @param objectStatus
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RFSObject> findObjectGroupByRiskGrade(final Integer objectType, final Integer riskType, final Short objectStatus, final Short year,int key,Long entityID) {
		if(year != null){
			return findObjectGroupByRiskGradeYear(objectType, riskType, objectStatus, year,key,entityID);
		}
		
		
//		String qs = "select new cn.redflagsoft.base.bean.ObjectRiskGrade(a.id, a.lifeStage, a.manager, a.managerType, max(b.grade)) from " + discover.findClass(objectType).getName() + " a left outer join Risk b" +
//				" on(a.id=b.objectID) where b.refObjectType=?" + (riskType != null ? " and b.type=?" : "") + (objectStatus != null ? " and a.status<?" : "") +
//				" group by a.id, a.lifeStage, a.manager, a.managerType";
		
		
//		final String sql ="select a.ID as key, a.LIFE_STAGE as lifeStage, a.MANAGER as manager, a.MANAGER_TYPE as managerType, max(b.GRADE) as riskGrade from " + discover.findClass(objectType).getName() + " a left join Risk b on(a.id=b.objectID)" +
//				" where b.REF_OBJECT_TYPE=?" + (riskType != null ? " and b.type=?" : "") + (objectStatus != null ? " and a.status<?" : "") +
//				" group by a.ID, a.LIFE_STAGE, a.MANAGER, a.MANAGER_TYPE";
		
		String sql ="select a.ID as key, a.LIFE_STAGE as lifeStage, a.MANAGER as manager, a.MANAGER_TYPE as managerType, (select grade from risk where object_type=102 and objectid=a.active_tasksn) as riskGrade from RFS_OBJECT a,ENTITY_GROUP b " +
		" where (a.MANAGER = b.ENTITYID and b.GROUPID=1) and a.OBJ_TYPE=?";
		if(entityID!=null){
			sql+=" and a.MANAGER="+entityID;
		}
		if(key!=1){
			sql +=" and a.id in(select oe.objectid from object_entity oe where oe.tp=200 and oe.obj_type=1001 and entityid=0)";
		}
		//select id,(select max(grade) from risk where ref_objectid=rfs_object.id) from rfs_object where obj_type=1001 and status<50
				
		if(objectStatus != null){
			sql += " and a.status<?";
		}
		sql+=" order by b.DISPLAY_ORDER Asc";
		log.debug("原始的SQL: " + sql);
		
		/*
		JdbcTemplate template = null;
		
		template.query(new PreparedStatementCreator(){

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				
				return null;
			}}, new RowMapper(){
				public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {
					RFSObject o = new RFSObject();
					o.setManager(manager)
					rs.getInt("");
					return null;
				}});*/
		
		final String sql0 = sql;
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql0);
				int index = 0;
				
				if(riskType != null) {
					query.setInteger(index++, riskType);
				}
				
				query.setInteger(index++, objectType);
				
	
				if(objectStatus != null) {
					query.setShort(index++, objectStatus);
				}
				return query.addScalar("key", Hibernate.LONG)
				.addScalar("lifeStage", Hibernate.SHORT)
				.addScalar("manager", Hibernate.LONG)
				.addScalar("managerType", Hibernate.BYTE)
				.addScalar("riskGrade", Hibernate.BYTE)
				.setResultTransformer(Transformers.aliasToBean(RFSObject.class))
				.list();
			}
		});
		

//		log.debug("原始的QL: " + qs);
//		return getHibernateTemplate().find(qs, objects);
	}
	
	
	
	/**
	 * 这个方法本来不该出现在这里。
	 * 包含有非基础工程的数据查询。
	 * 
	 * @param objectType
	 * @param riskType
	 * @param objectStatus
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RFSObject> findObjectGroupByRiskGradeYear(final Integer objectType, final Integer riskType, final Short objectStatus, final Short year,int key,Long entityID) {
		
		String sql ="select a.ID as key, a.LIFE_STAGE as lifeStage, a.MANAGER as manager, a.MANAGER_TYPE as managerType, (select grade from risk where object_type=102 and objectid=a.active_tasksn) as riskGrade from RFS_OBJECT a, TS_PROJECT2 b,ENTITY_GROUP c " +
		" where (a.MANAGER = c.ENTITYID and c.GROUPID=1) and a.id=b.id and a.OBJ_TYPE=?";
		if(entityID!=null){
			sql+=" and a.MANAGER="+entityID;
		}	
		if(key!=1){
			sql +=" and a.id in(select oe.objectid from object_entity oe where oe.tp=200 and oe.obj_type=1001 and entityid=0)";
		}
		
		//select id,(select max(grade) from risk where ref_objectid=rfs_object.id) from rfs_object where obj_type=1001 and status<50
				
		if(objectStatus != null){
			sql += " and a.status<?";
		}
		
		//sql += " and b.years=?";
		sql += " and b.plan_years=?";
		
		sql+=" order by c.DISPLAY_ORDER Asc";
		
		//log.debug("原始的SQL: " + sql);
		
		/*
		JdbcTemplate template = null;
		
		template.query(new PreparedStatementCreator(){

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				
				return null;
			}}, new RowMapper(){
				public Object mapRow(ResultSet rs, int rowIndex) throws SQLException {
					RFSObject o = new RFSObject();
					o.setManager(manager)
					rs.getInt("");
					return null;
				}});*/
		
		final String sql0 = sql;
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql0);
				int index = 0;
				
				if(riskType != null) {
					query.setInteger(index++, riskType);
				}
				
				query.setInteger(index++, objectType);
				
	
				if(objectStatus != null) {
					query.setShort(index++, objectStatus);
				}
				
				query.setShort(index++, year);
				
				return query.addScalar("key", Hibernate.LONG)
				.addScalar("lifeStage", Hibernate.SHORT)
				.addScalar("manager", Hibernate.LONG)
				.addScalar("managerType", Hibernate.BYTE)
				.addScalar("riskGrade", Hibernate.BYTE)
				.setResultTransformer(Transformers.aliasToBean(RFSObject.class))
				.list();
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Class<?> getEntityClassByObjectType(Integer objectType){
		Class<?> cls = typedObjectClassDiscover.findClass(objectType);
		//Class cls = discriminatorStrategy.getEntityClassByDiscriminatorValue(String.valueOf(objectType), RFSObject.class);
		if(cls == null){
			throw new IllegalArgumentException("找不到对象类型对应的的实体类：" + objectType);
		}
		return cls;
	}

	/* *
	 * @return the discriminatorStrategy
	 * /
	public DiscriminatorStrategy getDiscriminatorStrategy() {
		return discriminatorStrategy;
	}

	/ * *
	 * @param discriminatorStrategy the discriminatorStrategy to set
	 * /
	public void setDiscriminatorStrategy(DiscriminatorStrategy discriminatorStrategy) {
		this.discriminatorStrategy = discriminatorStrategy;
	}*/

	public TypedObjectClassDiscover getTypedObjectClassDiscover() {
		return typedObjectClassDiscover;
	}

	public void setTypedObjectClassDiscover(TypedObjectClassDiscover typedObjectClassDiscover) {
		this.typedObjectClassDiscover = typedObjectClassDiscover;
		//System.out.println(typedObjectClassDiscover + " 设置    。。。。");
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.hibernate3.AbstractBaseHibernateDao#initDao()
	 */
	@Override
	protected void initDao() throws Exception {
		//System.out.println(this + "  检查dao ...........");
		super.initDao();
		//System.out.println(typedObjectClassDiscover);
		Assert.notNull(typedObjectClassDiscover, "TypedObjectClassDiscover is required.");
	}

	@Deprecated
	public void updateObjectsDutyClerkIdAndNameByClerkID(final List<Long> ids, final Long clerkID,final String clerkName) {
		final String qs = "update RFSObject set dutyClerkID=:clerkID,dutyClerkName=:clerkName where id in(:ids)";
	    getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(qs)
				.setLong("clerkID", clerkID)
				.setString("clerkName", clerkName)
				.setParameterList("ids", ids)
				.executeUpdate();
				return null;
			}
	    });
	}
	/**
	 * DUTY_CLERKID
	 * DUTY_CLERK_NAME
DUTYER_LEADER1_ID
DUTYER_LEADER1_NAME
DUTYER_LEADER2_ID
DUTYER_LEADER2_NAME

	 * @param clerk
	 * @param oldName
	 * @param newName
	 * @param objectType
	 */
	protected void clerkNameChange(Clerk clerk, String oldName, final String newName, int objectType) {
		if(log.isDebugEnabled()){
			log.debug("人员信息发生了变化，同步更新RFSObject(" + objectType 
					+ ")中的相关人员：" + oldName + " --> " + newName);
		}
		String sql0 = "update RFS_OBJECT set DUTY_CLERK_NAME=? where DUTY_CLERKID=? and OBJ_TYPE=?";
		String sql1 = "update RFS_OBJECT set DUTYER_LEADER1_NAME=? where DUTYER_LEADER1_ID=? and OBJ_TYPE=?";
		String sql2 = "update RFS_OBJECT set DUTYER_LEADER2_NAME=? where DUTYER_LEADER2_ID=? and OBJ_TYPE=?";
		int rows = executeBatchSQLUpdate(new String[]{sql0, sql1, sql2}, newName, clerk.getId(), objectType);
		
		if(rows > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了ObjectHibernateDao(" + objectType + ")缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("ObjectHibernateDao(" + objectType + ")共更新 ‘" + rows + "’ 个人员名称。");
		}
	}
	
	/**
	 * DUTY_DEPARTMENT_ID
DUTY_DEPARTMENT_NAME
DUTY_ENTITY_ID
DUTY_ENTITY_NAME
	 * @param org
	 * @param oldAbbr
	 * @param newAbbr
	 * @param objectType
	 */
	protected void orgAbbrChange(Org org, String oldAbbr, String newAbbr, int objectType) {
		if(log.isDebugEnabled()){
			log.debug("单位信息发生了变化，同步更新RFSObject(" + objectType 
					+ ")中的相关单位：" + oldAbbr + " --> " + newAbbr);
		}
		
		String sql = "update RFS_OBJECT set DUTY_ENTITY_NAME=? where DUTY_ENTITY_ID=? and OBJ_TYPE=?";
		int rows = executeSQLUpdate(sql, newAbbr, org.getId(), objectType);
		if(rows > 0 && getCache() != null){
			getCache().clear();
			if(log.isDebugEnabled()){
				log.debug("清理了ObjectHibernateDao(" + objectType + ")缓存");
			}
		}
		if(log.isDebugEnabled()){
			log.debug("ObjectHibernateDao(" + objectType + ")共更新 ‘" + rows + "’ 个相关单位。");
		}
	}
}

