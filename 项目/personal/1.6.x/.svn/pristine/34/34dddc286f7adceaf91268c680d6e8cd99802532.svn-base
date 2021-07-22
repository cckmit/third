package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

public class RemoteConnection extends AbstractConnection {

	public RemoteConnection(OpenOfficeConnection connection) {
		super(connection);
	}

	@Override
	protected DocumentConverter createDocumentConverter(OpenOfficeConnection connection) {
		return new RefreshableStreamOpenOfficeDocumentConverter(connection);
	}

}
