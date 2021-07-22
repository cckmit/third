package org.opoo.apps.security.webapp;

import org.springframework.security.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216092484141701040L;

	public TokenAuthenticationException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

	public TokenAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	public TokenAuthenticationException(String msg) {
		super(msg);
	}

}
