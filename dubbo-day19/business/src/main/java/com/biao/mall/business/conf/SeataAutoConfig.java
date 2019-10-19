package com.biao.mall.business.conf;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname SeataAutoConfig
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-07 11:14
 * @Version 1.0
 **/
@Configuration
public class SeataAutoConfig {
    /**
     * init global transaction scanner
     * @Return: GlobalTransactionScanner
     */
    @Bean
    public GlobalTransactionScanner globalTransactionScanner(){
        return new GlobalTransactionScanner("dubbo-seata-at-springboot", "my_test_tx_group");
    }
}
