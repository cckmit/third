package org.opoo.apps.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.opoo.apps.file.converter.JODConverter;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lib.uno.adapter.ByteArrayToXInputStreamAdapter;
import com.sun.star.lib.uno.adapter.OutputStreamToXOutputStreamAdapter;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XColumnRowRange;
import com.sun.star.table.XTableColumns;
import com.sun.star.table.XTableRows;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XRefreshable;

import junit.framework.TestCase;

public class OOoApiTest1 extends TestCase {
	
	public void etestXXL() throws Exception{
		try {
			// get the remote office component context
			com.sun.star.uno.XComponentContext xContext = com.sun.star.comp.helper.Bootstrap
					.bootstrap();
 
			System.out.println("Connected to a running office ...");
 
			com.sun.star.lang.XMultiComponentFactory xMCF = xContext
					.getServiceManager();
 
			String available = (xMCF != null ? "available" : "not available");
			System.out.println("remote ServiceManager is " + available);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	
	public void testXLS() throws Exception{
		InputStream inputStream = new FileInputStream("E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls");
		OpenOfficeConnection openOfficeConnection = new SocketOpenOfficeConnection();
		XComponentLoader desktop = openOfficeConnection.getDesktop();
        
        Map/*<String,Object>*/ loadProperties = new HashMap();
        
        loadProperties.put("Hidden", true);
        
        //loadProperties.putAll();
        //loadProperties.putAll(importOptions);
        // doesn't work using InputStreamToXInputStreamAdapter; probably because it's not XSeekable 
        //property("InputStream", new InputStreamToXInputStreamAdapter(inputStream))
        loadProperties.put("InputStream", new ByteArrayToXInputStreamAdapter(IOUtils.toByteArray(inputStream)));
        
		//XComponent document = desktop.loadComponentFromURL("private:stream", "_blank", 0, toPropertyValues(loadProperties));
        
		String url = "file:///E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls";
        
        PropertyValue pv = new PropertyValue();
        pv.Name = "Hidden";
        pv.Value = Boolean.TRUE;
		XComponent document = desktop.loadComponentFromURL("file:///E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls", 
        		"_blank", 0, new PropertyValue[]{pv});
//		XComponent document = desktop.loadComponentFromURL("file:///d:/2.ods", "_blank", 0, new PropertyValue[]{pv});
        		
        if (document == null) {
            throw new OpenOfficeException("conversion failed: input document is null after loading");
        }

		refreshDocument(document);
		
		XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, document);
		XSpreadsheets sheets = xSpreadsheetDocument.getSheets();
		XSpreadsheet sheet = null;//(XSpreadsheet) sheets.getByName("Sheet1");
		System.out.println(sheets);
		
		XNameAccess xna = (XNameAccess) UnoRuntime.queryInterface(XNameAccess.class, sheets);
		
		Object o = xna.getByName("Sheet1");
		
		//XIndexAccess xia = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class, sheets);
		//Object o = xia.getByIndex(0);
		
		System.out.println(o);
		System.out.println(o instanceof XSpreadsheet);
		o = sheets.getByName("Sheet1");
		
		
		sheet = (XSpreadsheet) UnoRuntime.queryInterface(XSpreadsheet.class, o);
		System.out.println(sheet);

		
		
        XColumnRowRange xCRRange = (XColumnRowRange) UnoRuntime.queryInterface( XColumnRowRange.class, sheet );
        XTableColumns xColumns = xCRRange.getColumns();
        XTableRows xRows = xCRRange.getRows();
		
        
        XPropertySet ps0 = (XPropertySet)UnoRuntime.queryInterface( XPropertySet.class, xRows );
        
        System.out.println(xColumns);
        System.out.println(xRows);
        
        System.out.println(ps0.getPropertyValue("OptimalHeight"));
        ps0.setPropertyValue("OptimalHeight", true);
        
        
     // Get row 7 by index (interface XIndexAccess)
        Object aRowObj = xRows.getByIndex( 8 );
        XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface( XPropertySet.class, aRowObj );
        System.out.println(xPropSet.getPropertyValue("OptimalHeight"));
        xPropSet.setPropertyValue("OptimalHeight", true);
        //        xPropSet.setPropertyValue( "Height", new Integer( 5000 ) );
        
		
        
        
        
//		
//        Map/*<String,Object>*/ storeProperties = new HashMap();
//        storeProperties.putAll(exportOptions);
//        storeProperties.put("OutputStream", new OutputStreamToXOutputStreamAdapter(outputStream));
//        
		try {
			XStorable storable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
			PropertyValue[] props = new PropertyValue[1];
			props[0] = new PropertyValue();
			props[0].Name = "FilterName";
			props[0].Value = "MS Excel 97";
			props[0].Value = "calc_pdf_Export";
			
			storable.storeToURL("file:///d:/ee.pdf", props);
			
			//JODConverter c = new JODConverter();
			//c.convert(new File("d:/ee.xls"), new File("d:/ee.pdf"));
		} finally {
			document.dispose();
		}
		
		//document.dispose();
	}
	
	protected static PropertyValue[] toPropertyValues(Map/*<String,Object>*/ properties) {
		PropertyValue[] propertyValues = new PropertyValue[properties.size()];
		int i = 0;
		for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
            Object value = entry.getValue();
            if (value instanceof Map) {
                // recursively convert nested Map to PropertyValue[]
                Map subProperties = (Map) value;
                value = toPropertyValues(subProperties);
            }
			propertyValues[i++] = property((String) entry.getKey(), value);
		}
		return propertyValues;
	}
	
	protected static PropertyValue property(String name, Object value) {
    	PropertyValue property = new PropertyValue();
    	property.Name = name;
    	property.Value = value;
    	return property;
    }
	
    protected void refreshDocument(XComponent document) {
		XRefreshable refreshable = (XRefreshable) UnoRuntime.queryInterface(XRefreshable.class, document);
		System.out.println(refreshable);
		if (refreshable != null) {
			refreshable.refresh();
		}
	}
}
