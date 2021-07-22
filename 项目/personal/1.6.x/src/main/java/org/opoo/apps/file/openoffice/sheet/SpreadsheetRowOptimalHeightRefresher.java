package org.opoo.apps.file.openoffice.sheet;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.openoffice.XComponentRefresher;
import org.opoo.apps.file.openoffice.XConnectionProvider;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.sun.star.beans.Property;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.beans.XPropertySetInfo;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.BorderLine;
import com.sun.star.table.TableBorder;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.table.XColumnRowRange;
import com.sun.star.table.XTableRows;
import com.sun.star.uno.UnoRuntime;


/**
 * 电子表格最合适行高刷新器。
 * 
 * 除了能够设置所有行为最合适行高外，还可能设置所有单元格的边距。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SpreadsheetRowOptimalHeightRefresher implements XComponentRefresher {
	private static final Logger log = Logger.getLogger(SpreadsheetRowOptimalHeightRefresher.class.getName());
	private int margin = 135;
	//private short border = 20;
	
	
	
	public void refresh(XComponent document) {
		try {
			refreshRowHeight(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void refreshRowHeight(XComponent document) throws Exception {
    	XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, document);
		
    	if(xSpreadsheetDocument == null){
    		log.warning("不是有效的Spreadsheet文档，忽略处理行高的操作。");
    		return;
    	}
    	
    	XSpreadsheets sheets = xSpreadsheetDocument.getSheets();
		//System.out.println(sheets);
		
		XIndexAccess xia = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class, sheets);
		int sheetCount = xia.getCount();
		
		log.log(Level.FINE, "find sheets: " + sheetCount);
		
		for(int i = 0 ; i < sheetCount ; i++){
			Object o = xia.getByIndex(0);
			//System.out.println(o);
			
			XSpreadsheet sheet = (XSpreadsheet) UnoRuntime.queryInterface(XSpreadsheet.class, o);
			System.out.println(sheet);
			refreshRowHeight(sheet);
		}
    }
    
    private void refreshRowHeight(XSpreadsheet sheet) throws Exception{      
    	XColumnRowRange xColumnRowRange = (XColumnRowRange) UnoRuntime.queryInterface( XColumnRowRange.class, sheet);

        
        
        /*
        System.out.println("col count: " + xColumnRowRange.getColumns().getCount());
        System.out.println("rows count: " + xColumnRowRange.getRows().getCount());
        
        //XCellRange range = (XCellRange) xRows.getByIndex(3);
        //System.out.println(range);
        
        
        //range = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, sheet);
        //System.out.println(range);
        

        XCellRange oRange = (XCellRange)UnoRuntime.queryInterface(
                XCellRange.class, sheet);
        System.out.println(oRange);
        
        for(int x = 0 ; x < 30 ; x++){
        	for(int y = 0 ; y < 10000 ; y++){
        		XCell cell = oRange.getCellByPosition(x, y);
        		//System.out.println(x + ", " + y + ": " + cell);
        		xPropSet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, cell);
        		//System.out.println(xPropSet);
        		BorderLine bl = (BorderLine) xPropSet.getPropertyValue("TopBorder");
        		if(bl.OuterLineWidth > 0){
        			System.out.println(x + ", " + y + ": " + bl.OuterLineWidth);
        		}
        	}
        }
        
        */
        
        
        //调整边框和边距
        //if(margin >= 0 || border >= 0){
	        
	        
	        if(margin >= 0){
	        	XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xColumnRowRange);
		        setParaMargin(xPropSet, "ParaLeftMargin", margin);
		        setParaMargin(xPropSet, "ParaTopMargin", margin);
		        setParaMargin(xPropSet, "ParaBottomMargin", margin);
		        setParaMargin(xPropSet, "ParaRightMargin", margin);
	        }
	        
//	        if(border >= 0){
//		        setBorder(xPropSet, "TopBorder", border);
//		        setBorder(xPropSet, "LeftBorder", border);
//		        setBorder(xPropSet, "RightBorder", border);
//		        setBorder(xPropSet, "BottomBorder", border);
//	        }
//        }
	        
	        
	        
	        
	        
	        //XTableColumns xColumns = xColumnRowRange.getColumns();
	        XTableRows xRows = xColumnRowRange.getRows();
	        
	        XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xRows);
	        
	        //System.out.println(xColumns);
	        //System.out.println(xRows);
	        
	        //System.out.println(xPropSet.getPropertyValue("OptimalHeight"));
	        xPropSet.setPropertyValue("OptimalHeight", true);
	        System.out.println("OptimalHeight: " + xPropSet.getPropertyValue("OptimalHeight"));
    }
    
    private void setParaMargin(XPropertySet xPropSet, String margin, Integer value) throws UnknownPropertyException, WrappedTargetException, PropertyVetoException, IllegalArgumentException{
    	Integer m = (Integer) xPropSet.getPropertyValue(margin);
    	if(m != null){
    		xPropSet.setPropertyValue(margin, value);
    		System.out.println("set " + margin + ": " + value + " --- " + xPropSet.getPropertyValue(margin));
    	}
    }
    
