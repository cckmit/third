/*
 * $Id: AbstractBaseHibernateDao.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao.hibernate3;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.opoo.apps.Labelable;
import org.opoo.apps.dao.hibernate3.AbstractAppsHibernateDao;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.ndao.Domain;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;

import cn.redflagsoft.base.bean.LabelDataBean;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.VersionLogable;
import cn.redflagsoft.base.codegenerator.CodeGenerator;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.codegenerator.Codeable;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.BusinessExceptionManager;

/**
 * 
 * @author Alex Lin
 *
 * @param <T> 实体类类型
 * @param <K> 实体类的主键类型
 */
public class AbstractBaseHibernateDao<T extends Domain<K>, K extends Serializable> extends AbstractAppsHibernateDao<T, K> 
		//implements ApplicationContextAware
{
	private static final Log log = LogFactory.getLog(AbstractBaseHibernateDao.class);
	//private ApplicationContext applicationContext;
	private CodeGenerator codeGenerator;
	private CodeGeneratorProvider codeGeneratorProvider;
	private BusinessExceptionManager businessExceptionManager;
	/**
	 * 实体别名。
	 */
	private String entityAlias = "a";
	
	
	@Override
	public T save(T entity){
		if(entity instanceof VersionLogable){
			//todo:自定填入当前登录用户
			//((VersionableBean)entity).setCreator(null);
			User user = UserHolder.getNullableUser();
			if(user != null && !AuthUtils.isInternalUser(user)){
				((VersionLogable)entity).setCreator(user.getUserId());
			}
		}
		
		if(entity instanceof Codeable){
			populateCodeFor((Codeable)entity);
		}
		return super.save(entity);
	}
	
	private void populateCodeFor(Codeable codeable){
		if(codeable.getCode() == null){
			if(codeGenerator != null){
				codeable.setCode(codeGenerator.generateCode(codeable));
			}else if(codeGeneratorProvider != null){
				codeable.setCode(codeGeneratorProvider.generateCode(codeable));
			}
		}
	}
	

	@Override
	public T update(T entity) {
		if(entity instanceof VersionLogable){
			//todo:自定填入当前登录用户
			//((VersionableBean)entity).setModifier(null);
			
			User user = UserHolder.getNullableUser();
			if(user != null && !AuthUtils.isInternalUser(user)){
				((VersionLogable)entity).setModifier(user.getUserId());
			}
		}
		return super.update(entity);
	}
	
	

	public List<Labelable> findLabelables(ResultFilter rf) {
		String qs = buildQueryLabelDataBeanQueryString();
		return getQuerySupport().find(qs, rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.LabelDataBeanDao#findLabelDataBeans(org.opoo.ndao.support.ResultFilter)
	 */
	public List<LabelDataBean> findLabelDataBeans(ResultFilter rf) {
		String qs = buildQueryLabelDataBeanQueryString();
		return getQuerySupport().find(qs, rf);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.dao.LabelDataBeanDao#findLabelDataBeans()
	 */
	@SuppressWarnings("unchecked")
	public List<LabelDataBean> findLabelDataBeans() {
		String qs = buildQueryLabelDataBeanQueryString();
		return getHibernateTemplate().find(qs);
	}
	
	protected String buildQueryLabelDataBeanQueryString(){
		return "select new cn.redflagsoft.base.bean.DefaultLabelDataBean("
				+ getLabelProperty() + ", " + getDataProperty() + ") from "
				+ getEntityName();
	}
	
	
	/**
	 * label属性名。
	 * 默认为name。
	 * @return label属性名
	 */
	protected String getLabelProperty(){
		return "name";
	}
	
	/**
	 * data属性名。
	 * 默认为id。
	 * @return data属性名
	 */
	protected String getDataProperty(){
		return getIdProperty();
	}

	/**
	 * @return the codeGenerator
	 */
	public CodeGenerator getCodeGenerator() {
		return codeGenerator;
	}

	/**
	 * @param codeGenerator the codeGenerator to set
	 */
	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	/**
	 * @return the codeGeneratorProvider
	 */
	public CodeGeneratorProvider getCodeGeneratorProvider() {
		return codeGeneratorProvider;
	}

	/**
	 * @param codeGeneratorProvider the codeGeneratorProvider to set
	 */
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}

	/* (non-Javadoc)
	 *
	 */
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		//applicationContext = arg0;
	}

	/* (non-Javadoc)
	 *
	 */
	@Override
	protected void initDao() throws Exception {
		super.initDao();
		//if(codeGenerator == null && codeGeneratorProvider == null && applicationContext != null){
		//	codeGeneratorProvider = (CodeGeneratorProvider) applicationContext.getBean("codeGeneratorProvider");
		//}
	}
	
	/**
	 * 
	 * @param queryStrings
	 * @param values
	 * @return
	 */
	protected int executeBatchUpdate(String[] queryStrings, Object[] values){
		int rows = 0;
		for(String qs: queryStrings){
			rows += getQuerySupport().executeUpdate(qs, values);
		}
		return rows;
	}
	
	protected int updateNameById(String entityName, String idField, String nameField, Object idValue, Object nameValue){
		String qs = "update " + entityName + " set " + nameField + "=? where " + idField + "=?";
		int rows = getQuerySupport().executeUpdate(qs, new Object[]{nameValue, idValue});
		Log log = LogFactory.getLog(getClass());
		if(getCache() != null){
			getCache().clear();
		}
		if(log.isDebugEnabled()){
			log.debug("更新了 " + rows + " 条数据：update " + entityName
					+ " set " + nameField + "=" + nameValue + " where " + idField + "=" + idValue);
		}
		return rows;
	}
	
	protected int updateNameById(String idField, String nameField, Object idValue, Object nameValue){
		return updateNameById(getEntityName(), idField, nameField, idValue, nameValue);
	}
	
	
	/**
	 * 执行SQL更新。
	 * @param sql
	 * @param values
	 * @return
	 */
	protected int executeSQLUpdate(final String sql, final Object... values){
		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				return executeSQLUpdate(s, sql, values);
			}
		})).intValue();
	}
	
	
	protected int executeBatchSQLUpdate(final String[] sqls, final Object... values){
		return ((Number)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				int n = 0;
				for(String sql: sqls){
					n += executeSQLUpdate(s, sql, values);
				}
				return n;
			}
		})).intValue();
	}
	
	private int executeSQLUpdate(Session s, String sql, Object... values){
		SQLQuery query = s.createSQLQuery(sql);
		if(values != null && values.length > 0){
			for(int i = 0 ; i < values.length ; i++){
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}
	
	public String getEntityAlias() {
		return entityAlias;
	}


	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<K> findIds(ResultFilter resultFilter){
		String hql = "select " + getIdProperty() + " from " + getEntityName();
		if(StringUtils.isNotBlank(getEntityAlias())){
			hql += " " + getEntityAlias();
		}
		if(log.isDebugEnabled()){
			log.debug("查询主键集合：" + hql);
		}
		
		if(resultFilter != null){
			return getQuerySupport().find(hql, resultFilter);
		}else{
			return getHibernateTemplate().find(hql);
		}
	}
	
	@Override
    public int getCount(ResultFilter resultFilter) {
		String hql = "select count(*) from " + getEntityName();
		if(StringUtils.isNotBlank(getEntityAlias())){
			hql += " " + getEntityAlias();
		}
        return getQuerySupport().getInt(hql, resultFilter.getCriterion());
    }
	
	public int getCountBySQL(String tableName, Criterion c) {
		String hql = "select count(*) from " + tableName;
		if(StringUtils.isNotBlank(getEntityAlias())){
			hql += " " + getEntityAlias();
		}
        return getQuerySupport().getIntBySQL(hql, c);
    }
	
	protected void doBeforeDeleteOrg(Org org, String... relatedOrgIdPropertyNames){
		if(relatedOrgIdPropertyNames == null || relatedOrgIdPropertyNames.length == 0){
			return;
		}
		
		long orgId = org.getId();
		for(String propertyName: relatedOrgIdPropertyNames){
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			filter.setCriterion(Restrictions.eq(propertyName, orgId));
			int count = getCount(filter);
			if(count > 0){
				String message = "单位【{0}】已经被引用，不能删除。参考代码：{2}.{3}";
				getBusinessExceptionManager().throwBusinessException(1020L, message, null, 
						org.getName(), orgId, getEntityName(), propertyName);
			}
		}
	}
	
	/**
	 * 尽量使用 {@link #doBeforeDeleteOrg(Org, String...)}
	 * @param tableName
	 * @param org
	 * @param relatedOrgIdColumnNames
	 */
	protected void doBeforeDeleteOrgBySQL(String tableName, Org org, String... relatedOrgIdColumnNames){
		if(relatedOrgIdColumnNames == null || relatedOrgIdColumnNames.length == 0){
			return;
		}
		
		long orgId = org.getId();
		for(String columnName: relatedOrgIdColumnNames){
			int count = getCountBySQL(tableName, Restrictions.eq(columnName, orgId));
			if(count > 0){
				String message = "单位【{0}】已经被引用，不能删除。参考代码：{2}.{3}";
				getBusinessExceptionManager().throwBusinessException(1020L, message, null, 
						org.getName(), orgId, tableName, columnName);
			}
		}
	}
	
	/**
	 * @return the businessExceptionManager
	 */
	public BusinessExceptionManager getBusinessExceptionManager() {
		if(businessExceptionManager == null){
			businessExceptionManager = Application.getContext().get("businessExceptionManager", BusinessExceptionManager.class);
		}
		return businessExceptionManager;
	}

	/**
	 * @param businessExceptionManager the businessExceptionManager to set
	 */
	public void setBusinessExceptionManager(
			BusinessExceptionManager businessExceptionManager) {
		this.businessExceptionManager = businessExceptionManager;
	}
}
