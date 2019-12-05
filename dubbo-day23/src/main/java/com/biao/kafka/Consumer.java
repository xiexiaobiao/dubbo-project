package com.biao.kafka;

import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
//@Slf4j
public class Consumer {

    private Logger log = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(id = "foo",groupId = "test-consumer-group",topics = "HelloWorld")
    public void listen(ConsumerRecord<?,?> records){
        Optional<?> msg = Optional.ofNullable(records.value());
        if (msg.isPresent()){
            Object data = msg.get();
            log.info("ConsumerRecord >>>>>> {}", records);
            log.info("Record Data >>>>>> {}", data);
        }
    }
}
