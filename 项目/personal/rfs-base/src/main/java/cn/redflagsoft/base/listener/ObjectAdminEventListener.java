/*
 * $Id: ObjectAdminEventListener.java 5911 2012-06-26 03:56:12Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.listener;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.commons.ObjectAdmin;
import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.event2.ObjectAdminEvent;
import cn.redflagsoft.base.event2.ObjectAdminEvent.Type;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeInfo;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.service.ObjectService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectAdminEventListener implements EventListener<ObjectAdminEvent>, InitializingBean {
	private static final Log log = LogFactory.getLog(ObjectAdminEventListener.class);
	private SchemeManager schemeManager;
	private ObjectService<RFSObject> objectService;
	private ObjectDao<RFSObject> objectDao;

	private boolean queryStringNeedReplace = false;
	private String objectTypeIdQueryString;
	private Map<Type, Config> configMap = Maps.newHashMap();
	
	public ObjectDao<RFSObject> getObjectDao() {
		return objectDao;
	}

	public void setObjectDao(ObjectDao<RFSObject> objectDao) {
		this.objectDao = objectDao;
	}
	
	/**
	 * @return the schemeManager
	 */
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	/**
	 * @param schemeManager the schemeManager to set
	 */
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	/**
	 * @return the objectService
	 */
	public ObjectService<RFSObject> getObjectService() {
		return objectService;
	}

	/**
	 * @param objectService the objectService to set
	 */
	public void setObjectService(ObjectService<RFSObject> objectService) {
		this.objectService = objectService;
	}

	public List<Config> getConfigs(){
		return Lists.newArrayList(configMap.values());
	}
	
	public void setConfigs(List<Config> configs){
		for(Config config: configs){
			if(configMap.containsKey(config.eventType)){
				log.warn("已经存在配置“" + config.eventType + "”， 将被覆盖。");
			}
			configMap.put(config.eventType, config);
		}
	}
	
	/**
	 * @return the objectTypeIdQueryString
	 */
	public String getObjectTypeIdQueryString() {
		return objectTypeIdQueryString;
	}

	/**
	 * @param objectTypeIdQueryString the objectTypeIdQueryString to set
	 */
	public void setObjectTypeIdQueryString(String objectTypeIdQueryString) {
		this.objectTypeIdQueryString = objectTypeIdQueryString;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(objectTypeIdQueryString);
		queryStringNeedReplace = objectTypeIdQueryString.indexOf(":id") != -1;
	}

	public void handle(ObjectAdminEvent event) {
		RFSObject object = event.getSource();
		
		//根据事件类型查询配置
		Type type = event.getType();
		Config config = configMap.get(type);
		if(config == null){
			log.warn("没有找到相关配置，将跳过本次处理：" + type);
			return;
		}

		//查询相关对象的集合
		//Criterion sql = Restrictions.sql(objectTypeIdQueryString, new Object[]{object.getObjectType(), object.getId()});
		Criterion sql = null;
		if(queryStringNeedReplace){
//			String str = new String(objectTypeIdQueryString);
//			str = str.replaceAll(":type", object.getObjectType() + "");
//			str = str.replaceAll(":id", object.getId() + "");
			String str = resolveObjectTypeIdQueryString(objectTypeIdQueryString, object);
			sql = Restrictions.sql(str);
		}else{
			sql = Restrictions.sql(objectTypeIdQueryString, new Object[]{object.getObjectType(), object.getId()});
		}
		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(sql);
		//List<RFSObject> objects = objectService.findObjects(filter);
		List<RFSObject> objects = objectDao.find(filter);
		
		if(log.isDebugEnabled()){
			log.debug("查询相关对象：" + objects);
		}
		if(objects.isEmpty()){
			log.debug("无相关对象，不执行相关对象的管理功能。");
			return;
		}
		
		//循环处理每个对象
		for(RFSObject obj: objects){
			handle(event, obj, config);
		}
	}
	
	static String resolveObjectTypeIdQueryString(String objectTypeIdQueryString, RFSObject object){
		String str = new String(objectTypeIdQueryString);
		str = str.replaceAll(":type", object.getObjectType() + "");
		str = str.replaceAll(":id", object.getId() + "");
		return str;
	}
	
	/**
	 * @param event
	 * @param obj
	 */
	private void handle(ObjectAdminEvent event, RFSObject obj, Config config) {
		ObjectAdmin objectAdmin = (ObjectAdmin)event.getObjectAdmin();

		String prefix = config.prefix;
		
		Map<String,Object> map = Maps.newHashMap();
		
		map.put("objectId", obj.getId()+"");
		map.put(prefix + ".approveClerkId", objectAdmin.getApproveClerkId());
		map.put(prefix + ".approveClerkName", objectAdmin.getApproveClerkName());
		map.put(prefix + ".approveOrgId", objectAdmin.getApproveOrgId());
		map.put(prefix + ".approveOrgName", objectAdmin.getApproveOrgName());
		if(objectAdmin.getApproveTime() != null){
			map.put(prefix + ".approveTime", AppsGlobals.formatDate(objectAdmin.getApproveTime()));
		}
		map.put(prefix + ".clerkId", objectAdmin.getClerkId());
		map.put(prefix + ".clerkName", objectAdmin.getClerkName());
		map.put(prefix + ".description", objectAdmin.getDescription());
		map.put(prefix + ".fileId", objectAdmin.getFileId());
		map.put(prefix + ".fileNo", objectAdmin.getFileNo());
		map.put(prefix + ".operateCommand", objectAdmin.getOperateCommand());
		map.put(prefix + ".operateDesc", objectAdmin.getOperateDesc());
		map.put(prefix + ".operateMode", objectAdmin.getOperateMode());
		if(objectAdmin.getOperateTime() != null){
			map.put(prefix + ".operateTime", AppsGlobals.formatDate(objectAdmin.getOperateTime()));
		}
		map.put(prefix + ".orgId", objectAdmin.getOrgId());
		map.put(prefix + ".orgName", objectAdmin.getOrgName());
		map.put(prefix + ".remark", objectAdmin.getRemark());
		map.put(prefix + ".status", objectAdmin.getStatus());
		map.put(prefix + ".string0", objectAdmin.getString0());
		map.put(prefix + ".string1", objectAdmin.getString1());
		map.put(prefix + ".string2", objectAdmin.getString2());
		map.put(prefix + ".string3", objectAdmin.getString3());
		map.put(prefix + ".string4", objectAdmin.getString4());
		map.put(prefix + ".type", objectAdmin.getType());
		
		if(log.isDebugEnabled()){
			log.debug("构建WorkScheme参数：" + map);
		}
	
		try{
			SchemeInfo schemeInfo = SchemeInvoker.parseSchemeInfo(config.workSchemeInfo);
			SchemeInvoker.invoke(schemeManager, schemeInfo, map);
		}catch(RuntimeException e){
			throw e;
		}catch(Exception e){
			//log.error("调用对象相关处理的WorkScheme出错：" + config, e);
			throw new SchemeException("调用对象相关处理的WorkScheme出错：" + config, e);
		}
	}
	
	
	/**
	 * 配置
	 */
	public static class Config{
		private Type eventType;
		private String prefix;
		private String workSchemeInfo;
		
		/**
		 *
		 */
		public Config() {
			super();
		}
		/**
		 * @param eventType 事件类型
		 * @param prefix 被调用的WorkScheme接受的参数的前缀
		 * @param workSchemeInfo 被调用的 WorkScheme 信息
		 */
		public Config(String eventType, String prefix, String workSchemeInfo) {
			super();
			this.setEventType(eventType);
			this.prefix = prefix;
			this.workSchemeInfo = workSchemeInfo;
		}
		/**
		 * @return the eventType
		 */
		public String getEventType() {
			return eventType.name();
		}
		/**
		 * @param eventType the eventType to set
		 */
		public void setEventType(String eventType) {
			this.eventType = Type.valueOf(eventType);
		}
		/**
		 * @return the prefix
		 */
		public String getPrefix() {
			return prefix;
		}
		/**
		 * @param prefix the prefix to set
		 */
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		/**
		 * @return the workSchemeInfo
		 */
		public String getWorkSchemeInfo() {
			return workSchemeInfo;
		}
		/**
		 * @param workSchemeInfo the workSchemeInfo to set
		 */
		public void setWorkSchemeInfo(String workSchemeInfo) {
			this.workSchemeInfo = workSchemeInfo;
		}
		
		public String toString(){
			return String.format("[evnetType=%s, prefix=%s, workSchemeInfo=%s]", eventType, prefix, workSchemeInfo);
		}
	}
}
