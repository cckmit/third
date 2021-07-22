package org.opoo.apps.event.v2;

import java.util.Date;
import java.util.EventObject;
import java.util.UUID;


/**
 * 事件。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @param <E>
 * @param <O>
 */
public class Event<E extends Enum<E>,O> extends EventObject implements AppsEvent {
	private static final long serialVersionUID = 1905396948444243452L;
	private final E eventType;
	private final Date createdDate;
	private final O source;
	private final UUID uuid;
	
	/**
	 * 事件。
	 * @param eventType 事件类型
	 * @param source 事件源
	 */
	public Event(E eventType, O source) {
		super(source);
		this.eventType = eventType;
		this.source = source;
		this.createdDate = new Date();
		this.uuid = UUID.randomUUID();
	}
	/**
	 * 事件类型
	 * @return
	 */
	public E getType(){
		return eventType;
	}

	/**
	 * 事件类型
	 */
	public int getEventType(){
		return eventType.ordinal();
	}
	
	/**
	 * 事件唯一id
	 * @return
	 */
	public UUID getUuid(){
		return uuid;
	}
	
    public String toString()
    {
        return (new StringBuilder()).append("BaseEvent{uuid=").append(uuid.toString()).append(", eventType=").append(eventType).append(", source=").append(source).append(", createdDate=").append(createdDate).append('}').toString();
    }

	
	/**
	 * 事件创建时间。
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * 事件源
	 * @return the source
	 */
	public O getSource() {
		return source;
	}
	
}
