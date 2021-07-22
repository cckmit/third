/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license.validator;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.opoo.apps.license.AppsLicenseLog;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;



/**
 * Ç©ÃûÑéÖ¤Æ÷¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CheckSignatureValidator implements Validator {
	//private static final Logger log = Logger.getLogger(CheckSignatureValidator.class.getName());
	
    public void validate(License license) throws LicenseException
    {
    	if(license.getProperties().get("network") != null){
    		return;
    	}
    	
        try
        {
            byte[] bb = {0, 3, 68, 83, 65, 48, -126, 1, -72, 48, -126, 1, 44, 6, 7, 
            		42, -122, 72, -50, 56, 4, 1, 48, -126, 1, 
            		31, 2, -127, -127, 0, -3, 127, 83, -127, 29, 
            		117, 18, 41, 82, -33, 74, -100, 46, -20, -28, 
            		-25, -10, 17, -73, 82, 60, -17, 68, 0, -61, 
            		30, 63, -128, -74, 81, 38, 105, 69, 93, 64, 
            		34, 81, -5, 89, 61, -115, 88, -6, -65, -59, 
            		-11, -70, 48, -10, -53, -101, 85, 108, -41, -127, 
            		59, -128, 29, 52, 111, -14, 102, 96, -73, 107, 
            		-103, 80, -91, -92, -97, -97, -24, 4, 123, 16, 
            		34, -62, 79, -69, -87, -41, -2, -73, -58, 27, 
            		-8, 59, 87, -25, -58, -88, -90, 21, 15, 4, 
            		-5, -125, -10, -45, -59, 30, -61, 2, 53, 84, 
            		19, 90, 22, -111, 50, -10, 117, -13, -82, 43, 
            		97, -41, 42, -17, -14, 34, 3, 25, -99, -47, 
            		72, 1, -57, 2, 21, 0, -105, 96, 80, -113, 
            		21, 35, 11, -52, -78, -110, -71, -126, -94, -21, 
            		-124, 11, -16, 88, 28, -11, 2, -127, -127, 0, 
            		-9, -31, -96, -123, -42, -101, 61, -34, -53, -68, 
            		-85, 92, 54, -72, 87, -71, 121, -108, -81, -69, 
            		-6, 58, -22, -126, -7, 87, 76, 11, 61, 7, 
            		-126, 103, 81, 89, 87, -114, -70, -44, 89, 79, 
            		-26, 113, 7, 16, -127, -128, -76, 73, 22, 113, 
            		35, -24, 76, 40, 22, 19, -73, -49, 9, 50, 
            		-116, -56, -90, -31, 60, 22, 122, -117, 84, 124, 
            		-115, 40, -32, -93, -82, 30, 43, -77, -90, 117, 
            		-111, 110, -93, 127, 11, -6, 33, 53, 98, -15, 
            		-5, 98, 122, 1, 36, 59, -52, -92, -15, -66, 
            		-88, 81, -112, -119, -88, -125, -33, -31, 90, -27, 
            		-97, 6, -110, -117, 102, 94, -128, 123, 85, 37, 
            		100, 1, 76, 59, -2, -49, 73, 42, 3, -127, 
            		-123, 0, 2, -127, -127, 0, -54, 8, -42, -71, 
            		-80, -86, 22, 116, 114, -3, -57, -31, 8, 99, 
            		-75, -76, 56, 119, 48, -93, 120, 90, 123, 101, 
            		-40, -74, 32, -79, -80, -58, -39, -1, -9, -118, 
            		-30, -127, -5, -48, 33, -73, -12, 116, -26, 112, 
            		-64, -65, -5, 76, -38, 26, -37, -52, 101, -102, 
            		-125, -43, 19, -89, 25, -27, 43, -89, 78, 74, 
            		-35, 104, 86, -28, 71, 3, -114, 59, -12, 1, 
            		-104, 88, -33, 83, 25, 34, 55, -88, 60, -71, 
            		122, -66, -22, -48, -38, -84, -128, 37, -53, 72, 
            		65, -121, -26, 115, -88, 14, 89, 94, 27, 21, 
            		95, 83, -14, 125, 70, 42, 56, -82, 117, 108, 
            		-101, -4, -2, 112, -106, -93, 32, 105, -74, 44, 
            		59, -18, 99, 35};
            int k = (bb[0] & 0xff) << 8 | bb[1] & 0xff;
            byte[] pub = new byte[bb.length - k - 2];
            byte[] bts = new byte[3];
            System.arraycopy(bb, k + 2, pub, 0, pub.length);
            System.arraycopy(bb, 2, bts, 0, k);
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pub);
            KeyFactory keyFactory = KeyFactory.getInstance(new String(bts));
            java.security.PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            Signature sig = Signature.getInstance(new String(bts));
            sig.initVerify(pubKey);
            byte decoded[] = Hex.decodeHex(license.getSignature().toCharArray());
            AppsLicenseLog.LOG.debug((new StringBuilder()).append("Validating license. License fingerprint: ").append(license.getSignature()).toString());
            sig.update(license.getFingerprint());
            boolean verified = sig.verify(decoded);
            if(!verified){
                throw new LicenseException("License signature is invalid.");
            }
        }
        catch(Exception e){
            //log.log(Level.SEVERE, e.getMessage(), e);
        	AppsLicenseLog.LOG.error(e.getMessage(), e);
            throw new LicenseException(e);
        }
    }
}
