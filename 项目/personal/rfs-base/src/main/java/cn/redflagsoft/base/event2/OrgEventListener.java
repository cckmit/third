/*
 * $Id: OrgEventListener.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opoo.apps.event.v2.EventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.event2.OrgEvent.Type;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class OrgEventListener implements EventListener<OrgEvent>, ApplicationContextAware{
	private Set<OrgDeletedListener> orgDeletedListeners = new CopyOnWriteArraySet<OrgDeletedListener>();
	private Set<OrgBeforeDeleteListener> orgBeforeDeleteListeners = new CopyOnWriteArraySet<OrgBeforeDeleteListener>();
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
	 */
	public void handle(OrgEvent e) {
		Type type = e.getType();
		if(Type.BEFORE_DELETE == type){
			handleBeforeDelete(e);
		}else if(Type.DELETED == type){
			handleDeleted(e);
		}
	}

	/**
	 * @param e
	 */
	private void handleBeforeDelete(OrgEvent e) {
		Org org = e.getSource();
		for(OrgDeletedListener listener : orgDeletedListeners){
			listener.orgDeleted(org);
		}
	}

	/**
	 * @param e
	 */
	private void handleDeleted(OrgEvent e) {
		Org org = e.getSource();
		for(OrgBeforeDeleteListener listener: orgBeforeDeleteListeners){
			listener.orgBeforeDelete(org);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@SuppressWarnings("unchecked")
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String,OrgDeletedListener> map = applicationContext.getBeansOfType(OrgDeletedListener.class);
		for(OrgDeletedListener listener: map.values()){
			addOrgDeletedListener(listener);
		}
		
		Map<String,OrgBeforeDeleteListener> map2 = applicationContext.getBeansOfType(OrgBeforeDeleteListener.class);
		for(OrgBeforeDeleteListener listener: map2.values()){
			addOrgBeforeDeleteListener(listener);
		}
	}
	
	public void addOrgDeletedListener(OrgDeletedListener listener){
		orgDeletedListeners.add(listener);
	}
	
	public void addOrgBeforeDeleteListener(OrgBeforeDeleteListener listener){
		orgBeforeDeleteListeners.add(listener);
	}

}
