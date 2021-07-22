package org.opoo.apps.event.v1;



/**
 * 事件。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E> 事件类型的类型
 * @param <O> 事件源类型
 */
public class Event<E extends Enum<E>,O> extends org.opoo.apps.event.v2.Event<E, O> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8806730319032233448L;

	/**
	 * 构建事件实例。
	 * @param eventType 事件类型
	 * @param source 事件源
	 */
	public Event(E eventType, O source) {
		super(eventType, source);
	}
}
