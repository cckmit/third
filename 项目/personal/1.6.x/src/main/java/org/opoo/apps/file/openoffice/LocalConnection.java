package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

public class LocalConnection extends AbstractConnection {

	public LocalConnection(OpenOfficeConnection connection) {
		super(connection);
	}

	@Override
	protected DocumentConverter createDocumentConverter(OpenOfficeConnection connection) {
		return new RefreshableOpenOfficeDocumentConverter(connection);
	}

}
