package org.opoo.apps.database;


/**
 * Êý¾Ý¿âÒì³£¡£
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DatabaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2031080075768353237L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}

}
