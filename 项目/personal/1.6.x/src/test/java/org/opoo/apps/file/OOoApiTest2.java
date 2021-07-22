package org.opoo.apps.file;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XController;
import com.sun.star.frame.XModel;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheetView;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class OOoApiTest2 {
	private XComponentContext xRemoteContext;
	private XMultiComponentFactory xRemoteServiceManager;
	protected void useConnection() throws java.lang.Exception {
        try {
            // get the remote office component context
            xRemoteContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
            System.out.println("Connected to a running office ...");
 
            xRemoteServiceManager = xRemoteContext.getServiceManager();
        }
        catch( Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
 
  try {
        // get the Desktop, we need its XComponentLoader interface to load a new document
        Object desktop = xRemoteServiceManager.createInstanceWithContext(
            "com.sun.star.frame.Desktop", xRemoteContext);
 
        // query the XComponentLoader interface from the desktop
        XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(
            XComponentLoader.class, desktop);
 
        // create empty array of PropertyValue structs, needed for loadComponentFromURL
        PropertyValue[] loadProps = new PropertyValue[0];
 
        // load new calc file
        XComponent xSpreadsheetComponent = xComponentLoader.loadComponentFromURL(
            "private:factory/scalc", "_blank", 0, loadProps);
 
        // query its XSpreadsheetDocument interface, we want to use getSheets()
        XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(
            XSpreadsheetDocument.class, xSpreadsheetComponent);
 
        // use getSheets to get spreadsheets container
        XSpreadsheets xSpreadsheets = xSpreadsheetDocument.getSheets();
 
        //insert new sheet at position 0 and get it by name, then query its XSpreadsheet interface
        xSpreadsheets.insertNewByName("MySheet", (short)0);
        Object sheet = xSpreadsheets.getByName("MySheet");
        XSpreadsheet xSpreadsheet = (XSpreadsheet)UnoRuntime.queryInterface(
            XSpreadsheet.class, sheet);
 
        // use XSpreadsheet interface to get the cell A1 at position 0,0 and enter 21 as value
        XCell xCell = xSpreadsheet.getCellByPosition(0, 0);
        xCell.setValue(21);
 
        // enter another value into the cell A2 at position 0,1
        xCell = xSpreadsheet.getCellByPosition(0, 1);
        xCell.setValue(21);
 
        // sum up the two cells
        xCell = xSpreadsheet.getCellByPosition(0, 2);
        xCell.setFormula("=sum(A1:A2)");
 
        // we want to access the cell property CellStyle, so query the cell's XPropertySet interface
        XPropertySet xCellProps = (XPropertySet)UnoRuntime.queryInterface(
            XPropertySet.class, xCell);
 
        // assign the cell style "Result" to our formula, which is available out of the box
        xCellProps.setPropertyValue("CellStyle", "Result");
 
        // we want to make our new sheet the current sheet, so we need to ask the model
        // for the controller: first query the XModel interface from our spreadsheet component
        XModel xSpreadsheetModel = (XModel)UnoRuntime.queryInterface(
           XModel.class, xSpreadsheetComponent);
 
        // then get the current controller from the model
        XController xSpreadsheetController = xSpreadsheetModel.getCurrentController();
 
        // get the XSpreadsheetView interface from the controller, we want to call its method
        // setActiveSheet
        XSpreadsheetView xSpreadsheetView = (XSpreadsheetView)UnoRuntime.queryInterface(
           XSpreadsheetView.class, xSpreadsheetController);
 
        // make our newly inserted sheet the active sheet using setActiveSheet
        xSpreadsheetView.setActiveSheet(xSpreadsheet);     
    }
    catch( com.sun.star.lang.DisposedException e ) { //works from Patch 1
        xRemoteContext = null;
        throw e;
    }          
  }
}
