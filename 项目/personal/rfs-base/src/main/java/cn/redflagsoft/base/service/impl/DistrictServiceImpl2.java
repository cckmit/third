/*
 * $Id: DistrictServiceImpl2.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opoo.cache.Cache;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.District;
import cn.redflagsoft.base.bean.DistrictBean;
import cn.redflagsoft.base.dao.DistrictDao;
import cn.redflagsoft.base.service.DistrictService;

/**
 * 行政区划管理。
 * 
 * 该实现不使用缓存，一次性查询数据，在内存中组装。
 * 查询整个对象树时效率较高，查询单个对象的效率较低。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DistrictServiceImpl2 implements DistrictService {
	private DistrictDao districtDao;

	public DistrictDao getDistrictDao() {
		return districtDao;
	}

	public void setDistrictDao(DistrictDao districtDao) {
		this.districtDao = districtDao;
	}


	public District getRootDistrict(){
		return getDistrict(null);
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DistrictService#getDistrict(java.lang.String)
	 */
	public District getDistrict(String code) {
		Map<String, DistrictBean> map = findDistrictMap();
		Cache<String, District> cache = new org.opoo.cache.MapCache<String, District>();
		return getDistrict(map, cache , code);
	}
	
	Map<String, DistrictBean> findDistrictMap(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		List<DistrictBean> list = districtDao.find(filter);
		Map<String, DistrictBean> map = new LinkedHashMap<String, DistrictBean>();
		for (DistrictBean districtBean : list) {
			map.put(districtBean.getCode(), districtBean);
		}
		return map;
	}
	
	static District getDistrict(Map<String, DistrictBean> districts, Cache<String, District> cache, String code){
		if(code == null){
			code = "root";
		}
		District district = cache.get(code);
		if(district == null){
			if("root".equalsIgnoreCase(code)){
				district = new DistrictRoot2(districts, cache);
			}else{
				district = new DistrictImpl2(districts, cache, code);
			}
			cache.put(code, district);
		}
		return district;
	}

	static List<District> findSubdistricts(Map<String, DistrictBean> districts, Cache<String, District> cache, String code){
		List<District> list = new ArrayList<District>();
		for (DistrictBean d : districts.values()) {
			if((code == null && d.getParentCode() == null) 
					|| (code != null && code.equals(d.getParentCode()))){
				District district = getDistrict(districts, cache, d.getCode());
				list.add(district);
			}
		}
		return list;
	}
	
	
	List<String> findSubdistrictIds(String code){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setOrder(Order.asc("displayOrder"));
		filter.setCriterion(code == null ? Restrictions.isNull("parentCode") : Restrictions.eq("parentCode", code));
		return getDistrictDao().findIds(filter);
	}
	
	List<District> findSubdistricts(String code){
		final List<String> ids = findSubdistrictIds(code);
		return new AbstractList<District>(){
			@Override
			public District get(int index) {
				String code = ids.get(index);
				return getDistrict(code);
			}

			@Override
			public int size() {
				return ids.size();
			}};
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DistrictService#removeDistrict(java.lang.String)
	 */
	public void removeDistrict(String code) {
		List<String> ids = findSubdistrictIds(code);
		for (String subcode : ids) {
			removeDistrict(subcode);
		}
		getDistrictDao().remove(code);
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DistrictService#saveDistrict(java.lang.String, java.lang.String, cn.redflagsoft.base.bean.District, java.lang.String, int, byte, short)
	 */
	public District saveDistrict(String code, String name, District parent, String remark, int displayOrder,
			byte status, int type) {
		DistrictBean bean = new DistrictBean();
		bean.setCode(code);
		bean.setName(name);
		bean.setParentCode(parent != null ? parent.getCode() : null);
		bean.setRemark(remark);
		bean.setDisplayOrder(displayOrder);
		bean.setStatus(status);
		bean.setType(type);
		DistrictBean districtBean = getDistrictDao().save(bean);
		return getDistrict(districtBean.getCode());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DistrictService#updateDistrictBean(java.lang.String, java.lang.String, cn.redflagsoft.base.bean.District, java.lang.String, int, byte, short)
	 */
	public District updateDistrict(String code, String name, District parent, String remark, int displayOrder,
			byte status, int type) {
		if(code == null || "null".equalsIgnoreCase(code) || "root".equalsIgnoreCase(code)){
			throw new IllegalArgumentException("顶级行政区划不能修改");
		}

		String parentCode = parent != null ? parent.getCode() : null;
		if(parentCode != null){
			District old = getDistrict(code);
			if(containsCode(old, parentCode)){
				throw new IllegalArgumentException("上级行政区划设置不正确");
			}
		}
		
		DistrictBean bean = getDistrictDao().get(code);
		bean.setName(name);
		bean.setParentCode(parent != null ? parent.getCode() : null);
		bean.setRemark(remark);
		bean.setDisplayOrder(displayOrder);
		bean.setStatus(status);
		bean.setType(type);
		DistrictBean districtBean = getDistrictDao().update(bean);
		return getDistrict(districtBean.getCode());
	}
	
	
	private boolean containsCode(District district, String code){
		if(district.getCode().equalsIgnoreCase(code)){
			return true;
		}
		for(District d : district.getSubdistricts()){
			if(containsCode(d, code)){
				return true;
			}
		}
		return false;
	}
}
