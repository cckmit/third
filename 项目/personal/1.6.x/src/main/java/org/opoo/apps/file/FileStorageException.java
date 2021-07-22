package org.opoo.apps.file;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -5620204605287780664L;

	public FileStorageException() {
		super();
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(Throwable cause) {
		super(cause);
	}

}
