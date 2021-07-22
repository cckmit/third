package org.opoo.apps.conversion;


public class ConversionException extends Exception {

	private static final long serialVersionUID = 1010394667565408267L;

	public ConversionException() {
        super();
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }
}
