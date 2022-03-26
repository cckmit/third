package com.beitie.controller;

import com.beitie.util.HttpConnectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/11/5
 */
@Controller
public class StreamController {
    @RequestMapping("/getImage")
    public void getImage(HttpServletResponse response) throws IOException, InterruptedException {
        InputStream inputStream = HttpConnectionUtil.invokeInterface();
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        while(inputStream.available() > 0){
            int length= inputStream.available();
            byte [] bytes = new byte[length];
            inputStream.read(bytes);
            out.write(bytes);
            Thread.sleep(100);
        }
        inputStream.close();
        out.flush();
        out.close();

    }
    @RequestMapping("/getImage2")
    public void getImage2(HttpServletResponse response) throws IOException, InterruptedException {
        InputStream inputStream = HttpConnectionUtil.invokeInterface();
        byte [] bytes = new byte[270469];
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int total = 0;
        int read = 0;
        while(total < 270469 && read >=0){
            read =inputStream.read(bytes);
            out.write(bytes,0,read);
            out.write(bytes);
            out.flush();
            total += read;
        }
//        out.write(bytes);
        inputStream.close();
        out.close();

    }
}
