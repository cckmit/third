package org.opoo.apps.exception;

import org.opoo.apps.util.ExceptionUtils;
import org.opoo.util.GuidGenerator;

public class SystemException extends RuntimeException implements AppsException {
	
	private static final long serialVersionUID = 2802835809722264879L;
	private static final String ERROR_MESSAGE = "系统运行时发生异常";
	private String exceptionId;
	private String errorMsg;

	public SystemException() {
		this(ERROR_MESSAGE, null);
	}

	public SystemException(Exception ex) {
		this(ERROR_MESSAGE, ((Throwable) (ex)));
	}

	public SystemException(String msg) {
		this(msg, null);
	}

	public SystemException(String msg, Throwable cause) {
		super(msg, cause);
		Throwable t = cause;
		do {
			if (t == null)
				break;
			if (t instanceof AppsException) {
				exceptionId = ((AppsException) t).getExceptionId();
				break;
			}
			t = t.getCause();
		} while (true);
		if (exceptionId == null)
			exceptionId = GuidGenerator.newGuid("exid");
		errorMsg = msg;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public String getErrorMessage() {
		return errorMsg;
	}

	public String getMessage(){
		return "发生异常";
	}
	
	public String toString() {
		return (new StringBuilder()).append(super.toString()).append(" [").append(getExceptionId()).append("]")
				.toString();
	}

	public Throwable fetchRootCause() {
		return ExceptionUtils.fetchRootCause(this);
	}

	public String fetchRootCauseMessage() {
		return fetchRootCause().getMessage();
	}
}
