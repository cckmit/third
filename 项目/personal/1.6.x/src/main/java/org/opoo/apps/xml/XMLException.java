package org.opoo.apps.xml;


/**
 * XML ¥¶¿Ì“Ï≥£°£
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class XMLException extends RuntimeException {

	private static final long serialVersionUID = 289893565252321878L;

	public XMLException() {
        super();
    }

    public XMLException(String message) {
        super(message);
    }

    public XMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLException(Throwable cause) {
        super(cause);
    }
}
