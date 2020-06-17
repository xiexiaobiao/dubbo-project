package com.biao.study.apollo.spring;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Config
 * @Description: TODO
 * @Author Biao
 * @Date 2020/6/17
 * @Version V1.0
 **/

/** 指示Apollo注入FX.apollo和application.yml中的namespace的配置到Spring环境中，并且顺序为 1
 * 如果还有其他 config 类，可以根据顺序决定加载次序
 * @author Biao*/
@Configuration
//@EnableApolloConfig(value = {"FX.apollo", "application.yml"}, order = 1)
public class SomeConfig {

    @Bean
    public ConfigBean getConfigBean(){
        return new ConfigBean();
    }

    @Bean
    public SampleRedisConfig sampleRedisConfig() {
        return new SampleRedisConfig();
    }
}
