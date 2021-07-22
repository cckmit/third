package org.opoo.apps.event.v2.core;

import java.io.Serializable;
import java.util.Map;

import org.opoo.apps.event.v2.Event;

/**
 * 属性事件。
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
	 * 属性对象。
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
		 * 属性名称
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * 属性值
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 属性事件类型。
	 *
	 */
	public static enum Type{
		/**
		 * 添加
		 */
		ADDED,
		/**
		 * 删除
		 */
		REMOVED,
		/**
		 * 修改
		 */
		MODIFIED;
	}

	/**
	 * 其他参数
	 * @return the parameters
	 */
	public Map<String,Object> getParameters() {
		return parameters;
	}
}
