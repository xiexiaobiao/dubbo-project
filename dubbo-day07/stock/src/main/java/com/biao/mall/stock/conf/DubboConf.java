package com.biao.mall.stock.conf;

import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Classname DubboConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-13 21:04
 * @Version 1.0
 **/
@Configuration
//@EnableDubbo(scanBasePackages = "com.biao.mall.stock.impl")
@EnableDubbo // 此注解包括了@EnableDubboConfig @DubboComponentScan
@PropertySource("classpath:/spring/dubbo-stock-provider.properties")//注意properties里不要有和下面使用API方式的冲突。
public class DubboConf {

    //这里使用api方式，也可在properties文件中配置
    @Bean
    public ProviderConfig providerConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return  providerConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://localhost:2181");
        registryConfig.setClient("curator");//使用curator连接zk
        return  registryConfig;
    }

    //>>>>>>>>> java.lang.IllegalStateException: Duplicate application configs:
/*    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("sprintboot-dubbo-provider");
        return applicationConfig;
    }*/

    //properties 已有配置
/*    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(20880);
        protocolConfig.setName("dubbo");
        return protocolConfig;
    }*/
}
