package com.beitie.interfaceInvoke;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/4
 */
public class HttpConnectionUtilDemo2 {
    private static final String URL = "http://localhost:8080/msg.action";

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
            //post方法设置参数传递的方式
            String params="userName=zhangsan&password=lisi";

            connection.getOutputStream().write(params.getBytes());
            //向请求的头部MessageHeader添加属性，在request中可以通过 request.getHeader("userN")来获取头部属性信息
            connection.setRequestProperty("userN","useNProperty");
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
