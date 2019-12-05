package com.biao.kafka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
//@Slf4j
public class Producer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private Logger log = LoggerFactory.getLogger(Producer.class);
    private String time = LocalDateTime.now().toString();
    private final String msg = "THIS IS MESSAGE CONTENT " + time;

    public void send() throws InterruptedException {
        log.info("send message is {}",this.msg);
        Thread.sleep(1000L);
        // kafkaTemplate.sendDefault() 为异步方法，返回 ListenerFuture<T>，
        kafkaTemplate.send("HelloWorld","test-key",this.msg);
    }
}
