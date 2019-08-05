package com.example.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.common.api.GreetingService;
import com.example.common.constant.AnnotationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DemoController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-07-31 22:44
 * @Version 1.0
 **/
@RestController
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Reference(interfaceClass = GreetingService.class,version = AnnotationConstants.VERSION)
    private GreetingService greetingService;

    @RequestMapping("/hello")
    public String hello(){
        logger.info(this.getClass().getName().toString()+"called succefully");
        return greetingService.greeting("xiexiaobiao");
    }

}
