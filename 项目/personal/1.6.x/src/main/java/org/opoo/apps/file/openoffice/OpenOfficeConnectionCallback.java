package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

public interface OpenOfficeConnectionCallback {
	void doInConnection(OpenOfficeConnection connection);
}
