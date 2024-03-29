/*
 * $Id: InfoStatQuery.java 5988 2012-08-13 04:00:25Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.InfoStat;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.dao.InfoDetailDao;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.util.MapUtils;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class InfoStatQuery extends HibernateDaoSupport {
	private EntityObjectLoader entityObjectLoader;
	private InfoDetailDao infoDetailDao;
	
	
	/**
	 * @return the entityObjectLoader
	 */
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}

	/**
	 * @param entityObjectLoader the entityObjectLoader to set
	 */
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}

	
	/**
	 * @return the infoDetailDao
	 */
	public InfoDetailDao getInfoDetailDao() {
		return infoDetailDao;
	}

	/**
	 * @param infoDetailDao the infoDetailDao to set
	 */
	public void setInfoDetailDao(InfoDetailDao infoDetailDao) {
		this.infoDetailDao = infoDetailDao;
	}

	/**
	 * 以信息项责任单位为口径的查询（信息发布统计(按单位)）。
	 * 
	 * <pre>
	 * 查指定类型对象的统计表加条件： and s.objectType=?
	 * 查指定单位的统计表加条件： and s.dutyEntityId=?
	 * 查指定角色的统计表加条件： and c.roleId=?
	 * </pre>
	 * @param filter
	 * @return
	 */
	@Queryable(name="findInfoStatList")
	public List<InfoStatBean> findInfoStatList(ResultFilter filter){
		String qs = "select c, s from InfoConfig c, InfoStat s where c.id=s.infoConfigId and c.status=" + InfoConfig.STATUS_VALID;
		if(filter == null){
			filter = new ResultFilter();
		}
		filter = ResultFilterUtils.append(filter, Order.asc("c.displayOrder"));
		List<Object[]> list = getQuerySupport().find(qs, filter);
		List<InfoStatBean> result = Lists.newArrayList();
		for(Object[] objs: list){
			InfoStatBean bean = new InfoStatBean((InfoConfig)objs[0], (InfoStat)objs[1]);
			result.add(bean);
		}
		return result;
	}
	/**
	 * 以信息项角色为口径的查询（信息发布统计(汇总)）
	 * 
	 *  <pre>
	 * 查指定类型对象的统计表加条件： and s.objectType=?
	 * 查指定单位的统计表加条件： and s.dutyEntityId=?
	 * 查指定角色的统计表加条件： and c.roleId=?
	 * </pre>
	 * @param resultFilter
	 * @return
	 */
	@Queryable(name="findInfoStatListGroupByRole")
	public List<InfoStatBean> findInfoStatListGroupByRole(ResultFilter resultFilter){
		String qs = "select c.id, c.name, c.objectType, c.roleId, c.roleName, c.displayOrder, sum(s.completeInfoCount), sum(s.incompleteInfoCount) " +
				"from InfoConfig c, InfoStat s " +
				"where c.id=s.infoConfigId and c.status=" + InfoConfig.STATUS_VALID;
		Criterion c = resultFilter != null ? resultFilter.getCriterion() : null;
//		Order o = resultFilter != null ? resultFilter.getOrder() : null;
		
		if(c != null){
			qs += " and " + c.toString();
		}
		qs += " group by c.id, c.name, c.objectType, c.roleId, c.roleName, c.displayOrder order by c.displayOrder";
		
		List<Object[]> list = null;
		if(c != null && c.getValues() != null && c.getValues().length > 0){
			list = getQuerySupport().find(qs, c.getValues());
		}else{
			list = getQuerySupport().find(qs);
		}
		
		List<InfoStatBean> result = Lists.newArrayList();
		for(Object[] objs: list){
			// c.id, c.name, c.objectType, c.roleId, c.roleName, c.displayOrder, sum(s.completeInfoCount), sum(s.incompleteInfoCount)
//			long infoConfigId, String name, int objectType, 
//			Long roleId, String roleName,
//			int completeInfoCount, int incompleteInfoCount
//			for(Object o:objs){
//				if(o != null){
//					System.out.println(o.getClass());
//				}
//			}
			Long infoConfigId = (Long) objs[0];
			String name = (String) objs[1];
			Integer objectType = (Integer) objs[2];
			Long roleId = (Long)objs[3];
			String roleName = (String) objs[4];
			int cc = ((Number) objs[6]).intValue();
			int icc = ((Number) objs[7]).intValue();

			InfoStatBean bean = new InfoStatBean(infoConfigId, name, objectType, roleId, roleName, cc, icc);
			result.add(bean);
		}
		return result;
	}
	
	
	/**
	 * 查询执行infoConfigId指定infoDetailStatus的对象明细表，其中infoDetailStatus为空或者-1时忽略该条件。
	 * 
	 * url /q/infoStatQuery.findObjects.jspa?infoConfigId=100&infoDetailStatus=1
	 * @param infoConfigId
	 * @param infoDetailStatus
	 * @param infoStatDutyEntityId
	 * @return
	 */
	@Queryable(argNames={"infoConfigId", "infoDetailStatus", "infoStatDutyEntityId"})
	public List<RFSEntityObject> findObjects(long infoConfigId, Byte infoDetailStatus, Long infoStatDutyEntityId){
		Criterion c = Restrictions.eq("infoConfigId", infoConfigId);
		if(infoDetailStatus != null && infoDetailStatus.byteValue() != -1){
			c = Restrictions.logic(c).and(Restrictions.eq("status", infoDetailStatus));
		}
		if(infoStatDutyEntityId != null && infoStatDutyEntityId.longValue() > 0L){
			if(c instanceof Logic){
				c = ((Logic)c).and(Restrictions.eq("dutyEntityId", infoStatDutyEntityId));
			}else{
				c = Restrictions.logic(c).and(Restrictions.eq("dutyEntityId", infoStatDutyEntityId));
			}
		}
		
		List<InfoDetail> list = infoDetailDao.find(new ResultFilter(c, null));
		
		List<RFSEntityObject> result = Lists.newArrayList();
		for(InfoDetail d: list){
			RFSEntityObject entityObject = entityObjectLoader.getEntityObject(d.getObjectType(), d.getObjectId());
			result.add(entityObject);
		}
		return result;
	}
	
	/**
	 * 分页查询执行infoConfigId指定infoDetailStatus的对象明细表，其中infoDetailStatus为空或者-1时忽略该条件。
	 * 
	 * url /q/infoStatQuery.findPageableObjects.jspa?infoConfigId=100&infoDetailStatus=1&start=0&limit=20
	 * @param filter 原始参数中必须包含 infoConfigId和infoDetailStatus
	 * @return
	 */
	@Queryable
	public PageableList<RFSEntityObject> findPageableObjects(ResultFilter filter){
		Assert.notNull(filter, "filter is required.");
		Assert.isTrue(filter.isPageable(), "必须包含分页参数");
		String infoConfigId = MapUtils.getString(filter.getRawParameters(), "infoConfigId");
		String infoDetailStatus = MapUtils.getString(filter.getRawParameters(), "infoDetailStatus");
		String infoStatDutyEntityId = MapUtils.getString(filter.getRawParameters(), "infoStatDutyEntityId");
		Assert.notNull(infoConfigId, "找不到参数infoConfigId");
		//Assert.notNull(infoDetailStatus, "找不到参数infoDetailStatus");
		long infoConfigIdLong = Long.parseLong(infoConfigId);
//		byte infoDetailStatusByte = Byte.parseByte(infoDetailStatus);
		
		Criterion c = Restrictions.eq("infoConfigId", infoConfigIdLong);
		if(StringUtils.isNotBlank(infoDetailStatus)){
			byte infoDetailStatusByte = Byte.parseByte(infoDetailStatus);
			if(infoDetailStatusByte != -1){
				c = Restrictions.logic(c).and(Restrictions.eq("status", infoDetailStatusByte));
			}
		}
		
		if(StringUtils.isNotBlank(infoStatDutyEntityId)){
			long infoStatDutyEntityIdLong = Long.parseLong(infoStatDutyEntityId);
			if(infoStatDutyEntityIdLong > 0L){
				if(c instanceof Logic){
					c = ((Logic)c).and(Restrictions.eq("dutyEntityId", infoStatDutyEntityIdLong));
				}else{
					c = Restrictions.logic(c).and(Restrictions.eq("dutyEntityId", infoStatDutyEntityIdLong));
				}
			}
		}
		
		filter = ResultFilterUtils.append(filter, c);
		PageableList<InfoDetail> list = infoDetailDao.findPageableList(filter);
		List<RFSEntityObject> result = Lists.newArrayList();
		for(InfoDetail d: list){
			RFSEntityObject entityObject = entityObjectLoader.getEntityObject(d.getObjectType(), d.getObjectId());
			result.add(entityObject);
		}
		return new PageableList<RFSEntityObject>(result, list.getStartIndex(), list.getPageSize(), list.getItemCount());
	}

