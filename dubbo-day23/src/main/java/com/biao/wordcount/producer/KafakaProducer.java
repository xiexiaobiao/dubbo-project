package com.biao.wordcount.producer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @Classname KafakaProducer
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/1 19:15
 * @Version 1.0
 **/
@SpringBootApplication
public class KafakaProducer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);
//        KafkaTemplate<Integer,String> kafkaTemplate = (KafkaTemplate<Integer, String>) context.getBean(KafkaTemplate.class);
        KafkaTemplate<Integer,String> kafkaTemplate = (KafkaTemplate<Integer, String>) context.getBean(KafkaTemplate.class);
        LocalDateTime time = LocalDateTime.now();
        String data = "MSG CONTENT -> " + time ;
        // send(String topic, K key, @Nullable V data)
        ListenableFuture<SendResult<Integer,String>> send = kafkaTemplate.send("streams-plaintext-input", 1, data);
        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(">>>>>>> kafka message send failure");
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                System.out.println(">>>>>>> kafka message send successfully");
            }
        });
    }
}
