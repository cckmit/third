package org.opoo.apps.util;

import java.util.StringTokenizer;

import org.opoo.ndao.criterion.Order;

public class OrderUtils {
	
	/**
	 * 构建Order对象，a.name:1,b.id:0 --> a.name asc, b.id desc
	 * @param string
	 * @return
	 */
    public static Order buildOrder(String string) throws IllegalArgumentException{
        if (string == null || (string = string.trim()).length() < 3 || "null".equalsIgnoreCase(string)) {
            return null;
        }
        
        StringTokenizer token = new StringTokenizer(string, ",;");
        Order order = null;
        while(token.hasMoreTokens()){
        	String str = token.nextToken();
        	//System.out.println(str); 
        			
        	String[] orderStr = str.split(":");
        	if(orderStr.length != 2){
        		throw new IllegalArgumentException("length must be 2");
        	}
        	if(!StringUtils.isValidPropertyName(orderStr[0])){
        		throw new IllegalArgumentException("Illegal sort name: " + orderStr[0]);
        	}
        	
        	boolean asc = "1".equals(orderStr[1]);
        	boolean desc = "0".equals(orderStr[1]);
        	if(!(asc || desc)){
        		throw new IllegalArgumentException("Illegal order dir");
        	}
        	
        	Order current = asc ? Order.asc(orderStr[0]) : Order.desc(orderStr[0]);
        	
        	//System.out.println(str + " " + orderStr.length);
			if (order == null) {
				order = current;
			} else {
				order.add(current);
				// order = order.next;
			}
        }
        return order;
    }
    
	public static boolean isValidSortDir(String dir){
		if(StringUtils.isNotBlank(dir)){
			if("ASC".equalsIgnoreCase(dir) || "DESC".equalsIgnoreCase(dir)){
				return true;
			}
		}
		return false;
	}
	
	public static void validateSortDir(String dir){
		if(isValidSortDir(dir)){
			return;
		}
		throw new IllegalArgumentException("Invalid value of dir"); 
	}
	
	public static boolean isValidSortName(String sortName){
		return StringUtils.isValidPropertyName(sortName);
	}
	
	public static void validateSortName(String sortName){
		if(isValidSortName(sortName)){
			return;
		}
		throw new IllegalArgumentException("Invalid value of sort"); 
	}
	
	
    public static void main(String[] args){
    	Order order = buildOrder("a+1:1;b:0;c:1,dddd:0");
    	System.out.println(order);
    	
    	Order add = Order.asc("a.dsjkdkajsdk").add(order);
    	System.out.println(add);
    }
}
