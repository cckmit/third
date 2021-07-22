/*
 * $Id: IssueService.java 6289 2013-07-29 03:58:23Z thh $
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

import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.IssueDetails;
import cn.redflagsoft.base.bean.RFSObject;


/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface IssueService extends RFSObjectService<Issue> {

	/**
	 * 为制定的业务对象创建问题。
	 * 
	 * @param object 业务对象
	 * @param issue 问题对象
	 * @return
	 */
	Issue createIssue(RFSObject object, Issue issue);
	
	/****
	 * 	得到 问题详情
	 * @param id
	 * @return
	 */
	IssueDetails getIssueDetails(Long id);
	
	/**
	 * 根据条件查询问题列表
	 * @param filter
	 * @return
	 */
	List<Issue> findIssuees(ResultFilter filter);
}
