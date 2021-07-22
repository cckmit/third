package org.opoo.util;

import org.apache.commons.codec.binary.Base64;

public class EncodeUtils {

    public EncodeUtils() {
    }

    public static byte[] urlsafeEncodeBytes(byte[] src) {
        if(src.length % 3 == 0) {
            return _$1(src);
        } else {
            byte[] b = _$1(src);
            if(b.length % 4 == 0) {
                return b;
            } else {
                int pad = 4 - b.length % 4;
                byte[] b2 = new byte[b.length + pad];
                System.arraycopy(b, 0, b2, 0, b.length);
                b2[b.length] = 61;
                if(pad > 1) {
                    b2[b.length + 1] = 61;
                }

                return b2;
            }
        }
    }

    public static String urlsafeEncodeString(byte[] src) {
        return new String(urlsafeEncodeBytes(src));
    }

    public static String urlsafeEncode(String text) {
        return new String(urlsafeEncodeBytes(text.getBytes()));
    }

    private static byte[] _$1(byte[] src) {
        byte[] b64 = Base64.encodeBase64(src);

        for(int i = 0; i < b64.length; ++i) {
            if(b64[i] == 47) {
                b64[i] = 95;
            } else if(b64[i] == 43) {
                b64[i] = 45;
            }
        }

        return b64;
    }
}
