/*
 * $Id: BizDutyersConfigServiceImpl.java 6414 2014-07-08 03:52:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;
import org.opoo.cache.NullCache;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizDutyersConfig;
import cn.redflagsoft.base.dao.BizDutyersConfigDao;
import cn.redflagsoft.base.service.BizDutyersConfigService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BizDutyersConfigServiceImpl implements BizDutyersConfigService {
	private static final Log log = LogFactory.getLog(BizDutyersConfigServiceImpl.class);
	
	private BizDutyersConfigDao bizDutyersConfigDao;
	private Cache<Long, BizDutyersConfig> bizDutyersConfigCache = new NullCache<Long, BizDutyersConfig>();
	private Cache<String, Long> bizDutyersConfigIDCache = new NullCache<String, Long>();
	
	/**
	 * @return the bizDutyersConfigDao
	 */
	public BizDutyersConfigDao getBizDutyersConfigDao() {
		return bizDutyersConfigDao;
	}

	/**
	 * @param bizDutyersConfigDao the bizDutyersConfigDao to set
	 */
	public void setBizDutyersConfigDao(BizDutyersConfigDao bizDutyersConfigDao) {
		this.bizDutyersConfigDao = bizDutyersConfigDao;
	}

	/**
	 * @return the bizDutyersConfigCache
	 */
	public Cache<Long, BizDutyersConfig> getBizDutyersConfigCache() {
		return bizDutyersConfigCache;
	}

	/**
	 * @param bizDutyersConfigCache the bizDutyersConfigCache to set
	 */
	public void setBizDutyersConfigCache(
			Cache<Long, BizDutyersConfig> bizDutyersConfigCache) {
		this.bizDutyersConfigCache = bizDutyersConfigCache;
	}

	/**
	 * @return the bizDutyersConfigIDCache
	 */
	public Cache<String, Long> getBizDutyersConfigIDCache() {
		return bizDutyersConfigIDCache;
	}

	/**
	 * @param bizDutyersConfigIDCache the bizDutyersConfigIDCache to set
	 */
	public void setBizDutyersConfigIDCache(
			Cache<String, Long> bizDutyersConfigIDCache) {
		this.bizDutyersConfigIDCache = bizDutyersConfigIDCache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizDutyersConfigService#getBizDutyersConfigByUserId(cn.redflagsoft.base.bean.BizDef, Long)
	 */
	public BizDutyersConfig getBizDutyersConfigByUserId(BizDef bizDef, Long currentUserID) {
		String cacheKey = String.format("_%s_%s_%s", bizDef.getObjectType(), bizDef.getId(), currentUserID);
		Long id = bizDutyersConfigIDCache.get(cacheKey);
		if(id == null){
			id = getBizDutyersConfigIDByUserId(bizDef, currentUserID);
			if(id != null){
				bizDutyersConfigIDCache.put(cacheKey, id);
				
				if(log.isDebugEnabled()){
					log.debug("Cache BizDutyersConfig.id to cache: " + cacheKey + " -> " + id);
				}
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("Load BizDutyersConfig.id from cache: " + cacheKey + " -> " + id);
			}
		}
		if(id != null){
			return getBizDutyersConfig(id);
		}
		return null;
	}
	
	private Long getBizDutyersConfigIDByUserId(BizDef bizDef, Long currentUserID) {
		//where defObjectType=bizDef.objectType and defId=bizDef.id and dutyerID=currentUser.userId
		Logic logic = Restrictions.logic(Restrictions.eq("defObjectType", bizDef.getObjectType()))
			.and(Restrictions.eq("defId", bizDef.getId()))
			.and(Restrictions.eq("dutyerID", currentUserID));
		Order order = Order.asc("displayOrder");
		ResultFilter filter = new ResultFilter(logic, order, 0, 1);
		List<Long> list = bizDutyersConfigDao.findIds(filter);
		if(list != null && !list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizDutyersConfigService#getBizDutyersConfigFirstMatch(cn.redflagsoft.base.bean.BizDef, Long)
	 */
	public BizDutyersConfig getBizDutyersConfigFirstMatch(BizDef bizDef, Long currentUserID) {
		String cacheKey = String.format("_%s_%s_%s", bizDef.getObjectType(), bizDef.getId(), "_");
		Long id = bizDutyersConfigIDCache.get(cacheKey);
		if(id == null){
			id = getBizDutyersConfigIDFirstMatch(bizDef, currentUserID);
			if(id != null){
				bizDutyersConfigIDCache.put(cacheKey, id);
				
				if(log.isDebugEnabled()){
					log.debug("Cache BizDutyersConfig.id to cache: " + cacheKey + " -> " + id);
				}
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("Load BizDutyersConfig.id from cache: " + cacheKey + " -> " + id);
			}
		}
		if(id != null){
			return getBizDutyersConfig(id);
		}
		return null;
	}
	
	private Long getBizDutyersConfigIDFirstMatch(BizDef bizDef, Long currentUserID) {
		Logic logic = Restrictions.logic(Restrictions.eq("defObjectType", bizDef.getObjectType())).and(Restrictions.eq("defId", bizDef.getId()));
		Order order = Order.asc("displayOrder");
		ResultFilter filter = new ResultFilter(logic, order, 0, 1);
		List<Long> list = bizDutyersConfigDao.findIds(filter);
		if(list != null && !list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
	
	
	public BizDutyersConfig getBizDutyersConfigByDutyerId(Long currentDutyerId){
		Logic logic = Restrictions.logic(Restrictions.eq("dutyerID", currentDutyerId));
		Order order = Order.asc("displayOrder");
		ResultFilter filter = new ResultFilter(logic, order, 0, 1);
		
		List<BizDutyersConfig> lists = bizDutyersConfigDao.find(filter);
		if(lists != null && !lists.isEmpty()){
			return lists.iterator().next();
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizDutyersConfigService#getBizDutyersConfig(java.lang.Long)
	 */
	public BizDutyersConfig getBizDutyersConfig(Long id) {
		BizDutyersConfig config = bizDutyersConfigCache.get(id);
		if(config == null){
			config = bizDutyersConfigDao.get(id);
			if(config != null){
				bizDutyersConfigCache.put(id, config);
			}
		}
		return config;
	}
	
	/**
	 * 添加三级责任人规则
	 * @param bdc
	 * @return
	 */
	public BizDutyersConfig saveBizDutyersConfig(BizDutyersConfig bdc){
		return bizDutyersConfigDao.save(bdc);
	}
	
	/**
	 * 删除三级责任人规则
	 * @param bdc
	 * @return
	 */
	public int deteleBizDutyersConfig(List<Long> ids){
		int temp = 0;
		for(Long id:ids){
			bizDutyersConfigDao.remove(id);
			temp++;
		}
		return temp;
	}
	
	/**
	 * 修改三级责任人规则
	 * @param bizDutyersConfig
	 * @return
	 */
	public BizDutyersConfig updateBizDutyersConfig(BizDutyersConfig bizDutyersConfig){
		return bizDutyersConfigDao.update(bizDutyersConfig);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.BizDutyersConfigService#getBizDutyersConfigByEntityId(cn.redflagsoft.base.bean.BizDef, java.lang.Long)
	 */
	public BizDutyersConfig getBizDutyersConfigByDutyEntityID(BizDef bizDef,	Long dutyEntityID) {
		String cacheKey = String.format("_%s_%s_%s_by_orgid", bizDef.getObjectType(), bizDef.getId(), dutyEntityID);
		Long id = bizDutyersConfigIDCache.get(cacheKey);
		if(id == null){
			id = getBizDutyersConfigIDByDutyEntityID(bizDef, dutyEntityID);
			if(id != null){
				bizDutyersConfigIDCache.put(cacheKey, id);

				if(log.isDebugEnabled()){
					log.debug("Cache BizDutyersConfig.id to cache: " + cacheKey + " -> " + id);
				}
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("Load BizDutyersConfig.id from cache: " + cacheKey + " -> " + id);
			}
		}
		if(id != null){
			return getBizDutyersConfig(id);
		}
		return null;
	}

	/**
	 * @param bizDef
	 * @param dutyEntityID
	 * @return
	 */
	private Long getBizDutyersConfigIDByDutyEntityID(BizDef bizDef, Long dutyEntityID) {
		//defObjectType=bizDef.objectType and defId=bizDef.id and dutyEntityID=dutyEntityID
		Logic logic = Restrictions.logic(Restrictions.eq("defObjectType", bizDef.getObjectType()))
				.and(Restrictions.eq("defId", bizDef.getId()))
				.and(Restrictions.eq("dutyEntityID", dutyEntityID));
		Order order = Order.asc("displayOrder");
		ResultFilter filter = new ResultFilter(logic, order, 0, 1);
		List<Long> list = bizDutyersConfigDao.findIds(filter);
		if(list != null && !list.isEmpty()){
			return list.iterator().next();
		}
		return null;
	}
}
