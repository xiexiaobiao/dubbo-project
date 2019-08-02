package com.example.consumer.conf;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @Classname ConsumerConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-07-31 22:38
 * @Version 1.0
 **/

/*
   @EnableDubbo
   Enables Dubbo components as Spring Beans, equals
   {@link DubboComponentScan} and {@link EnableDubboConfig} combination.
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.example.consumer")
@PropertySource("classpath:/spring/consumer_properties")
public class ConsumerConf {

    /**
    * @Description: TODO
    * @param null
    * @params 
    * @return 
    * @author xiaobiao
    * @date 2019-08-01 19:08 
    */
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setClient("curator");//使用curator连接zk
        registryConfig.setAddress("zookeeper://localhost:2181");
        return registryConfig;
    }

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("springboot-dubbo-consumber");
        return applicationConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        consumerConfig.setCheck(false);//服务检查，可以先false，防止有些提供的服务还没有，会报错，
        return consumerConfig;
    }

}
