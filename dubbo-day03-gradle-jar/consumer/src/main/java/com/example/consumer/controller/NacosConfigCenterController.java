package com.example.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname NacosConfigCenterController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-02 22:11
 * @Version 1.0
 **/
@RestController
public class NacosConfigCenterController {
    @Value("${consumer.name}")
    private String configStr;

    private void setConfigStr(String configStr){
        this.configStr = configStr;
    }

    @RequestMapping("/config")
    public String getConfig(){
        return configStr;
    }
}
