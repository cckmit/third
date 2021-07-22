/*
 * ClerkDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.List;

import org.opoo.ndao.Dao;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.security.UserClerk;
import cn.redflagsoft.base.vo.UserClerkVO;

/**
 * @author Administrator
 *
 */
public interface ClerkDao extends Dao<Clerk,Long>{
	/**
	 * ��ѯ��ԱID���ϡ�
	 * 
	 * @param rf
	 * @return
	 */
	List<Long> findClerkIds(ResultFilter rf);
	/**
	 * ��ѯ�û���Ա���ϡ�
	 * @param rf
	 * @return
	 */
	List<UserClerk> findUserClerks(ResultFilter rf);
	/**
	 * 
	 * @param rf
	 * @return
	 */
	List<UserClerkVO> findUserClerkVOs(ResultFilter rf);
	
	/**
	 * ����������ѯ�û���ְԱ�������û����б�
	 * 
	 * @param rf
	 * @return
	 */
	List<String> findUserClerkUsernames(ResultFilter rf);
	
	/**
	 * ��ҳ��ѯ��Ա��ID���ϡ�
	 * @param rf
	 * @return
	 */
	PageableList<Long> findPageableClerkIds(ResultFilter rf);
	
	/**
	 * ���ݹ�����λ���Ʋ�ѯ��Ա���ϡ�
	 * @param entityName
	 * @return
	 */
	List<Clerk> findClerkByEntityName(String entityName);
	
	/**
	 * ����������Ա�Ĺ�����λ��
	 * @param entityID
	 * @param entityName
	 * @return
	 */
	int updateEntityName(Long entityID, String entityName);
	
	/**
	 * @param filter
	 * @return
	 * @see #findUserClerkUsernames(ResultFilter)
	 */
	int getUserClerkUsernameCount(ResultFilter filter);
}
