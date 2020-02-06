package com.biao.mall.business.conf;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SentinelAspectConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-12 20:58
 * @Version 1.0
 **/
@Configuration
public class SentinelAspectConf {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect();
    }
}
