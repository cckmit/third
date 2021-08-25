package com.beitie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/23
 */
@EnableBinding(Sink.class)
public class ReceivedMsgController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void receiveMsg(String msg){
        System.out.println("---consumer received msg---"+msg+"\t端口号"+serverPort);
    }

}
