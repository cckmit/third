/*
 * $Id: SmsgDao.java 5420 2012-03-09 00:49:14Z lcj $
 * RiskDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import java.util.Map;

import cn.redflagsoft.base.bean.Smsg;


/**
 * @author Administrator
 *
 */
public interface SmsgDao extends ObjectDao<Smsg> {
	
//	/**
//	 * ��Ϣ�Ķ�
//	 * @param clerk
//	 * @param smsgId
//	 * @return
//	 */
//	List<Object> smsgRead(Clerk clerk,Long smsgId);
//	
//	
//	/***
//	 * ��Ϣ�Ķ�(������==��ǰ�û�)
//	 * @param clerk
//	 * @param smsgId
//	 * @return
//	 */
//	Smsg smsgReadForClerk(Clerk clerk,Long smsgId);
	
	/**
	 * ��ȡָ���û��յ���ָ�����͵���Ϣ���������ҿ��Ը����Ķ�״̬���й��ˡ�
	 * 
	 * ��Ϣ��״̬�������Ѿ����͵ġ�
	 * 
	 * @param userId �û�ID
	 * @param msgKind ��Ϣ����
	 * @param readStatus �Ķ�״̬���Ѷ���δ����ȡ����ֵʱ���������������
	 * @return ��Ϣ����
	 */
	int getUserSmsgCount(long userId, byte msgKind, byte readStatus);
	
	/**
	 * ��ȡָ���û��յ���ָ��������Ϣ�����������Ķ�״̬���з��顣
	 * 
	 * @param userId
	 * @param msgKind
	 * @return key �Ķ�״̬��value ��Ϣ����
	 */
	Map<Byte, Integer> getUserSmsgCount(long userId, byte msgKind);
}
