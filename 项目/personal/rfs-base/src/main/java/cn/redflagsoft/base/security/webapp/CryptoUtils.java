/*
 * $Id: CryptoUtils.java 6231 2013-06-07 02:33:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.webapp;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;


/**
 * 加密解密算法。
 * 
 * <p>AES 加密解密方法结果与 CryptoJS 的结果一致（注意：非英文结果不一致）。
 * 
 * <p>参考: http://crypto-js.googlecode.com/
 * 
 * <p>主要应用流程是：在前台使用 CryptoJS 加密，然后在后台使用该类来解密。
 *
 * @author Alex Lin(lcql@msn.com)
 */
public abstract class CryptoUtils {
	
	public static String encryptByAES(String data, String key, String iv) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();

        byte[] dataBytes = data.getBytes();
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);
        
        //com.sun.org.apache.xml.internal.security.utils.Base64
        //return Base64.encode(encrypted);
        //return new BASE64Encoder().encode(encrypted);
        return new String(Base64.encodeBase64(encrypted)).trim();
	}
	
	public static String decryptByAES(String data, String key, String iv) throws Exception{
		//com.sun.org.apache.xml.internal.security.utils.Base64
		//byte[] decode = Base64.decode(data);
        //byte[] decode = new BASE64Decoder().decodeBuffer(data);
		byte[] decode = Base64.decodeBase64(data.getBytes());
         
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

		byte[] original = cipher.doFinal(decode);
		String originalString = new String(original);
		return originalString.trim();
	}
}
