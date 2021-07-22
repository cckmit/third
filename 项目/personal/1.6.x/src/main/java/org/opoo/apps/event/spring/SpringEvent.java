package org.opoo.apps.event.spring;

import org.springframework.context.ApplicationEvent;


/**
 * Spring µÄÊÂ¼þ¡£
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SpringEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5836771263335202880L;

	public SpringEvent(Object source) {
		super(source);
	}
}
