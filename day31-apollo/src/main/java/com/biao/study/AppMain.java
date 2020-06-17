package com.biao.study;

import com.biao.study.apollo.spring.ConfigBean;
import com.biao.study.apollo.spring.SampleRedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ConfigBean configBean;
    private SampleRedisConfig redisConfig;

    @Autowired
    public AppMain(ConfigBean configBean,SampleRedisConfig redisConfig){
        this.configBean = configBean;
        this.redisConfig = redisConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class,args);
        System.out.println("AppMain app started >>>>>>>>>>>>");
    }

/*    @RequestMapping("/jenkins/{name}")
    public String hello(@PathVariable(name = "name") String name){
        System.out.println("Variable： "+ name);
        return "hello, " + name;
    }*/

    @RequestMapping("/config")
    public String apollo(){
        System.out.println("config 1 >>>> " + configBean.getSchool() +"/"+ configBean.getTime());
        System.out.println("config 2 >>>> " + redisConfig.getCommandTimeout()+"/"+ redisConfig.getExpireSeconds());
        return "config >>>> " + configBean.getSchool() +"/"+ configBean.getTime();
    }
}
