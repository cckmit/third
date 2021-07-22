package cn.redflagsoft.base.event2;

import java.util.List;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;

/**
 * 消息事件。
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgEvent extends Event<SmsgEvent.Type, Smsg> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6261494903423544581L;
	private final List<SmsgReceiver> receivers;
	public SmsgEvent(Type eventType, Smsg source, List<SmsgReceiver> receivers) {
		super(eventType, source);
		this.receivers = receivers;
	}
	public SmsgEvent(Type eventType, Smsg source) {
		super(eventType, source);
		this.receivers = null;
	}

	/**
	 * @return the receivers
	 */
	public List<SmsgReceiver> getReceivers() {
		return receivers;
	}


	public static enum Type{
		CREATED, UPDATED, DELETED, SENT, SENDING, FAIL, READ;
	} 
}
