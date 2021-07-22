/*
 * $Id: ObjectProgressManagerImpl.java 6170 2013-01-08 07:15:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.security.User;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInfo;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.security.SchedulerUser;
import cn.redflagsoft.base.service.ObjectProgressManager;
import cn.redflagsoft.base.service.ProgressProvider;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectProgressManagerImpl implements ObjectProgressManager {
	private static final Log log = LogFactory.getLog(ObjectProgressManagerImpl.class);
	private SchemeManager schemeManager;

	/**
	 * @return the schemeManager
	 */
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	/**
	 * @param schemeManager the schemeManager to set
	 */
	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}


	/* (non-Javadoc)
	 */
	public Progress createObjectProgressByWorkScheme(RFSObject object, String title, Date belongTime, 
			String createObjectProgressWorkSchemeInfo, User authUser) {
		Map<String,String> map = Maps.newHashMap();
		map.put("objectId", object.getId() + "");
		map.put("progress.title", title);
		map.put("progress.belongTime", AppsGlobals.formatDate(belongTime));
		
		SchemeInfo schemeInfo = SchemeInvoker.parseSchemeInfo(createObjectProgressWorkSchemeInfo);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			String username = authUser != null ? authUser.getUsername() : SchedulerUser.USERNAME;
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username/*SchedulerUser.USERNAME*/, "***"));
			
			Scheme scheme = schemeManager.getScheme(schemeInfo.getName());
			if(scheme instanceof ParametersAware){
				((ParametersAware) scheme).setParameters(map);
			}
			
			SchemeInvoker.invoke(scheme, schemeInfo.getMethod());
		
			if(scheme instanceof ProgressProvider){
				return ((ProgressProvider) scheme).getProgress();
			}
		}catch(Exception e){
			log.error("创建对象进度报告出错，当前对象: " + object + "， 标题：" + title);
		}finally{
			//调用完成时，将当前用户信息恢复
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		return null;
	}
}
