package cn.redflagsoft.base.menu.event;

import org.opoo.apps.event.v2.Event;

import cn.redflagsoft.base.bean.Action;


/**
 * �����¼���
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ActionEvent extends Event<ActionEvent.Type,Action>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7093812107689575959L;

	public ActionEvent(ActionEvent.Type eventType, Action source) {
		super(eventType, source);
	}
	
	/**
	 * �¼����͡�
	 * 
	 *
	 */
	public static enum Type{
		CREATED,
		UPDATED,
		REMOVED;
	}
}
