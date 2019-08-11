package com.biao.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname BusinessApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-07 21:53
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan({"com.biao.mall.*.mapper"})
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class,args);
        System.out.println("Stock Application started.");
    }
}
