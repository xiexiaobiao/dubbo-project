package com.biao.mall;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname stock
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-06 21:14
 * @Version 1.0
 **/
@SpringBootApplication
//@MapperScan({"com.biao.mall.*.mapper"})
@DubboComponentScan("com.biao.mall.stock") //不能去掉，否则扫描不到dubbo的@Service
public class StockApplication {
    public static void main(String[] args){
        SpringApplication.run(StockApplication.class, args);
        System.out.println("Stock Application started.>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
