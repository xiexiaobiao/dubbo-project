package com.example;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @Classname ProviderApplication
 * @Description
 * @Author xiexiaobiao
 * @Date 2019-07-30 22:57
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.example")
@DubboComponentScan(basePackages = "com.example")
public class ProviderApplication {
        public static void main(String[] args){
            SpringApplication.run(ProviderApplication.class, args);
            System.out.println("dubbo service provider started.");
        }

}
