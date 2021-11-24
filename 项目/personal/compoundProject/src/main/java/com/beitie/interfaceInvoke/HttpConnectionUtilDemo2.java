package com.beitie.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/4
 */
public class HttpConnectionUtilDemo2 {
    private static final String URL = "http://localhost:8080/msg";

    public static void main(String[] args) throws InterruptedException, IOException {
        invokeInterface();
    }
    public static InputStream invokeInterface() throws InterruptedException, IOException {
        HttpURLConnection connection;
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            String params="userName=zhangsan&password=lisi";
            connection.setRequestProperty("userN","useNProperty");
            connection.getOutputStream().write(params.getBytes());
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return connection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
