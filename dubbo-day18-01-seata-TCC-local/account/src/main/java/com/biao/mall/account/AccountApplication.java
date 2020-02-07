package com.biao.mall.account;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname AccountApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-05 09:16
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDubbo
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
        System.out.println("Account Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
