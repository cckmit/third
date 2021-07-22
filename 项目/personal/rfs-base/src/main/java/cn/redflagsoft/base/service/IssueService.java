/*
 * $Id: IssueService.java 6289 2013-07-29 03:58:23Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
	 * Ϊ�ƶ���ҵ����󴴽����⡣
	 * 
	 * @param object ҵ�����
	 * @param issue �������
	 * @return
	 */
	Issue createIssue(RFSObject object, Issue issue);
	
	/****
	 * 	�õ� ��������
	 * @param id
	 * @return
	 */
	IssueDetails getIssueDetails(Long id);
	
	/**
	 * ����������ѯ�����б�
	 * @param filter
	 * @return
	 */
	List<Issue> findIssuees(ResultFilter filter);
}
