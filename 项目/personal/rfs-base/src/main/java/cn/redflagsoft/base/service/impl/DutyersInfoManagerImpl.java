/*
 * $Id: DutyersInfoManagerImpl.java 6414 2014-07-08 03:52:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;

import cn.redflagsoft.base.bean.BizDef;
import cn.redflagsoft.base.bean.BizInstance;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.service.BizDutyersConfigService;
import cn.redflagsoft.base.service.DutyersInfoManager;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class DutyersInfoManagerImpl implements DutyersInfoManager {
	private static final Log log = LogFactory.getLog(DutyersInfoManagerImpl.class);
	
	private BizDutyersConfigService bizDutyersConfigService;
	/**
	 * @return the bizDutyersConfigService
	 */
	public BizDutyersConfigService getBizDutyersConfigService() {
		return bizDutyersConfigService;
	}

	/**
	 * @param bizDutyersConfigService the bizDutyersConfigService to set
	 */
	public void setBizDutyersConfigService(
			BizDutyersConfigService bizDutyersConfigService) {
		this.bizDutyersConfigService = bizDutyersConfigService;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DutyersInfoManager#getDutyersInfo(cn.redflagsoft.base.bean.BizDef, cn.redflagsoft.base.bean.RFSObject, Long)
	 */
	public DutyersInfo getDutyersInfo(BizDef bizDef, RFSObject object, Long currentUserID) {
		int dutyerType = bizDef.getDutyerType();
		switch (dutyerType) {
		case BizDef.DUTYER_TYPE_CONFIG_CURRENT_USER:
			//��ָ�����������������и��ݵ�ǰ�û���������������
			return bizDutyersConfigService.getBizDutyersConfigByUserId(bizDef, currentUserID);
		case BizDef.DUTYER_TYPE_CONFIG_FIRST_MATCH:
			//��ָ�����������������в�ѯ���������ĵ�һ����¼
			return bizDutyersConfigService.getBizDutyersConfigFirstMatch(bizDef, currentUserID);
		case BizDef.DUTYER_TYPE_FROM_OBJECT:
			return object;
		case BizDef.DUTYER_TYPE_CONFIG_BY_OBJECT_ORG_ID:
			return bizDutyersConfigService.getBizDutyersConfigByDutyEntityID(bizDef, object.getDutyEntityID());
		case BizDef.DUTYER_TYPE_UNKNOWN:
		default:
			break;
		} 
		return null;
	}	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DutyersInfoManager#setDutyersInfo(cn.redflagsoft.base.bean.BizInstance, cn.redflagsoft.base.bean.DutyersInfo)
	 */
	public void setDutyersInfo(BizInstance biz, DutyersInfo dutyers) {
		biz.setDutyEntityID(dutyers.getDutyEntityID());
		biz.setDutyEntityName(dutyers.getDutyEntityName());
		biz.setDutyDepartmentID(dutyers.getDutyDepartmentID());
		biz.setDutyDepartmentName(dutyers.getDutyDepartmentName());
		biz.setDutyerID(dutyers.getDutyerID());
		biz.setDutyerName(dutyers.getDutyerName());
		biz.setDutyerLeader1Id(dutyers.getDutyerLeader1Id());
		biz.setDutyerLeader1Name(dutyers.getDutyerLeader1Name());
		biz.setDutyerLeader2Id(dutyers.getDutyerLeader2Id());
		biz.setDutyerLeader2Name(dutyers.getDutyerLeader2Name());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DutyersInfoManager#findAndSetDutyers(cn.redflagsoft.base.bean.BizInstance, cn.redflagsoft.base.bean.BizDef, cn.redflagsoft.base.bean.RFSObject, java.lang.Long)
	 */
	public DutyersInfo findAndSetDutyers(BizInstance biz, BizDef bizDef, Object object, Long userID) {
		//TODO �������ָ�������ˣ�����Ҫ�������²������׳��쳣
		if(bizDef == null){
			log.warn("BizDefΪnull���޷�Ϊҵ������������: " + biz);
			return null;
		}
		
		RFSObject obj = null;
		if(object instanceof RFSObject){
			obj = (RFSObject) object;
		}
		
		DutyersInfo dutyersInfo = getDutyersInfo(bizDef, obj, userID);
		if(dutyersInfo == null){
			//ϵͳ�û���ֻ��¼DEBUG��Ϣ��
			//��Ϊͨ��ϵͳ�û����õ�task��work���������ҵ��
			//if(userID != null && AuthUtils.isSystemUser(userID) && log.isDebugEnabled()){
			if(isUserOrCurrentUserIsSystemUser(userID)){
				if(log.isDebugEnabled()){
					log.debug("�Ҳ���ҵ�����Ӧ��������������Ϣ��biz=" + biz + ", bizDef=" + bizDef.getId() + ", userID=" + userID + ", object=" + object);
				}
			}else{
				if(log.isWarnEnabled()){
					log.warn("�Ҳ���ҵ�����Ӧ��������������Ϣ��biz=" + biz + ", bizDef=" + bizDef.getId() + ", userID=" + userID + ", object=" + object);
				}
			}
			return null;
		}
		
		setDutyersInfo(biz, dutyersInfo);
		return dutyersInfo;
	}
	/**
	 * �ж�ָ���û��ǲ���ϵͳ�û������ߵ�ǰ��¼�û��ǲ���ϵͳ�û���
	 * ���������ʱ����ǰ�û�����ϵͳ�û���
	 * @param userID
	 * @return
	 */
	private boolean isUserOrCurrentUserIsSystemUser(Long userID){
		if(userID != null && AuthUtils.isSystemUser(userID)){
			return true;
		}
		User currentUser = UserHolder.getNullableUser();
		if(currentUser != null && AuthUtils.isSystemUser(currentUser)){
			return true;
		}
		return false;
	}
}
