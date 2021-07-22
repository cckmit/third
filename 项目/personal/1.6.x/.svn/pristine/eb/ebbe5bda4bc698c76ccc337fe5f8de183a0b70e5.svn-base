/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.opoo.apps.license.AppsLicense;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;

/**
 * 过期时间检查器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ExpirationDateValidator implements Validator {
	private static final long THIRTY_DAYS_TIME = 2592000000L;//30 * 24 * 60 * 60 * 1000;
	private static final long THREE_DAYS_TIME = 259200000L;//3 * 24 * 60 * 60 * 1000;
	private static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	
	
	public void validate(License license) throws LicenseException {
		AppsLicense lic = license instanceof AppsLicense ? (AppsLicense)license : new AppsLicense(license);
		Map<String, String> map = license.getProperties();
		boolean isFileLicense = (map.get("hasp") == null && map.get("network") == null);
		Date expirationDate = lic.getExpirationDate();
		if(expirationDate != null && isFileLicense){
			//因为时间是指向当天0点的，所以到当天23点59分59秒999毫秒。
			long expirationTime = expirationDate.getTime() + ONE_DAY_TIME - 1;
			if(expirationTime < System.currentTimeMillis()){
				throw new LicenseException("The license is out of date");
			}else{
				long time = expirationTime - System.currentTimeMillis();
				if(time <= THIRTY_DAYS_TIME && time > THREE_DAYS_TIME){
					String message = "您的 License 将于近期过期，请及时处理。";
					System.err.println(message);
					//throw new AppsLicenseWarningException(message);
				}else if(time <= THREE_DAYS_TIME){
					java.awt.Toolkit.getDefaultToolkit().beep();
					
					System.err.println("************************************************************************");
					String message = "严重：您的 License 将于" + format.format(expirationDate) + "过期，请及时处理！！";
					System.err.println(message);
					System.err.println("************************************************************************");
					//throw new AppsLicenseWarningException(message);
				}else{
					//System.out.println("License is not expired. " + time);
				}
			}
		}else{
			//非文件证书不检查
			//throw new LicenseException("The license is invalid");
		}
	}
}
