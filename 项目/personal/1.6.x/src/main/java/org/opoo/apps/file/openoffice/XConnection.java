package org.opoo.apps.file.openoffice;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;


/**
 * OpenOffice¡¨Ω”°£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface XConnection {
	
	OpenOfficeConnection getOpenOfficeConnection();
	
	XDocumentConverter getDocumentConverter();
}
