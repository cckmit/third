package com.beitie.stream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/12
 */
public class PhotoEncodeBase64 {
    public static void main(String[] args) throws IOException {
        PhotoEncodeBase64 photoEncodeBase64 = new PhotoEncodeBase64();
        photoEncodeBase64.fromBase64ToFile(photoEncodeBase64.toBase64());
    }

    public String toBase64() throws IOException {
        File file = new File("d:/1634909675.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte [] b = new byte[inputStream.available()];
        inputStream.read(b);
        BASE64Encoder encoder = new BASE64Encoder();
        String base64=encoder.encode(b).toString();
        System.out.println(base64);
        inputStream.close();
        return base64;
    }
    public void fromBase64ToFile(String photoStr) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte [] b = decoder.decodeBuffer(photoStr);
        FileOutputStream outputStream = new FileOutputStream("d:/xiaoxiao.jpg");
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
    }
}
