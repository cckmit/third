package com.beitie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author betieforever
 * @description 描述
 * @date 2021/8/22
 */
@EnableBinding(Source.class)
public class JMessageSenderImpl implements JMessageSender{
    @Resource
    private MessageChannel output;
    @Override
    public String send() {
        String msg= UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(msg).build());
        System.out.println("---msg-----"+msg);
        return msg;
    }
}
