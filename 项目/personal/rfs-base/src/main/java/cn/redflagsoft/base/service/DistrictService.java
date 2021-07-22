/*
 * $Id: DistrictService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.District;

/**
 * 行政区划的管理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface DistrictService {

	/**
	 * 获取根对象
	 * @return
	 */
	District getRootDistrict();
	
	/**
	 * 获取行政区划对象。
	 * 
	 * @param code
	 * @return
	 */
	District getDistrict(String code);
	
	
	/**
	 * 删除行政区划，包括下级行政区划。
	 * 
	 * @param code
	 */
	void removeDistrict(String code);
	
	
	/**
	 * 更新行政区划，注意检查循环嵌套。
	 * 
	 * @param code
	 * @param name
	 * @param parent
	 * @param remark
	 * @param displayOrder
	 * @param status
	 * @param type
	 * @return
	 */
	District updateDistrict(String code, String name, District parent, String remark, 
			int displayOrder, byte status, int type);
	
	/**
	 * 新增行政区划。
	 * 
	 * @param code
	 * @param name
	 * @param parent
	 * @param remark
	 * @param displayOrder
	 * @param status
	 * @param type
	 * @return
	 */
	District saveDistrict(String code, String name, District parent, String remark, 
			int displayOrder, byte status, int type);
}
