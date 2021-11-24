package com.beitie.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/4
 */
public class HttpConnectionUtil {
    private static final String URL = "http://112.48.134.27:8888/gisBoot/filePermission/fileDownload?cipherStr=7bBJVdi0aTFrTPd8LN1KKX9x4ma4lcx5Mci%2F9jRwpI6Sy5jzblEQ2udXUq8m46sYFAQyWaLzAQt%2B%2Bw1AkgKx2YnZMrkeXStw5AMyj2%2BXCTQ%3D";

    public static void main(String[] args) throws InterruptedException, IOException {
        invokeInterface();
    }
    public static void invokeInterface() throws InterruptedException, IOException {
        HttpURLConnection connection;
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("cipherStr","7bBJVdi0aTFrTPd8LN1KKX9x4ma4lcx5Mci%2F9jRwpI6Sy5jzblEQ2udXUq8m46sYFAQyWaLzAQt%2B%2Bw1AkgKx2YnZMrkeXStw5AMyj2%2BXCTQ%3D");
            // 设置是否想请求中输出参数 , 因为是Post方式请求，所以必须设置为true才能输出参数
            //  同时如果通过输出参数
//            connection.setDoOutput(true);
//            connection.setRequestProperty("","");
//            connection.addRequestProperty("","");
//            String msg = "we are good children";
            // 一旦调用getOutputStream方法，connection的RequestMehtod将自动变为Post，而不管你手动设置为什么方式
//            connection.getOutputStream().write(msg.getBytes());
            // 一旦建立连接，就相当于发送了请求，设置连接的其他属性就是去意义了
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                FileOutputStream file = new FileOutputStream("e:/111.jpg");

                int total = connection.getContentLength();
//                byte [] bytesTotal = new byte[connection.getContentLength()];
                int length = 0;
                while( inputStream.available() >0){
                    length = inputStream.available();
                    byte [] readByte= new byte[length];
                    inputStream.read(readByte);
                    file.write(readByte);
                    Thread.sleep(100);
                }
                inputStream.close();
                file.flush();
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
