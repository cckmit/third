package cn.redflagsoft.base.event2;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.event.v2.Event;


/**
 * 附件操作事件。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class AttachmentEvent extends Event<AttachmentEvent.Type, Attachment> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2769519252859697153L;

	public AttachmentEvent(Type eventType, Attachment source) {
		super(eventType, source);
	}
	
	public static enum Type{
		CREATED, UPDATED, DELETED;
	}  
}
