/*
 * $Id: ProgressService.java 6136 2012-11-28 06:33:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.YearProgress;

public interface ProgressService {
	
	Progress createProgress(RFSObject object, String progress);
	
	Progress createProgress(RFSObject object, Progress progress);
	
	/**
	 * 获取指定对象的最近一次上报的进度。
	 * 
	 * 
	 * @param object 业务对象，其id和objectType不能为空。
	 * @return 如果没有最近的进度，则创建一个实例。
	 */
	Progress getLastProgress(RFSObject object);
	
	
	/**
	 * 保存进度情况。
	 * 
	 * @param progress
	 * @return
	 */
	Progress saveProgress(Progress progress);
	
	
	/**
	 * 根据当前业务对象和进度描述，创建一个进度报告实例。
	 * 
	 * @param object
	 * @param progress
	 * @return
	 */
	
	
	/**
	 * 删除进度报告。
	 */
	void removeProgress(Long id);
	
	/**
	 * 查找指定对象指定月份的 Progress
	 * @param objectType
	 * @param objectID
	 * @param year
	 * @param month
	 * @return
	 */
	Progress getProgress(int objectType, long objectID, int year, int month);
	
	/**
	 * 查询指定对象某一年的所有进度报告
	 * @param objectType
	 * @param objectID
	 * @param year
	 * @return
	 */
	YearProgress getYearProgress(int objectType, long objectID, int year);
	
	/***
	 *  获取指定progressId 的 进度报告对象
	 * @param progressId
	 * @return
	 */
	Progress getProgress(Long progressId);
	
	/**
	 *  更新progress对象
	 * @param progress
	 * @return
	 */
	Progress updateProgress(Progress progress);

	/**
	 * 查询进度报告集合。
	 * @param filter
	 * @return
	 */
	List<Progress> findProgresses(ResultFilter filter);
	
	/**
	 * 查询指定对象的最近一个进度报告，按belongTime和mtime倒序查找。
	 * @param object
	 * @return
	 */
	Progress getLastProgressV3(RFSEntityObject object);
}
