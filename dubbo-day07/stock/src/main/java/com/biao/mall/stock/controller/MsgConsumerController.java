package com.biao.mall.stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Classname MsgConsumerController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-11 11:56
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class MsgConsumerController {

    private static Logger logger = LoggerFactory.getLogger(MsgConsumerController.class);

    @PostMapping("/userInfo")
    public String getUserInfo(@RequestBody Map<String,Object> params){
        logger.info("received params >>>> "+ params);
        return "R.ok()";
    }
}
