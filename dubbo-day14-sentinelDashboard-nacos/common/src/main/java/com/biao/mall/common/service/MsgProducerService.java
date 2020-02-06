package com.biao.mall.common.service;

import com.biao.mall.common.conf.RabbitConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @Classname MsgProducer
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-08 21:54
 * @Version 1.0
 **/
@Component
public class MsgProducerService implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(MsgProducerService.class);

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     * */
    @Autowired
    public MsgProducerService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //Only one ConfirmCallback is supported by each RabbitTemplate
        rabbitTemplate.setConfirmCallback(this);//rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    public void sendMapMsg(Map<String,Object> map){
        //每个消息的唯一ID值,用于消息幂等和防丢
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //convertAndSend是一个多态方法，可以发送Object对象类型,可发给exchange或queue
        rabbitTemplate.convertAndSend(RabbitConf.EXCHANGE_A, RabbitConf.X_ROUTING_KEY_A2C,map,correlationData);
    }

    public void sendJsonMsg(Object jsonStr){
        //每个消息的唯一ID值,用于消息幂等和防丢
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //convertAndSend是一个多态方法，可以发送Object对象类型,可发给exchange或queue
        rabbitTemplate.convertAndSend(RabbitConf.EXCHANGE_A, RabbitConf.X_ROUTING_KEY_A2A,jsonStr,correlationData);
    }

    // 回调重写
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" callback confirm id>>>>" + correlationData);
        if (ack){
            logger.info("msg has been consumed successfully.>>>>");
        }else {
            logger.info("msg consumed failed>>>>" + cause);
        }
    }
}
