package com.beitie.jiaMi;

import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/3
 */
public class TieRSA {
    private static String src="iloveyou";
    public static void main(String[] args) throws Exception {
        jdkRSA();
    }

    public static void  jdkRSA() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //1、初始化秘钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(521);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();

        System.out.println("public key :: "+ Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded())) ;
        System.out.println("private key :: "+ Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded())) ;


        //私钥加密，公钥解密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte [] result = cipher.doFinal(src.getBytes());
        String resultT = Base64.getEncoder().encodeToString(result);
        System.out.println("加密后："+resultT);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte [] dest = cipher.doFinal(result);
        String destT = Base64.getEncoder().encodeToString(dest);
        System.out.println("解密后：" + destT);


    }

}
