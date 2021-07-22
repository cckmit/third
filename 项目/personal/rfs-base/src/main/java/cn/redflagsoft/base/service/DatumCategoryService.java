/*
 * $Id: DatumCategoryService.java 4677 2011-09-14 01:29:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.vo.DatumCategoryVO;


/**
 * 资料处理。
 *
 */
public interface DatumCategoryService {

	/**
	 * 查询资料列表。
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType,  Long matterID,
			Long objectID, int objectType);

	/**
	 * 查询资料列表。
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @param matterDatumType 资料是否隐藏
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID, int matterDatumType,
			Long objectID, int objectType);

	/**
	 * 查询指定id的资料配置。
	 * 
	 * @param id
	 * @return
	 */
	DatumCategory getDatumCategory(Long id);

	/**
	 * 删除资料配置。
	 * @param datumCategory
	 * @return
	 */
	int deleteDatumCategory(DatumCategory datumCategory);

	/**
	 * 更新资料配置。
	 * @param datumCategory
	 * @return
	 */
	DatumCategory updateDatumCategory(DatumCategory datumCategory);
	
	
	/**
	 * 查询资料列表。
	 * 
	 * @param taskType 业务类型
	 * @param workType 工作类型
	 * @param processType 
	 * @param matterID
	 * @param matterDatumType 资料是否隐藏 
	 * @param objectID 对象ID，可能不存在
	 * @param objectType 对象类型
	 * @param bizAction 业务操作，暂停、续办等。
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType, Long objectID, int objectType, byte bizAction);
	
	/**
	 * 查询资料列表。
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param matterID
	 * @param objectID
	 * @param objectType
	 * @param bizAction
	 * @return
	 */
	List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			Long objectID, int objectType, byte bizAction);
}
