package com.biao.mall.business.conf;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Classname DubboConsumerConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-16 18:41
 * @Version 1.0
 **/
@Configuration
@EnableDubbo // 此注解包括了@EnableDubboConfig @DubboComponentScan
@PropertySource("classpath:/spring/dubbo-business.properties")
public class DubboConsumerConf {

/*    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("business-module");//可配置于properties中
        applicationConfig.setQosPort(2223);//也可配置于properties中
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        registryConfig.setClient("curator");//使用curator连接zk
        return registryConfig;
    }*/

    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        consumerConfig.setCheck(false);//服务检查，可以先false，防止有些服务还没有注册，会报错，
        return consumerConfig;
    }

/*    @Bean(name = "businessProvider")
    public ProviderConfig providerConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return  providerConfig;
    }*/
/*
    @Bean
    public ServiceConfig serviceConfig(){
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setInterface(DubboOrderService.class);
        return serviceConfig;
    }*/


}
