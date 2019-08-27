package com.biao.mall.logistic.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Classname DubboLogisticConsumerConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-24 11:13
 * @Version 1.0
 **/
@Configuration
@PropertySource("classpath:/spring/dubbo-logistic-consumer.properties")
public class DubboLogisticConsumerConf {

/*    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("logistics-consumer");//可配置于properties中
        applicationConfig.setQosPort(22226);//也可配置于properties中
        return applicationConfig;
    }*/

/*    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setClient("curator");//使用curator连接zk
        registryConfig.setAddress("zookeeper://localhost:2181"); //配置到properties中
        return registryConfig;
    }*/

/*    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        consumerConfig.setCheck(false);//服务检查，可以先false，防止有些服务还没有注册，会报错，
        return consumerConfig;
    }*/
}
