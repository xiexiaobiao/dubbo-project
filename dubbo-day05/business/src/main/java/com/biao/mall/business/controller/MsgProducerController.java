package com.biao.mall.business.controller;


import com.biao.mall.common.conf.RabbitConf;
import com.biao.mall.business.service.MsgProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MsgProducerController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-11 11:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("/producer")
public class MsgProducerController {

    private static Logger logger = LoggerFactory.getLogger(MsgProducerController.class);

    private AmqpTemplate amqpTemplate;
    private MsgProducer msgProducer;

    @Autowired
    public MsgProducerController(AmqpTemplate amqpTemplate,MsgProducer msgProducer){
        this.amqpTemplate = amqpTemplate;
        this.msgProducer = msgProducer;
    }

    @RequestMapping("/msg")
    public String send(){
        String content = "来自交易模块。time: "+ System.currentTimeMillis();
        amqpTemplate.convertAndSend(RabbitConf.QUEUE_A,content);
        return "R.ok()";
    }
    @PostMapping("/msg2")
    public String send2(@RequestBody HashMap<String,Object> params){
        logger.info("来自交易模块。 params: "+params);
        msgProducer.sendMsg(params);
        return "R.ok()";
    }

}
