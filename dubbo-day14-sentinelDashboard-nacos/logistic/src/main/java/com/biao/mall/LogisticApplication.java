package com.biao.mall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname LogisticApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-17 22:44
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDubbo
public class LogisticApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogisticApplication.class,args);
        System.out.println("Logistic Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
