package com.biao.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AppMain
 * @Description: TODO
 * @Author Biao
 * @Date 2020/5/30
 * @Version V1.0
 **/
@SpringBootApplication
@RestController
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class,args);
        System.out.println("AppMain app started >>>>>>>>>>>>");
    }

    @RequestMapping("/jenkins/{name}")
    public String hello(@PathVariable(name = "name") String name){
        System.out.println("Variable： "+ name);
        return "hello, " + name;
    }
}
