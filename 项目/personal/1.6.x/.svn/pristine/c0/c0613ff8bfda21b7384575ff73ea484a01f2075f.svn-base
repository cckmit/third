package org.opoo.apps.module.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.module.ModuleMetaData;

/**
 * 模块状态事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleStateEvent extends Event<ModuleStateEvent.State, ModuleMetaData> {
	/**
	 * 模块状态。
	 * 
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum State{
		/**
		 * 已安装
		 */
		INSTALLED, 
		/**
		 * 已卸载
		 */
		UNINSTALLED, 
		/**
		 * 已加载
		 */
		UNLOADED, 
		/**
		 * 重启
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
