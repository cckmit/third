/*
 * $Id: EventSource.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import java.io.Serializable;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class EventSource implements EventDispatcherAware {
	private EventDispatcher eventDispatcher;
	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}
	
	public <E extends Event<?,?>> void dispatchEvent(E event){
		if(eventDispatcher != null){
			eventDispatcher.dispatchEvent(event);
		}
	}
	
	/**
	 * �������Ա�����¼���
	 * 
	 * @param <O>
	 * @param <V>
	 * @param object
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	public <O, V extends Serializable> void firePropertyChange(O object, String propertyName, V oldValue, V newValue){
		if(eventDispatcher != null){
			eventDispatcher.dispatchEvent(new PropertyChangeEvent<O, V>(object, propertyName, oldValue, newValue));
		}
	}
}
