package org.opoo.apps.event.v2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


import junit.framework.TestCase;

public class EventTest extends TestCase {
	static enum OType{
		IN,OUT;
	}
	
	static class UserEvent extends Event<OType, String>{
		private static final long serialVersionUID = -352273236221957643L;

		public UserEvent(OType eventType, String source) {
			super(eventType, source);
		}
	}
	
	static class UserListener implements EventListener<UserEvent>{
		public void handle(UserEvent event) {
			System.out.println(this + " -- " + event + " is handling.");
		}
	}
	
	static class InEvent extends Event<OType, String>{

		public InEvent(OType eventType, String source) {
			super(eventType, source);
		}
	}
	
	static class OutEvent extends InEvent{
		public OutEvent(OType eventType, String source) {
			super(eventType, source);
		}
	}
	
	static class InListener implements EventListener<InEvent>{
		public void handle(InEvent event) {
			System.out.println(this + " -- " + event + " is handling.");
		}
	}
	
	static interface Anc<E>{
		
	}
	static class OutListener<E> extends InListener implements java.io.Serializable, EventListener<InEvent>, Anc<E>{
		
	}
	static class AllListener implements EventListener{

		public void handle(Event event) {
			System.out.println(this + " -- " + event + " is handling.");
		}
	}
	public static void main(String[] args){
		OutListener listener = new OutListener();
		
        Set<Type> interfaces = new HashSet<Type>();
        Class<?> clazz = listener.getClass();
        do
        {
            interfaces.addAll(Arrays.asList(clazz.getGenericInterfaces()));
            System.out.println(interfaces);
            clazz = clazz.getSuperclass();
        } while(clazz != null);
        System.out.println("------" + interfaces);
        
        for(Type iface: interfaces){
        	System.out.println(iface + "==" + (iface instanceof ParameterizedType));
        	if((iface instanceof ParameterizedType) && ((ParameterizedType)iface).getRawType().equals(EventListener.class)){
        		Type type = ((ParameterizedType)iface).getActualTypeArguments()[0];
        		System.out.println(">>> " + type);
        		System.out.println(((ParameterizedType)iface).getRawType());
        	}
        }
        EventListenerWrapper<InEvent> f = new EventListenerWrapper<InEvent>(listener);
        
        
        Class<?> c = EventUtils.getParameterizedEventClass(listener);
        System.out.println("½á¹û£º " + c);
        
        Set<EventListener> set = new CopyOnWriteArraySet<EventListener>();
        set.add(listener);
        set.add(new UserListener());
        set.add(new AllListener());
        
        
        UserEvent e = new UserEvent(OType.IN, "aaaaaaaaa");
        for(EventListener l: set){
        	EventListenerWrapper w = new EventListenerWrapper(l);
        	if(w.accepts(e)){
        		w.handle(e);
        	}
        }
        
        EventListenerManager holder = new WrappedEventListenerManager();
        holder.addEventListener(listener);
        holder.addEventListener(new UserListener());
        holder.addEventListener(new AllListener());
        
        System.out.println(holder.hasEventListener(listener));
        System.out.println(holder.getEventListeners());
        
        holder.removeEventListener(listener);
        System.out.println(holder.hasEventListener(listener));
        System.out.println(holder.getEventListeners());
        
        
        EventListenerSet s = new EventListenerSetImpl();
        s.add(listener);
        s.add(new UserListener());
        s.add(new AllListener());
        
        Iterator<EventListener> it = s.iterator();
        while(it.hasNext()){
        	System.out.println("IT::: " + it.next());
        }
        
        it = s.iterator(e);
        while(it.hasNext()){
        	System.out.println("IT22::: " + it.next());
        }
	}
}
