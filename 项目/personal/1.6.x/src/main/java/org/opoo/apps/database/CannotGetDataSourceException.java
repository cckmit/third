package org.opoo.apps.database;


/**
 * �޷���ȡ����Դ���쳣��
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CannotGetDataSourceException extends DatabaseException {

	public CannotGetDataSourceException() {
		super();
	}

	public CannotGetDataSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CannotGetDataSourceException(String message) {
		super(message);
	}

	public CannotGetDataSourceException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6455516210565413659L;

}