//    private void setBorder(XPropertySet xPropSet, String border, short value) throws UnknownPropertyException, WrappedTargetException, PropertyVetoException, IllegalArgumentException{
//    	BorderLine bl = (BorderLine) xPropSet.getPropertyValue(border);
//    	if(bl != null){
//    		//System.out.println(bl.OuterLineWidth);
//    		bl.OuterLineWidth = value;
//    		xPropSet.setPropertyValue(border, bl);
//    		bl = (BorderLine) xPropSet.getPropertyValue(border);
//    		System.out.println("set " + border + ": " + value + " --- " + bl.OuterLineWidth);
//    	}
//    }
//    
    
    
    
    
    public static void main(String[] args) throws Exception{
    	XConnectionProvider cp = new SocketXConnectionProvider();
    	OpenOfficeConnection conn = cp.getConnection().getOpenOfficeConnection();
    	conn.connect();
    	XComponentLoader desktop = conn.getDesktop();
    	
    	PropertyValue pv = new PropertyValue();
        pv.Name = "Hidden";
        pv.Value = Boolean.TRUE;
        
        //String file = "file:///E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls";
        String file = "file:///E:/work.home/appHome/jxls/template/xxx.ods";
        //String file = "file:///E:/work.home/appHome/jxls/template/XiangMuJianJie.xls";
		XComponent document = desktop.loadComponentFromURL(file, "_blank", 0, new PropertyValue[]{pv});
		
		
		XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, document);
		
    	if(xSpreadsheetDocument == null){
    		log.warning("不是有效的Spreadsheet文档，忽略处理行高的操作。");
    		return;
    	}
    	
    	XSpreadsheets sheets = xSpreadsheetDocument.getSheets();
		//System.out.println(sheets);
		
		XIndexAccess xia = (XIndexAccess) UnoRuntime.queryInterface(XIndexAccess.class, sheets);
		int sheetCount = xia.getCount();
		
		log.log(Level.FINE, "find sheets: " + sheetCount);
		
		for(int i = 0 ; i < sheetCount ; i++){
			Object o = xia.getByIndex(0);
			//System.out.println(o);
			
			XSpreadsheet sheet = (XSpreadsheet) UnoRuntime.queryInterface(XSpreadsheet.class, o);
			System.out.println(sheet + " \n---------------------------");
			//refreshRowHeight(sheet);
			XCell cell = sheet.getCellByPosition(1, 1);
	        System.out.println(cell.getValue());
	        XPropertySet xPropSet0 = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, cell);
	        System.out.println(xPropSet0);
	        XPropertySetInfo info0 = xPropSet0.getPropertySetInfo();
	        Property[] props0 = info0.getProperties();
	        System.out.println("Sheet's cell 1,1 properties:\n-------------------------------------");
	        for(Property p:props0){
	        	System.out.println(p.Name + ": " + xPropSet0.getPropertyValue(p.Name));
	        }
	        BorderLine bl = (BorderLine) xPropSet0.getPropertyValue("DiagonalBLTR");
			System.out.println(bl.InnerLineWidth + ", " + bl.OuterLineWidth + ", " + bl.LineDistance);
			bl = (BorderLine) xPropSet0.getPropertyValue("TopBorder");
			System.out.println(bl.InnerLineWidth + ", " + bl.OuterLineWidth + ", " + bl.LineDistance);
			
			System.out.println(xPropSet0.getPropertyValue("CellStyle"));
			TableBorder tb = (TableBorder) xPropSet0.getPropertyValue("TableBorder");
			System.out.println(tb.BottomLine.OuterLineWidth);
	        System.out.println(xPropSet0.getPropertyValue("ParaLeftMargin").getClass());
	        Integer lm = (Integer) xPropSet0.getPropertyValue("ParaLeftMargin");
	        System.out.println(lm);
	        
	        XCellRange oRange = (XCellRange)UnoRuntime.queryInterface(XCellRange.class, sheet);
	        System.out.println(oRange);
	        
	        
	        
	        
	        xPropSet0 = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, oRange);
	        System.out.println(xPropSet0);
	        info0 = xPropSet0.getPropertySetInfo();
	        props0 = info0.getProperties();
	        System.out.println("Sheet's cell 000000000000000 properties:\n-------------------------------------");
	        for(Property p:props0){
	        	System.out.println(p.Name + ": " + xPropSet0.getPropertyValue(p.Name));
	        }
	        System.out.println("-------------------------------------\n\n");
	        
	        
			
	    	XColumnRowRange xColumnRowRange = (XColumnRowRange) UnoRuntime.queryInterface( XColumnRowRange.class, sheet);
	        //XTableColumns xColumns = xColumnRowRange.getColumns();
	        XTableRows xRows = xColumnRowRange.getRows();
	        
	        XPropertySet xPropSet = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xColumnRowRange);
	        
	        System.out.println(xRows.getByIndex(0));
	        
	        

	        
	        
	        
	        
	        XPropertySetInfo info = xPropSet.getPropertySetInfo();
	        Property[] props = info.getProperties();
	        for(Property p:props){
	        	System.out.println(p.Name + ": " + xPropSet.getPropertyValue(p.Name));
	        }
		}
		
		
		
		conn.disconnect();

    }

	/**
	 * @return the margin
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * @param margin the margin to set
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

//	/**
//	 * @return the border
//	 */
//	public short getBorder() {
//		return border;
//	}
//
//	/**
//	 * @param border the border to set
//	 */
//	public void setBorder(short border) {
//		this.border = border;
//	}
}
