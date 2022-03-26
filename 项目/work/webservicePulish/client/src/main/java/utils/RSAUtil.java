package utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


public class RSAUtil {
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";

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
        List<String> keys = new ArrayList<>(requestParam.keySet());
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

        return Base64.getEncoder().encodeToString(bytes);
    }

    static byte[] base64Decode(String str) {

        return Base64.getDecoder().decode(str);
    }
}