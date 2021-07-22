package org.opoo.apps.util;

public abstract class ExceptionUtils {
	/**
	 * 
	 * @param throwable
	 * @return
	 */
	public static Throwable fetchRootCause(Throwable throwable) {
		Throwable follower = null;
		for (; throwable != null; throwable = throwable.getCause())
			follower = throwable;
		return follower;
	}
}
