package com.biao.mall.common.service;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;


public interface RocketMQService {

    SendResult sendTransMsg(Message msg) throws MQClientException;

    void consumeConcurrentMsg() throws MQClientException;

}
