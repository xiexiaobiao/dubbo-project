package com.biao.mall.common.impl;

import com.biao.mall.common.component.RocketConsumeMsgListener;
import com.biao.mall.common.service.RocketMQService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname RocketMQServiceImpl
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-01 09:57
 * @Version 1.0
 **/
@Service
public class RocketMQServiceImpl implements RocketMQService {

    private TransactionMQProducer producer;
    private DefaultMQPushConsumer consumer;
    private RocketConsumeMsgListener listener;

    @Autowired
    public RocketMQServiceImpl(TransactionMQProducer producer, DefaultMQPushConsumer consumer,
            RocketConsumeMsgListener listener){
        this.producer = producer;
        this.consumer = consumer;
        this.listener = listener;
    }

    @Override
    public SendResult sendTransMsg(Message msg) throws MQClientException {
        SendResult sendResult = producer.sendMessageInTransaction(msg,null);
        return sendResult;
    }

    @Override
    public void consumeConcurrentMsg() throws MQClientException {
        /**特别注意！！！不是 consumer.setMessageListener(listener);*/
        consumer.registerMessageListener(listener);
    }
}
