/*
 * $Id: ObjectSnChangeEventListener.java 5915 2012-06-28 08:54:20Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.event2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public class ObjectSnChangeEventListener implements EventListener<ObjectSnChangeEvent>, ApplicationContextAware {
	public static final Log log = LogFactory.getLog(ObjectSnChangeEventListener.class);
	private Set<ObjectSnChangeListener> listeners = new CopyOnWriteArraySet<ObjectSnChangeListener>();
	

	public void setApplicationContext(ApplicationContext applicationContext) {
		@SuppressWarnings("unchecked")
		Map<String, ObjectSnChangeListener> map = applicationContext.getBeansOfType(ObjectSnChangeListener.class);
		for(ObjectSnChangeListener l: map.values()){
			addObjectSnChangeListener(l);
		}
	}
	
	public void addObjectSnChangeListener(ObjectSnChangeListener listener){
		if(log.isDebugEnabled()){
			log.debug("Add ObjectSnChangeListener: " + listener);
		}
		listeners.add(listener);
	}
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
	 */
	public void handle(ObjectSnChangeEvent event) {
		for(ObjectSnChangeListener l: listeners){
			l.objectSnChange(event.getSource(), event.getOldValue(), event.getNewValue());
		}
	}
}
