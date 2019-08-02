package com.example.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname controller
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-01 22:21
 * @Version 1.0
 **/
@RestController
@RequestMapping("/config")
public class ConfigTest {

    @Value("${user.name}")
    private String name;

    public void setName(String name){
        this.name = name;
    }

    @RequestMapping(value = "/name",method = RequestMethod.GET)
    public String getConfigCenter(){
        return name;
    }
}
