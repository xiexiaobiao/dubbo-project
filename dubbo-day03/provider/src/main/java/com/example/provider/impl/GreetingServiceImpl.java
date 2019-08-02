package com.example.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.common.api.GreetingService;
import com.example.common.constant.AnnotationConstants;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Classname ProviderImpl
 * @Description
 * @Author xiexiaobiao
 * @Date 2019-07-31 17:39
 * @Version 1.0
 **/

//一定注意这里的service类别
@Service(version = AnnotationConstants.VERSION)
public class GreetingServiceImpl implements GreetingService, Serializable {

    @Override
    public String greeting(String name) {
        System.out.println("provider received invoke of greeting: " + name);
        sleepWhile();
        return name+" Congratulations! Dubbo is working now!";
    }

    private void sleepWhile() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
