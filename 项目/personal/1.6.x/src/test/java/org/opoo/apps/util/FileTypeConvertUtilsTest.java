package org.opoo.apps.util;

import java.io.File;
import java.net.ConnectException;

import junit.framework.TestCase;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class FileTypeConvertUtilsTest extends TestCase {
	
	
	
	public static void convert(File input, File output) throws ConnectException{
		//File inputFile = new File("document.doc");
		//File outputFile = new File("document.pdf");
		 
		// connect to an OpenOffice.org instance running on port 8100
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		connection.connect();
		 
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(input, output);
		 
		// close the connection
		connection.disconnect();
	}
	
	
	public void testA() throws ConnectException{
		convert(new File("D:\\Downloads\\jodconverter-2.2.1\\lib\\m.doc"),
				new File("D:\\Downloads\\jodconverter-2.2.1\\lib\\m.pdf"));
	}
}
