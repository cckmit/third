package org.opoo.apps.lifecycle.event;

import org.opoo.apps.event.v2.Event;
import org.opoo.apps.lifecycle.ApplicationState;


/**
 * 应用程序状态改变的事件。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ApplicationStateChangeEvent extends Event<ApplicationStateChangeEvent.Type, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -79697930466986257L;

	/**
	 * 应用程序状态事件类型。
	 * @author Alex Lin(alex@opoo.org)
	 *
	 */
	public static enum Type {
		CHANGE;
	};

	private final ApplicationState previousState;
	private final ApplicationState newState;

	public ApplicationStateChangeEvent(ApplicationState previousState, ApplicationState newState) {
		super(Type.CHANGE, 1);
		this.previousState = previousState;
		this.newState = newState;
	}

	/**
	 * 原始状态。
	 * @return the previousState
	 */
	public ApplicationState getPreviousState() {
		return previousState;
	}

	/**
	 * 新状态。
	 * @return the newState
	 */
	public ApplicationState getNewState() {
		return newState;
	}
}
