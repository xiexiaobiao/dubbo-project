package com.example.provider;

import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.CountDownLatch;

/**
 * @Classname ProviderApplication
 * @Description
 * @Author xiexiaobiao
 * @Date 2019-07-30 22:57
 * @Version 1.0
 **/
@SpringBootApplication
@DubboComponentScan(basePackages = "com.example.provider")
public class ProviderApplication {
        public static void main(String[] args) throws Exception{
            SpringApplication.run(ProviderApplication.class, args);
            System.out.println("dubbo service started.");
//            new CountDownLatch(1).await();
        }

}
