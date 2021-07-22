package org.opoo.apps.exception;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class QueryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2188157944462058106L;

	public QueryException() {
	}

	public QueryException(String message) {
		super(message);
	}

	public QueryException(Throwable cause) {
		super(cause);
	}

	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryException(String target, String method, Throwable cause){
		super("无法执行查询：" + target+ "." + method, cause);
	}
	
	public QueryException(String target, String method){
		super("无法执行查询：" + target+ "." + method);
	}
}
