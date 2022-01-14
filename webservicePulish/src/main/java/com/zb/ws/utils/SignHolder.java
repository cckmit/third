package com.zb.ws.utils;

public class SignHolder {


    private static final ThreadLocal<String> SIGN_HOLDER = new ThreadLocal<String>();

    public static void setSign(String sign){
        SIGN_HOLDER.set(sign);
    }

    public static String takeSign(){
        return SIGN_HOLDER.get();
    }

    public static void clearSign(){
        SIGN_HOLDER.remove();
    }
}