package com.example.provider.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname NacosConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-01 21:58
 * @Version 1.0
 **/
@Configuration
@Configurable
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "dubbo-config-center-nacos.properties",autoRefreshed = true,groupId = "DEFAULT_GROUP")
public class NacosConfigCenterConf {

}
