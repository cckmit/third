package org.opoo.apps.license.hasp;


public class HaspException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6782698543846644995L;

	public static final int ERROR_UNKNOWN = -1;
	
	private int errorCode = ERROR_UNKNOWN;
	
	public HaspException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public HaspException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public HaspException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public HaspException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode(){
		return errorCode;
	}
}
