/*
 * $Id: CryptoUtils.java 6231 2013-06-07 02:33:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.webapp;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;


/**
 * ���ܽ����㷨��
 * 
 * <p>AES ���ܽ��ܷ�������� CryptoJS �Ľ��һ�£�ע�⣺��Ӣ�Ľ����һ�£���
 * 
 * <p>�ο�: http://crypto-js.googlecode.com/
 * 
 * <p>��ҪӦ�������ǣ���ǰ̨ʹ�� CryptoJS ���ܣ�Ȼ���ں�̨ʹ�ø��������ܡ�
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
