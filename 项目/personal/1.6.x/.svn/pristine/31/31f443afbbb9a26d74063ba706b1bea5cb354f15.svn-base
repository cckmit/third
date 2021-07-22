/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license.validator;

import java.net.InetAddress;
import java.net.ServerSocket;

import org.opoo.apps.license.AppsLicense;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;

public class GrantedIPValidator implements Validator {

	public void validate(License license) throws LicenseException {
		AppsLicense lic = new AppsLicense(license);
		String ip = lic.getGrantedIP();
		if(ip != null){
//            try {
//				InetAddress inetaddress = InetAddress.getByName(ip);
//				ServerSocket serversocket = new ServerSocket(0, 1, inetaddress);
//				serversocket.close();
//			} catch (Exception e) {
//				throw new LicenseException("IP not granted for " + ip);
//			}
			
			
			String[] ips = ip.split(",");
			boolean valid = validate(ips, 0);
			if(!valid){
				throw new LicenseException("IP not granted."/* + ip*/);
			}
		}
	}
	
	private boolean validate(String[] ips, int index){
		if(index >= ips.length || index < 0){
			return false;
		}
		String ip = ips[index];
		try {
			InetAddress inetaddress = InetAddress.getByName(ip);
			ServerSocket serversocket = new ServerSocket(0, 1, inetaddress);
			serversocket.close();
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return validate(ips, index + 1);
		}
	}

}
