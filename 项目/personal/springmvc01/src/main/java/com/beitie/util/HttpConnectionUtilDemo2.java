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
    public static InputStream invokeInterface() throws InterruptedException, IOException {
        HttpURLConnection connection;
        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
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
