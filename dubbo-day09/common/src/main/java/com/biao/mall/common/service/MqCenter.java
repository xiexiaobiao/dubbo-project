package com.biao.mall.common.service;

import com.biao.mall.common.conf.RabbitConf;
import com.biao.mall.common.util.RestfulUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @Classname MqCenter 消息中心,1,接收来自服务消息 2,发送消息到MQ 3,监听来自MQ的消息
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-25 13:36
 * @Version 1.0
 **/
@Component
public class MqCenter implements RabbitTemplate.ConfirmCallback{

    private final Logger logger = LoggerFactory.getLogger(MqCenter.class);

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     * */
    @Autowired
    public MqCenter(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //Only one ConfirmCallback is supported by each RabbitTemplate
        rabbitTemplate.setConfirmCallback(this);//rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    public void sendMsg2MQ(Map<String,Object> map){
        //每个消息的唯一ID值,用于消息幂等和防丢
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //convertAndSend是一个多态方法，可以发送Object对象类型,可发给exchange或queue
        rabbitTemplate.convertAndSend(RabbitConf.EXCHANGE_A,RabbitConf.DLX_ROUTING_KEY_A2A,map,correlationData);
    }

    // 回调方法
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" callback confirm id>>>>" + correlationData);
        if (ack){
            logger.info("msg has been consumed successfully.>>>>");
        }else {
            logger.info("msg consumed failed>>>>" + cause);
        }
    }

    //@RabbitListener也可以放到MqCenter类上做注解,方法上可以监听不同的queue
    @RabbitListener(queues = RabbitConf.QUEUE_A,containerFactory = "customContainerFactory")//指定监听的队列
    @RabbitHandler //消息处理方法,参数对应于MQ发送者
    public void process(Map<String,Object> map){
        logger.info("MQ center handle  received msg >>>> " + map + "\n  current thread name >>>>"
                + Thread.currentThread().getName()
                + "\n thread id >>>>" + Thread.currentThread().getId());
/*        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        String resultStr = HttpUtils.doPost(URL,jsonObject);*/
        String jsonString = map.get("data").toString();
        //统一使用rest发送
        logger.debug("MQ center send to receiver>>"+jsonString);
        RestfulUtil.sendMsgPost(jsonString);
    }

}
