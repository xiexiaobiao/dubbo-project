package com.biao.mall.business;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname BusinessApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-05 09:16
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDubbo
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class,args);
        System.out.println("Business Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
