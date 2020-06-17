package com.biao.study.apollo.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SampleRedisConfig
 * @Description: TODO
 * @Author Biao
 * @Date 2020/6/17
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "redis.cache")
public class SampleRedisConfig {
    private int expireSeconds;
    private int commandTimeout;

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public int getCommandTimeout() {
        return commandTimeout;
    }
}
