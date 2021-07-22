package org.opoo.apps.web.struts2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
import org.opoo.apps.AppsGlobals;

import com.opensymphony.xwork2.util.TypeConversionException;

/**
 * 根据系统配置实现的日期类型转换器。
 * 
 * @author Alex Lin
 * @version 1.0
 */

public class DateConverter extends StrutsTypeConverter {
    public static final String DATE_STYLE = "yyyy-MM-dd";
    public static final String DATE_TIME_STYLE = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_TIME_STYLE = "yyyy-MM-dd HH:mm";
    
    static int DSL = DATE_STYLE.length();
    static int DTSL = DATE_TIME_STYLE.length();
    static int SDTSL = SHORT_DATE_TIME_STYLE.length();
    
    static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_STYLE);
    static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_STYLE);
    static SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat(SHORT_DATE_TIME_STYLE);
    
    @SuppressWarnings("unchecked")
	public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values != null && values.length > 0 && values[0] != null && values[0].length() > 0) {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
            	String value = values[0];
            	int length = value.length();
            	if(length == DSL){//yyyy-MM-dd
            		return dateFormat.parse(value);
            	}else if(length == DTSL){
            		return dateTimeFormat.parse(value);
            	}else if(length == SDTSL){
            		return shortDateTimeFormat.parse(value);
            	}
            	
//            	return AppsGlobals.parseDate(values[0]);
                //return sdf.parse(values[0]);
            }
            catch(ParseException e) {
                throw new TypeConversionException(e);
            }
        }
        return null;
    }
    @SuppressWarnings("unchecked")
	public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //return sdf.format((Date)o);
        	
//        	Date d = (Date) o;
//        	Calendar c = Calendar.getInstance();
//        	c.setTime(d);
//        	if(c.get(Calendar.HOUR) == 0 && c.get(Calendar.MINUTE) == 0 && c.get(Calendar.SECOND) == 0){
//        		return AppsGlobals.formatDate(d);
//        	}
        	return AppsGlobals.formatDate((Date)o);
        }
        return "";
    }
}

