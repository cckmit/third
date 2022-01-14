package com.zb.ws.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class RSAUtil {

//    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";
//    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALPXOpAe0e8pzMeHnx4+JE2A7KIcTHqVkM+W/AFNoeaQ2VDLInTpIiUxTWmYYrEguNPufTqfEbsuUVVQ3kyRb+PAXf3/52uQczkHSSH6E9DKAWNGrzXLG8vCiW35WruH4JNTRnXCQUAFxN5BCV1mQmVOnLHRzf6OrSjjvAgWOrl/AgMBAAECgYAgA0YHdZUFL7mmIvwuE/2+Vh7JVKRAhfM7ILNHQBx7wHkOqro9eWp8mGQhUeDvitWb1C4yizJK0Znkx/pqQtFZuoatUsggocjXFl86FElQwrBp08DvfKfd0bGgy0VTFQVmCtxiqhpAmC7xmXNZXfBD41rl9CKbFfZw05QC5BoQ0QJBAO7LSku97NgFBJQ+vbmVDonuvgnQjVNb7SnwrcpJHEUAGbaVq1a50jz+s6n39TOagASaW6pcY0uwiygYu6xDnkMCQQDAzIGNKFKomTI6djcOyHfQ1ZXqyDQ3guX6nHhzZnNHFF8ZD3fPyyIRSZ3JvPK5iEzJLhB7FRtyWkGcdXgJTWoVAkBfx9zKGqkYUJLwn2XcPWRygPdq2mMFb5bmPqqGu+KB7rNhoBD0nV4tpwALifCpPSxiLEPeRmZxoqN+dsU4KHsfAkAyQt4fK3zpAQ8MGJdf3jkGEzhC/bBHLHPB8pqgEvxIcnIcOWEVpbIa6aMd3Yk1fuftpnmbbLQ8CnWCUUlau3jFAkEAk6bOZIWhTYRwIZcwBdkpyLlbatQFoTTM3i444YutXt3FrFfaWBxge+eYKId+J4dCrt/EmHhSfWKEzHibf6N5Sg==";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";

    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALPXOpAe0e8pzMeHnx4+JE2A7KIcTHqVkM+W/AFNoeaQ2VDLInTpIiUxTWmYYrEguNPufTqfEbsuUVVQ3kyRb+PAXf3/52uQczkHSSH6E9DKAWNGrzXLG8vCiW35WruH4JNTRnXCQUAFxN5BCV1mQmVOnLHRzf6OrSjjvAgWOrl/AgMBAAECgYAgA0YHdZUFL7mmIvwuE/2+Vh7JVKRAhfM7ILNHQBx7wHkOqro9eWp8mGQhUeDvitWb1C4yizJK0Znkx/pqQtFZuoatUsggocjXFl86FElQwrBp08DvfKfd0bGgy0VTFQVmCtxiqhpAmC7xmXNZXfBD41rl9CKbFfZw05QC5BoQ0QJBAO7LSku97NgFBJQ+vbmVDonuvgnQjVNb7SnwrcpJHEUAGbaVq1a50jz+s6n39TOagASaW6pcY0uwiygYu6xDnkMCQQDAzIGNKFKomTI6djcOyHfQ1ZXqyDQ3guX6nHhzZnNHFF8ZD3fPyyIRSZ3JvPK5iEzJLhB7FRtyWkGcdXgJTWoVAkBfx9zKGqkYUJLwn2XcPWRygPdq2mMFb5bmPqqGu+KB7rNhoBD0nV4tpwALifCpPSxiLEPeRmZxoqN+dsU4KHsfAkAyQt4fK3zpAQ8MGJdf3jkGEzhC/bBHLHPB8pqgEvxIcnIcOWEVpbIa6aMd3Yk1fuftpnmbbLQ8CnWCUUlau3jFAkEAk6bOZIWhTYRwIZcwBdkpyLlbatQFoTTM3i444YutXt3FrFfaWBxge+eYKId+J4dCrt/EmHhSfWKEzHibf6N5Sg==";

    //1.请求时将参数按顺序进行拼接，将key按照ASCII顺序进行排列，然后按照key=value&key=value拼接得到字符串
    //2.将上一步得到的字符串进行sha256编码得到字符串
    //3.将上一步得到的字符使用对方公钥进行加密得到签名
    //4.收到请求方解密出签名内容
    //5.将参数按照步骤1进行拼接
    //6.将上一步结果进行步骤2得到字符串
    //7.匹配传过来后解密的签名和自己生成的签名是否匹配

    /** 获取签名
     * @param object
     * @return
     * @throws GeneralSecurityException
     */
    public static String getSign(Object object) throws GeneralSecurityException {

        String prestr = JSONObject.toJSONString(object,SerializerFeature.SortField);//assembleParam(requestParam);
        //将结果进行sha256编码
        String sha256Sign = SHA256Utils.getSHA256(prestr);
        System.out.println("待签名的内容:"+sha256Sign);
        return encrypt(sha256Sign,publicKey);
    }

    /** 将参数的key按照ASCII码顺序进行排序，然后拼接 key=value&key=value  直至拼接完所有参数和值，如果值为空串或者null，不参与拼接
     * @param requestParam 参数
     * @return
     */
    public static String assembleParam(Map<String,String> requestParam ){
        List<String> keys = new ArrayList<String>(requestParam.keySet());
        Collections.sort(keys);

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = requestParam.get(key);
            if(value == null || "".equals(value)){
                continue;
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**验证签名
     * @param object
     * @param sign 签名
     * @return
     * @throws GeneralSecurityException
     */
    public static Boolean validateSign(Object object,String sign) throws GeneralSecurityException {
        //拿到参数后解码
        String decryptSign = decrypt(sign, privateKey);
        System.out.println("解密签名内容:"+decryptSign);
        String assembleParamStr = JSONObject.toJSONString(object,SerializerFeature.SortField);//assembleParam(receiveParam);
        String mySign = SHA256Utils.getSHA256(assembleParamStr);
        System.out.println("正确签名内容:"+mySign);
        return decryptSign.equals(mySign);
    }

    /** RSA加密
     * @param data 数据
     * @param publicKey 公钥
     * @return
     * @throws GeneralSecurityException
     */
    public static String encrypt(String data, String publicKey) throws GeneralSecurityException {

        EncodedKeySpec keySpec = new X509EncodedKeySpec(base64Decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(keySpec));
        return base64Encode(cipher.doFinal(data.getBytes()));
    }

    /** RSA解密
     * @param data 数据
     * @param privateKey 私钥
     * @return
     * @throws GeneralSecurityException
     */
    public static String decrypt(String data, String privateKey) throws GeneralSecurityException {

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(base64Decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(keySpec));
        return new String(cipher.doFinal(base64Decode(data)));
    }


    static String base64Encode(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    static byte[] base64Decode(String str) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}