package org.opoo.apps.event.v2.core;

import java.io.Serializable;
import java.util.Map;

import org.opoo.apps.event.v2.Event;

/**
 * �����¼���
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class PropertyEvent extends Event<PropertyEvent.Type, PropertyEvent.Entry>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2129260748009127562L;
	private Map<String,Object> parameters;
	
	public PropertyEvent(Type eventType, Entry source) {
		super(eventType, source);
	}
	
	public PropertyEvent(Type type, String name, String value, Map<String,Object> parameters){
		this(type, new Entry(name, value));
		this.parameters = parameters;
	}
	
	public String getName(){
		return getSource().getName();
	}
	
	public String getValue(){
		return getSource().getValue();
	}

	/**
	 * ���Զ���
	 *
	 */
	public static class Entry implements Serializable {
		private static final long serialVersionUID = -7537822383320272813L;
		private final String name;
		private final String value;
		public Entry(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}

		/**
		 * ��������
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * ����ֵ
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * �����¼����͡�
	 *
	 */
	public static enum Type{
		/**
		 * ���
		 */
		ADDED,
		/**
		 * ɾ��
		 */
		REMOVED,
		/**
		 * �޸�
		 */
		MODIFIED;
	}

	/**
	 * ��������
	 * @return the parameters
	 */
	public Map<String,Object> getParameters() {
		return parameters;
	}
}
