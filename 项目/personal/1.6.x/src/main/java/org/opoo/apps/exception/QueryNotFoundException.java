package org.opoo.apps.exception;


public class QueryNotFoundException extends QueryException {

	private static final long serialVersionUID = -2941582739061535257L;

	public QueryNotFoundException(String name) {
		super(name);
	}

	public QueryNotFoundException(Throwable cause) {
		super(cause);
	}

	public QueryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public QueryNotFoundException(String target, String method, Throwable cause) {
		super(target, method, cause);
	}

	public QueryNotFoundException(String target, String method) {
		super(target, method);
	}
}
