package org.opoo.apps;

import javax.xml.ws.WebFault;

@WebFault(name = "NotFound")
public class NotFoundException extends Exception {

	private static final long serialVersionUID = -3828452447409270279L;

	public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public NotFoundException(Throwable throwable) {
        super(throwable);
    }
}