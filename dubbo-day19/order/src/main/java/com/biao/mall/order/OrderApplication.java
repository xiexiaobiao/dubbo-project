package com.biao.mall.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname OrderApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-05 09:16
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDubbo
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
        System.out.println("Order Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
