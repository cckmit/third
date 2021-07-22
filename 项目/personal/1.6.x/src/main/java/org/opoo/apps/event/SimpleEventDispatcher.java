package org.opoo.apps.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的事件分发器实现。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <E>
 */
public class SimpleEventDispatcher<E extends Event<?>> implements EventDispatcher<E> {
	private Map<Integer, List<EventListener<E>>> listeners = new HashMap<Integer, List<EventListener<E>>>();
	private Object target;
	public SimpleEventDispatcher(){}
	public SimpleEventDispatcher(Object target){
		this.target = target;
	}
	
	public void addEventListener(int eventType, EventListener<E> listener) {
		List<EventListener<E>> list = listeners.get(eventType);
		if(list == null){
			list = new ArrayList<EventListener<E>>();
			listeners.put(eventType, list);
		}
		list.add(listener);
	}

	public void dispatchEvent(E e) {
		//e.target = target;
		int eventType = e.getEventType();
		List<EventListener<E>> list = listeners.get(eventType);
		if(list != null){
			for(EventListener<E> l: list){
				l.perform(e);
			}
		}
	}

	@SuppressWarnings("null")
	public boolean hasEventListener(int eventType, EventListener<E> listener) {
		List<EventListener<E>> list = listeners.get(eventType);
		if(list == null){
			return list.contains(listener);
		}
		return false;
	}

	@SuppressWarnings("null")
	public void removeEventListener(int eventType, EventListener<E> listener) {
		List<EventListener<E>> list = listeners.get(eventType);
		if(list == null){
			list.remove(listener);
		}
	}
	/**
	 * @return the target
	 */
	public Object getTarget() {
		return target;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		@SuppressWarnings("serial")
		class ME extends Event<Long>{
			public ME(int eventType, Long source, Map parameters) {
				super(eventType, source, parameters);
			}

			public ME(int eventType, Long source) {
				super(eventType, source);
			}
			
		}
		@SuppressWarnings({ "serial", "unused" })
		class EL implements EventListener<ME>, java.io.Serializable{
			public void perform(ME event) {
			}
		}
		
		//System.out.println(EL.class.getGenericInterfaces());
		Type[] types = ME.class.getGenericInterfaces();
		for(Type type: types){
			System.out.println("TP::::" + type);
			print(type);
		}
		
		Class cls = ME.class.getSuperclass();
		System.out.println(cls);
		
		Type st = ME.class.getGenericSuperclass();
		print(st);
	}
	
	/**
	 * 打印调试信息。
	 * @param type
	 */
	private static void print(Type type){
		if(type instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType) type;
			System.out.println("RAWTYPE==> " + pt.getRawType());
			Type[] tps = pt.getActualTypeArguments();
			for(Type t: tps){
				System.out.println("getActualTypeArguments--> " + t);
			}
		}
	}
}
