package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

public abstract class AbstractConnection implements XConnection{
	
	private OpenOfficeConnection connection;
	private XDocumentConverter documentConverter;
	
	public AbstractConnection(OpenOfficeConnection connection) {
		super();
		this.connection = connection;
	}
	
	
	public XDocumentConverter getDocumentConverter() {
		if(documentConverter == null){
			DocumentConverter converter = createDocumentConverter(connection);
			documentConverter = new XDocumentConverterWrapper(converter, connection);
		}
		return documentConverter;
	}
	

	public OpenOfficeConnection getOpenOfficeConnection() {
		return this.connection;
	}

	protected abstract DocumentConverter createDocumentConverter(OpenOfficeConnection connection);
}
