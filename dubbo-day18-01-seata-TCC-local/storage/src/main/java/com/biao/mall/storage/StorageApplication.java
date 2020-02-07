package com.biao.mall.storage;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname StorageApplication
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-05 09:16
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDubbo
@MapperScan("com.biao.mall.storage.dao")
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class,args);
        System.out.println("Storage Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
