package org.opoo.apps.module;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719336261904928233L;

	public ModuleException() {
		super();
	}

	public ModuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModuleException(String message) {
		super(message);
	}

	public ModuleException(Throwable cause) {
		super(cause);
	}

}
