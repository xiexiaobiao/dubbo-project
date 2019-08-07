package com.example.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Classname ConsumerApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-07-31 22:32
 * @Version 1.0
 **/
@SpringBootApplication
//@DubboComponentScan(basePackages = "com.example.consumer") //Enables Dubbo components as Spring Beans @since 2.5.7,also can use @EnableDubbo instead
public class ConsumerApplication {
    //
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
        System.out.println("Consumer Application started.");
    }
}

