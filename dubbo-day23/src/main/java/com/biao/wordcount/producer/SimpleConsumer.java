package com.biao.wordcount.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname SimpleConsumerLister
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/1 19:22
 * @Version 1.0
 **/
@Component
public class SimpleConsumer {
    private Logger log = LoggerFactory.getLogger(SimpleConsumer.class);
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @KafkaListener(id = "foo",topics = "streams-wordcount-output")
    public void listen(byte[] records){
        System.out.println("records is >>>> "+ records);
        this.countDownLatch.countDown();
        log.debug("consume successfully!");
    }
    //在WordCountApplication实例中，无法打印流结果，因为需要格式化
/*    public void listen(ConsumerRecord<?,?> records){
        Optional<?> msg = Optional.ofNullable(records.value());
        if (msg.isPresent()){
            Object data = msg.get();
            log.info("Consumer Record >>>>>> {}", records);
            log.info("Record Data >>>>>> {}", data);
        }
    }*/
}