//	@Queryable
//	public List<RFSObject> findObjects(ResultFilter filter){
//		Assert.notNull(filter, "filter is required.");
//		String entityName = MapUtils.getString(filter.getRawParameters(), "objectEntityName");
//		String objectType = MapUtils.getString(filter.getRawParameters(), "objectType");
//		Assert.notNull(entityName, "找不到对象实体名称");
//		Assert.notNull(objectType, "找不到对象实体类型");
//		
//		String qs = "select o from " + entityName + " o, InfoDetail d where d.objectId=o.id and d.objectType=" + objectType;
//		return getQuerySupport().find(qs, filter);
//		
//		super.getSessionFactory().getAllClassMetadata()entityName;
//		ClassMetadata me;
//		me.getMappedClass(EntityMode.POJO);
//	}
//	
//	@Queryable
//	public PageableList<RFSObject> findPageableObjects(ResultFilter filter){
//		Assert.notNull(filter, "filter is required.");
//		Assert.isTrue(filter.isPageable(), "必须包含分页参数");
//		
//		String entityName = MapUtils.getString(filter.getRawParameters(), "objectEntityName");
//		String objectType = MapUtils.getString(filter.getRawParameters(), "objectType");
//		Assert.notNull(entityName, "找不到对象实体名称");
//		Assert.notNull(objectType, "找不到对象实体类型");
//		
//		String qs = "select o from " + entityName + " o, InfoDetail d where d.objectId=o.id and d.objectType=" + objectType;
//		String qsc = "select count(*) from " + entityName + " o, InfoDetail d where d.objectId=o.id and d.objectType=" + objectType;
//		List<RFSObject> list = getQuerySupport().find(qs, filter);
//		int count = getQuerySupport().getInt(qsc, filter.getCriterion());
//		return new PageableList<RFSObject>(list, filter.getFirstResult(), filter.getMaxResults(), count);
//	}
//	
//	@Queryable
//	public List<RFSObject> findObjects2(String objectEntityName, int objectType, long infoConfigId, byte status){
//		String qs = "select o from " + objectEntityName + " o, InfoDetail d where d.objectId=o.id " +
//				" and d.objectType=? and d.infoConfigId=? and d.status=?";
//		return getQuerySupport().find(qs, new Object[]{objectType, infoConfigId, status});
//	}
	
	public static class InfoStatBean implements Serializable{
		private static final long serialVersionUID = 6810050542455015036L;
		private String name;
		private long infoConfigId;
		private int objectType;
		private Long dutyEntityId;
		private String dutyEntityName;
		private Long roleId;
		private String roleName;
		private int completeInfoCount;
		private int incompleteInfoCount;
		private String remark;
		private double rate = 0.0;
		private String rateString = "0.00%";
		private String rateDesc = "";
		
//		public InfoStatBean(){
//		}

		public InfoStatBean(InfoConfig c, InfoStat s){
			this.name = c.getName();
			this.infoConfigId = c.getId();
			this.objectType = c.getObjectType();
			this.roleId = c.getRoleId();
			this.roleName = c.getRoleName();
			this.dutyEntityId = s.getDutyEntityId();
			this.dutyEntityName = s.getDutyEntityName();
			this.completeInfoCount = s.getCompleteInfoCount();
			this.incompleteInfoCount = s.getIncompleteInfoCount();
			
			cal();
		}
		
		public InfoStatBean(long infoConfigId, String name, int objectType, 
				Long roleId, String roleName,
				int completeInfoCount, int incompleteInfoCount){
			this.name = name;
			this.infoConfigId = infoConfigId;
			this.objectType = objectType;
			this.roleId = roleId;
			this.roleName = roleName;
			this.completeInfoCount = completeInfoCount;
			this.incompleteInfoCount = incompleteInfoCount;
			
			cal();
		}
		
		private void cal(){
			int total = getTotal();
			if(total > 0){
				rate = 1.0 * completeInfoCount / total;
				if(rate > 0){
					BigDecimal decimal = new BigDecimal(rate * 100);
					//System.out.println(decimal);
					BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
					rateString = setScale + "%";
				}
				if(rate >= 1.0){
					rateDesc = "优秀";
				}else if(rate >= 0.8){
					rateDesc = "良好";
				}else if(rate >= 0.6){
					rateDesc = "及格";
				}else if(rate >= 0.4){
					rateDesc = "较差";
				}else if(rate >= 0.2){
					rateDesc = "很差";
				}else{
					rateDesc = "极差";
				}
			}
		}
		
		/**
		 * @return the total
		 */
		public int getTotal() {
			return completeInfoCount + incompleteInfoCount;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(int total) {
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the infoConfigId
		 */
		public long getInfoConfigId() {
			return infoConfigId;
		}
		/**
		 * @param infoConfigId the infoConfigId to set
		 */
		public void setInfoConfigId(long infoConfigId) {
			this.infoConfigId = infoConfigId;
		}
		/**
		 * @return the objectType
		 */
		public int getObjectType() {
			return objectType;
		}
		/**
		 * @param objectType the objectType to set
		 */
		public void setObjectType(int objectType) {
			this.objectType = objectType;
		}
		/**
		 * @return the dutyEntityId
		 */
		public Long getDutyEntityId() {
			return dutyEntityId;
		}
		/**
		 * @param dutyEntityId the dutyEntityId to set
		 */
		public void setDutyEntityId(Long dutyEntityId) {
			this.dutyEntityId = dutyEntityId;
		}
		/**
		 * @return the dutyEntityName
		 */
		public String getDutyEntityName() {
			return dutyEntityName;
		}
		/**
		 * @param dutyEntityName the dutyEntityName to set
		 */
		public void setDutyEntityName(String dutyEntityName) {
			this.dutyEntityName = dutyEntityName;
		}
		/**
		 * @return the completeInfoCount
		 */
		public int getCompleteInfoCount() {
			return completeInfoCount;
		}
		/**
		 * @param completeInfoCount the completeInfoCount to set
		 */
		public void setCompleteInfoCount(int completeInfoCount) {
			this.completeInfoCount = completeInfoCount;
		}
		/**
		 * @return the incompleteInfoCount
		 */
		public int getIncompleteInfoCount() {
			return incompleteInfoCount;
		}
		/**
		 * @param incompleteInfoCount the incompleteInfoCount to set
		 */
		public void setIncompleteInfoCount(int incompleteInfoCount) {
			this.incompleteInfoCount = incompleteInfoCount;
		}
		/**
		 * @return the remark
		 */
		public String getRemark() {
			return remark;
		}
		/**
		 * @param remark the remark to set
		 */
		public void setRemark(String remark) {
			this.remark = remark;
		}

		/**
		 * @return the rate
		 */
		public double getRate() {
			return rate;
		}

		/**
		 * @param rate the rate to set
		 */
		public void setRate(double rate) {
			this.rate = rate;
		}

		/**
		 * @return the rateString
		 */
		public String getRateString() {
			return rateString;
		}

		/**
		 * @param rateString the rateString to set
		 */
		public void setRateString(String rateString) {
			this.rateString = rateString;
		}

		/**
		 * @return the rateDesc
		 */
		public String getRateDesc() {
			return rateDesc;
		}

		/**
		 * @param rateDesc the rateDesc to set
		 */
		public void setRateDesc(String rateDesc) {
			this.rateDesc = rateDesc;
		}

		/**
		 * @return the roleId
		 */
		public Long getRoleId() {
			return roleId;
		}

		/**
		 * @param roleId the roleId to set
		 */
		public void setRoleId(Long roleId) {
			this.roleId = roleId;
		}

		/**
		 * @return the roleName
		 */
		public String getRoleName() {
			return roleName;
		}

		/**
		 * @param roleName the roleName to set
		 */
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
	}
	
	public static void main(String[] args){
		double d = 1.0 * 19/283;
		System.out.println(d * 100);
		
		
		double rate = 0.0;
		String rateString = "0.00%";
		String rateDesc = "";
		int completeInfoCount = 0;
		int incompleteInfoCount = 012323;
	
		int total = completeInfoCount + incompleteInfoCount;
		if(total > 0){
			rate = 1.0 * completeInfoCount / total;
			if(rate > 0){
				BigDecimal decimal = new BigDecimal(rate * 100);
				System.out.println(decimal);
				BigDecimal setScale = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
				rateString = setScale + "%";
			}
			if(rate >= 1.0){
				rateDesc = "优秀";
			}else if(rate >= 0.8){
				rateDesc = "良好";
			}else if(rate >= 0.6){
				rateDesc = "及格";
			}else if(rate >= 0.4){
				rateDesc = "较差";
			}else if(rate >= 0.2){
				rateDesc = "很差";
			}else{
				rateDesc = "极差";
			}
		}
		
		System.out.println(rate + " " + rateString + " " + rateDesc);
	}
}
