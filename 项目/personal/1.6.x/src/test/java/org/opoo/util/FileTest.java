package org.opoo.util;


import java.util.Set;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileDeleteStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;



public class FileTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	//@Test
	public void test0(){
		MimetypesFileTypeMap map = new MimetypesFileTypeMap();
		System.out.println(map.getContentType("anD.htm"));
	}
	@Test
	public void test2(){
		long l1 = 7163375629070528609L;
		long l2 = 1702063717L;
		byte[] bytes = new byte[16];
		for(int i = 0 ; i < 8; i++){
			bytes[i] = (byte) l1;
			bytes[i + 8] = (byte) l2;
			l1 >>= 8;
			l2 >>= 8;
		}
		String fs = new String(bytes, 0, 12);
		System.out.println(fs);
		
		String string = Long.toString(239289, Character.MAX_RADIX);
		System.out.println(string);
		
		String str = NumberUtils.longToRadix64String(System.currentTimeMillis());
		System.out.println(str);
		System.out.println(NumberUtils.longToRadix64String(239289));
		long x = System.currentTimeMillis();
		System.out.println(x);
		System.out.println(NumberUtils.longToString(x, 64));
		
		//1333122092546==>NTA4ROc
		
		//101
		System.out.println(digit('1') * Math.pow(10, 2));
		
		System.out.println(parseLong("NTA4ROc", 62));
		System.out.println(parseLong("1010", 64));
		
		x = 1333122092546L;
		System.out.println(NumberUtils.longToRadix64String(x));
		System.out.println(NumberUtils.longToString(x, 64));
		System.out.println(toUnsignedString(1333122092546L, 6));
		System.out.println(toUnsignedString(System.currentTimeMillis(), 6));
	}
	
	//2~64
	static long parseLong(String s, int radix){
		if (s == null) {
			throw new NumberFormatException("null");
		}

		if (radix < Character.MIN_RADIX) {
			throw new NumberFormatException("radix " + radix
					+ " less than Character.MIN_RADIX");
		}
		if (radix > 64) {
			throw new NumberFormatException("radix " + radix
					+ " greater than 64");
		}
		
		long result = 0;
		boolean negative = false;
		int i = 0, max = s.length();
		int digit;
		long limit;
		long multmin;
		
		if (max > 0) {
			if (s.charAt(0) == '-') {
				negative = true;
				limit = Long.MIN_VALUE;
				i++;
			} else {
				limit = -Long.MAX_VALUE;
			}
			if (radix == 10) {
				multmin = negative ? MULTMIN_RADIX_TEN : N_MULTMAX_RADIX_TEN;
			} else {
				multmin = limit / radix;
			}
			if (i < max) {
				digit = digit(s.charAt(i++));
				if (digit < 0) {
					throw new NumberFormatException("For input string: \"" + s + "\"");
				} else {
					result = -digit;
				}
			}
		    while (i < max) {
				// Accumulating negatively avoids surprises near MAX_VALUE
				digit = digit(s.charAt(i++));
				if (digit < 0) {
					throw new NumberFormatException("For input string: \"" + s + "\"");
				}
				if (result < multmin) {
					throw new NumberFormatException("For input string: \"" + s + "\"");
				}
				result *= radix;
				if (result < limit + digit) {
					throw new NumberFormatException("For input string: \"" + s + "\"");
				}
				result -= digit;
		    }
		} else {
			throw new NumberFormatException("For input string: \"" + s + "\"");
		}
		if (negative) {
		    if (i > 1) {
		    	return result;
		    } else {	/* Only got "-" */
		    	throw new NumberFormatException("For input string: \"" + s + "\"");
		    }
		} else {
		    return -result;
		}
	}
	
	static int digit(char c){
		int i = Arrays.binarySearch(digits, c);
		return i;
	}
	static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'$', '.' };
	private static final long   MULTMIN_RADIX_TEN =  Long.MIN_VALUE / 10;
    private static final long N_MULTMAX_RADIX_TEN = -Long.MAX_VALUE / 10;
    
	public static String toUnsignedString(long i, int shift) {
		char[] buf = new char[64];
		int charPos = 64;
		int radix = 1 << shift;
		long mask = radix - 1;
		do {
			buf[--charPos] = digits[(int) (i & mask)];
			i >>>= shift;
		} while (i != 0);
		return new String(buf, charPos, (64 - charPos));
	}
}
