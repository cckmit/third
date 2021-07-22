package org.opoo.apps.event;

import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.Map;

/**
 * 事件。
 * @author Alex Lin(alex@opoo.org)
 * @param <T>
 */
public class Event<T> extends EventObject {
	private static final long serialVersionUID = 5327462670896927661L;
	private int eventType;
	@SuppressWarnings("unchecked")
	private Map parameters;
	private Date created = new Date();
	
	/**
	 * 事件。
	 * @param eventType 事件类型
	 * @param source 事件源
	 */
	public Event(int eventType, T source){
		super(source);
		this.eventType = eventType;
	}
	
	/**
	 * 事件。
	 * @param eventType 事件类型
	 * @param source 事件源
	 * @param parameters 参数
	 */
	@SuppressWarnings("unchecked")
	public Event(int eventType, T source, Map parameters) {
		super(source);
		this.eventType = eventType;
		this.parameters = parameters != null ? Collections.unmodifiableMap(parameters) : null;
	}

	/**
	 * 事件源
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getObject(){
		return (T) super.getSource();
	}
	/**
	 * 事件类型
	 * @return
	 */
	public int getEventType(){
		return eventType;
	}
	/**
	 * 参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getParameters(){
		return parameters;
	}
	
	
	/**
	 * 事件创建时间
	 * @return
	 */
	public Date getCreated(){
		return created;
	}
}
