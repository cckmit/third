package org.opoo.apps.exception;

/**
 * 附件处理异常。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AttachmentException extends RuntimeException {

	private static final long serialVersionUID = 5482604031361462547L;

	public AttachmentException() {
		super();
	}

	public AttachmentException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttachmentException(String message) {
		super(message);
	}

	public AttachmentException(Throwable cause) {
		super(cause);
	}
}
