package org.opoo.apps.util;

import java.util.Map;

import junit.framework.TestCase;

import org.opoo.apps.Version;
import org.opoo.apps.event.Event;
import org.opoo.apps.event.EventDispatcher;
import org.opoo.apps.event.EventListener;
import org.opoo.apps.event.SimpleEventDispatcher;

public class SimpleEventDispatcherTest extends TestCase {

	public void testListeners(){
		SimpleEventDispatcher dispatcher = new SimpleEventDispatcher(this);

		
		class MyEvent extends Event{
			/**
			 * 
			 */
			private static final long serialVersionUID = -1151391003728570628L;

			public MyEvent(int eventType, Object source, Map parameters) {
				super(eventType, source, parameters);
			}	
		}
				
		dispatcher.addEventListener(0, new EventListener(){
			public void perform(Event event) {
				System.out.println(">  "  + event);				
			}}
		);
//		EventListener l2 = new EventListener<MyEvent>(){
//			public void perform(MyEvent event) {
//				System.out.println(">sdsd  "  + event);		
//			}
//		};
		
//		dispatcher.addEventListener(0, l2);
		dispatcher.dispatchEvent(new MyEvent(0, this, null));
		
	}
	
	
	
	class MMEvent extends Event<Version>{
		private static final long serialVersionUID = 1L;
		public static final int CREATED = 1;
		public static final int UPDATED = 2;
		
		private String message;
		public MMEvent(int eventType, Version source) {
			super(eventType, source, null);
		}
		public MMEvent(int eventType, String message){
			super(eventType, new Version(1,0,0), null);
			this.message = message;
		}
		
		public String getMessage(){
			return message;
		}
	}
	
	class MMEventListener implements EventListener<MMEvent>{
		public void perform(MMEvent event) {
			System.out.println(event.getObject().getClass());
			System.out.println(event.getEventType());
			System.out.println("传递的消息是：" + event.getMessage());
		}
	}
	
	
	
	
	
	public void testMMDispatcher(){
		SimpleEventDispatcher<MMEvent> dispatcher = new SimpleEventDispatcher<MMEvent>(this);
		dispatcher.addEventListener(MMEvent.CREATED, new MMEventListener());
		
		//
		dispatcher.dispatchEvent(new MMEvent(MMEvent.CREATED, new Version(1,0,0)));
	}
	
	
	class SomeClass implements EventDispatcher<MMEvent>{
		private EventDispatcher<MMEvent> dispatcher;
		public SomeClass(){
			dispatcher = new SimpleEventDispatcher<MMEvent>(this);
		};

		public void addEventListener(int eventType, MMEventListener listener) {
			this.dispatcher.addEventListener(eventType, listener);
		}

		public void dispatchEvent(MMEvent e) {
			this.dispatcher.dispatchEvent(e);
		}

		public boolean hasEventListener(int eventType, MMEventListener listener) {
			return dispatcher.hasEventListener(eventType, listener);
		}

		public void removeEventListener(int eventType, MMEventListener listener) {
			this.dispatcher.removeEventListener(eventType, listener);
		}
		
		
		public void foo(){
			System.out.println("foo");
			this.dispatchEvent(new MMEvent(MMEvent.CREATED, "foo"));
		}
		
		public void bar(){
			System.out.println("bar");
			this.dispatchEvent(new MMEvent(MMEvent.CREATED, "bar"));
		}

		public void addEventListener(int eventType,
				EventListener<MMEvent> listener) {
			// TODO Auto-generated method stub
			
		}

		public boolean hasEventListener(int eventType,
				EventListener<MMEvent> listener) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeEventListener(int eventType,
				EventListener<MMEvent> listener) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void testSomeClass(){
		SomeClass sc = new SomeClass();
		sc.addEventListener(MMEvent.CREATED, new MMEventListener());
		
		sc.foo();
		sc.bar();
	}
}
