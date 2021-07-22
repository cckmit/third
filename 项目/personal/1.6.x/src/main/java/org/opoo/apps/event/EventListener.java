package org.opoo.apps.event;

/**
 * ÊÂ¼ş¼àÌıÆ÷¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @param <E>
 */
public interface EventListener<E extends Event<?>> extends java.util.EventListener {
	/**
	 * Ö´ĞĞ¡£
	 * @param event
	 */
	void perform(E event);
}
