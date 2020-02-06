package com.biao.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname KafkaApplication
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/2 12:24
 * @Version 1.0
 **/
@SpringBootApplication
public class KafkaApplication {
/*
    private Consumer consumer;
    private Producer producer;

    @Autowired
    public KafkaApplication(Consumer consumer,Producer producer){
        this.consumer = consumer;
        this.producer = producer;
    }
*/

    public static void main(String[] args) throws InterruptedException {
        System.out.println("KafkaApplication started >>>>>>");
        ConfigurableApplicationContext context = SpringApplication.run(KafkaApplication.class,args);
        Producer producer = context.getBean(Producer.class);
        producer.send();
    }
}
