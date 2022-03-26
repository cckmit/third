package com.tang.webserviceServer.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class SecUtil  {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SecUtil.class);

    /**
     * 自定义 KEY
     */
    private static byte[] keybytes = { 0x6e, 0x6e, 0x7a, 0x6c, 0x7a, 0x7a, 0x6a, 0x67,  0x6e, 0x6e, 0x7a, 0x6c, 0x7a, 0x7a, 0x6a, 0x67 };


    /**
     * @Title: encrypt
     * @author yunlin.liu
     * @Description: 加密
     * @param @param value
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String encrypt(String value) {
        String s = null;

        int mode = Cipher.ENCRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher.doFinal(value.getBytes("UTF-8"));

            s = String.valueOf(Hex.encodeHex(outBytes));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return s;
    }


    /**
     * @Title: decrypt
     * @author yunlin.liu
     * @Description: 解密
     * @param @param value
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String decrypt(String value) {
        String s = null;

        int mode = Cipher.DECRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher
                    .doFinal(Hex.decodeHex(value.toCharArray()));

            s = new String(outBytes,"UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return s;
    }


    /**
     * @Title: initCipher
     * @author yunlin.liu
     * @Description: 初始化密码
     * @param @param mode
     * @param @return
     * @param @throws NoSuchAlgorithmException
     * @param @throws NoSuchPaddingException
     * @param @throws InvalidKeyException    设定文件
     * @return Cipher    返回类型
     * @throws
     */
    private static Cipher initCipher(int mode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(keybytes, "AES");
        cipher.init(mode, key);
        return cipher;
    }
}

