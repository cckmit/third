/*
 * $Id: DutyersInfoManagerImpl.java 6414 2014-07-08 03:52:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
			//从指定三级责任人配置中根据当前用户查找三级责任人
			return bizDutyersConfigService.getBizDutyersConfigByUserId(bizDef, currentUserID);
		case BizDef.DUTYER_TYPE_CONFIG_FIRST_MATCH:
			//总指定三级责任人配置中查询满足条件的第一条记录
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
		//TODO 如果必须指定责任人，则需要在这以下步骤中抛出异常
		if(bizDef == null){
			log.warn("BizDef为null，无法为业务设置责任人: " + biz);
			return null;
		}
		
		RFSObject obj = null;
		if(object instanceof RFSObject){
			obj = (RFSObject) object;
		}
		
		DutyersInfo dutyersInfo = getDutyersInfo(bizDef, obj, userID);
		if(dutyersInfo == null){
			//系统用户，只记录DEBUG信息。
			//因为通常系统用户调用的task，work都是特殊的业务
			//if(userID != null && AuthUtils.isSystemUser(userID) && log.isDebugEnabled()){
			if(isUserOrCurrentUserIsSystemUser(userID)){
				if(log.isDebugEnabled()){
					log.debug("找不到业务定义对应的三级责任人信息：biz=" + biz + ", bizDef=" + bizDef.getId() + ", userID=" + userID + ", object=" + object);
				}
			}else{
				if(log.isWarnEnabled()){
					log.warn("找不到业务定义对应的三级责任人信息：biz=" + biz + ", bizDef=" + bizDef.getId() + ", userID=" + userID + ", object=" + object);
				}
			}
			return null;
		}
		
		setDutyersInfo(biz, dutyersInfo);
		return dutyersInfo;
	}
	/**
	 * 判断指定用户是不是系统用户，或者当前登录用户是不是系统用户。
	 * 例如监察计算时，当前用户就是系统用户。
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
