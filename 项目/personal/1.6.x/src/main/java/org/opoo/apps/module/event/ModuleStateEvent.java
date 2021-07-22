package org.opoo.apps.module.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.module.ModuleMetaData;

/**
 * ģ��״̬�¼���
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleStateEvent extends Event<ModuleStateEvent.State, ModuleMetaData> {
	/**
	 * ģ��״̬��
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum State{
		/**
		 * �Ѱ�װ
		 */
		INSTALLED, 
		/**
		 * ��ж��
		 */
		UNINSTALLED, 
		/**
		 * �Ѽ���
		 */
		UNLOADED, 
		/**
		 * ����
		 */
		RESTART;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4787206479103191638L;


	
	public ModuleStateEvent(State eventType, ModuleMetaData source) {
		super(eventType, source);
	}
}
