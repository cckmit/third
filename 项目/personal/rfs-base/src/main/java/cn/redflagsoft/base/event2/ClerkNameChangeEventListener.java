/*
 * $Id: ClerkNameChangeEventListener.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opoo.apps.event.v2.EventListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ClerkNameChangeEventListener implements EventListener<ClerkNameChangeEvent>, ApplicationContextAware{
	private Set<ClerkNameChangeListener> listeners = new CopyOnWriteArraySet<ClerkNameChangeListener>();
	

	public void setApplicationContext(ApplicationContext applicationContext) {
		@SuppressWarnings("unchecked")
		Map<String, ClerkNameChangeListener> map = applicationContext.getBeansOfType(ClerkNameChangeListener.class);
		for(ClerkNameChangeListener l: map.values()){
			addClerkNameChangeListener(l);
		}
	}
	
	public void addClerkNameChangeListener(ClerkNameChangeListener listener){
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
	 */
	public void handle(ClerkNameChangeEvent event) {
		for(ClerkNameChangeListener l: listeners){
			l.clerkNameChange(event.getSource(), event.getOldValue(), event.getNewValue());
		}
	}
}
