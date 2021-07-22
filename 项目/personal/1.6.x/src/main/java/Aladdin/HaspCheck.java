package Aladdin;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;


public class HaspCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		msg("java.library.path = " + System.getProperty("java.library.path"));
		
		String VENDOR_CODE = 
			"wEhXhoO9HlBec5mkDHQWqyCT3MtQanxDB5aMWRORQXQIAjamsrQOMkeOWHdVmDWUbK28Qdtg5GBEjcf9" + 
			"kLXxGqYwckLQYM44feW+Zmg0RVEI1hRWJH0xS3/kWfLnoXA1H6sh9UgWZN93QRMovKF1xDm/WSebXEES" + 
			"LWtBYYOeR22E+Ze9D5Eep1eOrZurRix7qTVlCuYmUxaFP/V6vASf+18QtnLOzzsJud5F3AV9aUYgNFAn" + 
			"jOGK41m55uD+8YaG39eF9okDXzJR9Jr3emPADRb1JFKyxOL0pQCQ2yZHZGQallQEe/0IXHKssm/fcTEt" + 
			"bKYMBXdkBNSk/T15HdE0oMrIQVCKPtHxsTYYSym0+4VODqd/vsfrHjVDjbQGHDYItW6kIya6vxxUpL8l" + 
			"FymlT4fb84WTyO0Fi4z94AyYakqJ/NoJLn++WTR0AImYX2kYkWXEslfQdDJ7BTFrM36fMRTtGUWsCcCg" + 
			"+flf83AMhrLUhF2Cv42XXXqxpyqRgdT+6kmYttrPJwAs9wiLdhBonk/e9OIVFGhBOikOsV9G2sNJ0NKK" + 
			"hTeWpn/mJ26KeNFqRPICxMJk3sTzP7sBu327KS/t7/CSJH3+fmV9rTJMTfJwntB7PePILWvXTPpJYp+o" + 
			"pvWEKnhJlp7UmaRjXKLFwkhLRR2Uu62l4FyrqO6mc9CEvFNWTT0mYW2zGEcJQKbP62Cttq3G2WaGvFsr" + 
			"s5EsqS2K7jBlFdAxMlN9CnZqjHWwICi88/KhVFPiMvLA3Au8IYYgXoXuheSMevfOOuziFKuObph+hGJY" + 
			"XjXqBt5ufmZgbeHOBJ4G8h664zTAkcC9HwrbvjBZ08SQ2qhD0gvcfX0IOxU9kXL8GRxnKR4Cy5jSJRZe" + 
			"gFFcH9GTnD43pd3qeBLOsVdwyn0IkHvWuYv884GYOqT9L7QaZcahGSxUTUs=";
		try {
			Hasp hasp = new Hasp(10/*Hasp.HASP_DEFAULT_FID*/);
			hasp.login(VENDOR_CODE);
			int status = hasp.getLastError();
			if(status != HaspStatus.HASP_STATUS_OK){
				try {
					hasp.logout();
				} catch (Exception e) {
				}
				err("Hasp error£º" + status + (status == HaspStatus.HASP_HASP_NOT_FOUND ? " - HASP NOT FOUND." : ""));
				return;
			}
			
			hasp.logout();
			msg("Hasp OK!");
		} catch (Throwable e) {
			err(e.getMessage());
		}
	}
	
	private static void msg(String msg){
		try {
			JOptionPane.showMessageDialog(null, msg);
		} catch (HeadlessException e) {
			System.out.println("[MSG] " + msg);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void err(String msg){
		try {
			JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			System.out.println("[ERR] " + msg);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
