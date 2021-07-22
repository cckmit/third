/*
 * $Id: DutyersInfoManager.java 6095 2012-11-06 01:07:54Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizInstance;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * ���������˹�������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface DutyersInfoManager {

	/**
	 * ��ȡ��������Ϣ��
	 * 
	 * @param bizDef ҵ����
	 * @param object ��ض���
	 * @param currentUser ��ǰ�û�ID
	 * @return
	 */
	DutyersInfo getDutyersInfo(BizDef bizDef, RFSObject object, Long currentUserID);
	
	/**
	 * ������������Ϣ��
	 * 
	 * @param biz ҵ��ʵ��
	 * @param duetyers ��������Ϣ
	 */
	void setDutyersInfo(BizInstance biz, DutyersInfo dutyers);
	
	/**
	 * ��ȡ��������������Ϣ��
	 * @param biz
	 * @param bizDef
	 * @param object
	 * @param userID
	 * @return
	 * @see #getDutyersInfo(BizDef, RFSObject, Long)
	 * @see #setDutyersInfo(BizInstance, DutyersInfo)
	 */
	DutyersInfo findAndSetDutyers(BizInstance biz, BizDef bizDef, Object object, Long userID);
}
