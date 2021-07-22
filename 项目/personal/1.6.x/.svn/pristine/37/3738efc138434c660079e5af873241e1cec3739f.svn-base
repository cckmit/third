package org.opoo.apps.util;

import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class StringUtilsTest {
    private static byte[] stringToBytes(String string)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        for(int i = 0; i < string.length(); i += 2)
        {
            int b = Integer.parseInt(string.substring(i, i + 2), 16);
            bytes.write(b);
        }

        return bytes.toByteArray();
    }
    
    private static String bytesToString(byte[] bytes){
    	StringBuffer sb = new StringBuffer();
    	for(byte b: bytes){
    		String s = Integer.toHexString(b);
    		if(s.length() == 1){
    			s = "0" + s;
    		}else if(s.length() > 2){
    			s = s.substring(s.length() - 2);
    		}
    		sb.append(s);
    	}
    	return sb.toString();
    }
    
    public static void main(String[] args) throws DecoderException{
    	
    	String s = "c4e3bac331323334";
    	byte[] bytes = stringToBytes(s);
    	
    	System.out.println(new String(bytes));
    	
    	System.out.println(Integer.toHexString(10));
    	
    	System.out.println(bytesToString("ÄãºÃ1234".getBytes()));
    	System.out.println(Hex.encodeHex("ÄãºÃ1234".getBytes()));
    	System.out.println(new String(Hex.decodeHex("c4e3bac331323334".toCharArray())));
    }
}
