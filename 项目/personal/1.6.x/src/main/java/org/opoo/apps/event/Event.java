package org.opoo.apps.event;

import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.Map;

/**
 * �¼���
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
	 * �¼���
	 * @param eventType �¼�����
	 * @param source �¼�Դ
	 */
	public Event(int eventType, T source){
		super(source);
		this.eventType = eventType;
	}
	
	/**
	 * �¼���
	 * @param eventType �¼�����
	 * @param source �¼�Դ
	 * @param parameters ����
	 */
	@SuppressWarnings("unchecked")
	public Event(int eventType, T source, Map parameters) {
		super(source);
		this.eventType = eventType;
		this.parameters = parameters != null ? Collections.unmodifiableMap(parameters) : null;
	}

	/**
	 * �¼�Դ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getObject(){
		return (T) super.getSource();
	}
	/**
	 * �¼�����
	 * @return
	 */
	public int getEventType(){
		return eventType;
	}
	/**
	 * ����
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getParameters(){
		return parameters;
	}
	
	
	/**
	 * �¼�����ʱ��
	 * @return
	 */
	public Date getCreated(){
		return created;
	}
}
