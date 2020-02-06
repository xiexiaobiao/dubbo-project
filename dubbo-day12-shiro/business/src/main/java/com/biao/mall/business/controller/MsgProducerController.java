package com.biao.mall.business.controller;


import com.biao.mall.common.conf.RabbitConf;
import com.biao.mall.common.service.MsgProducer;
import com.biao.mall.common.util.RedisUitl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Classname MsgProducerController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-11 11:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("/business")
public class MsgProducerController {

    private static Logger logger = LoggerFactory.getLogger(MsgProducerController.class);
    private AmqpTemplate amqpTemplate;
    private MsgProducer msgProducer;
    private RedisUitl redisUitl;

    @Autowired
    public MsgProducerController(AmqpTemplate amqpTemplate,MsgProducer msgProducer,
                                 RedisUitl redisUitl){
        this.amqpTemplate = amqpTemplate;
        this.msgProducer = msgProducer;
        this.redisUitl = redisUitl;
    }

    @RequestMapping("/msg")
    public String send2Queue(){
        String content = "time: "+ System.currentTimeMillis();
        amqpTemplate.convertAndSend(RabbitConf.QUEUE_A,content);
        return "R.ok()";
    }

    @RequestMapping("/msg2")
    public String send2Exchange(){
        String content = "time: "+ System.currentTimeMillis();
        amqpTemplate.convertAndSend(RabbitConf.EXCHANGE_A,RabbitConf.X_ROUTING_KEY_A2C,content);
        return "R.ok()";
    }

    @PostMapping("/msg3")
    public String send2(@RequestBody HashMap<String,Object> params){
        logger.info("params: "+params);
        String content = System.currentTimeMillis() + "";
        params.put("currentTimeMillis >>> ",content);
        msgProducer.sendMapMsg(params);
        return "R.ok()";
    }

    @RequestMapping("/redis")
    public String redis(){
        StringBuilder stringBuilder = new StringBuilder(1200);
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("/\n"+redisUitl.doGetId("DISTRIBUTE"));
        }
        return stringBuilder.toString();
    }

    @RequestMapping("/redis2")
    public String shuffle(){
        StringBuilder stringBuilder = new StringBuilder(1200);
        for (int i = 0; i < 5; i++) {
            stringBuilder.append("/\n"+redisUitl.getShuffleId());
        }
        return stringBuilder.toString();
    }

}
