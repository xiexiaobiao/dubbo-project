package com.biao.mall.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Classname MQConsumeMsgListenerProcessor
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-31 21:55
 * @Version 1.0
 **/
@Component
@Slf4j
public class RocketConsumeMsgListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(RocketConsumeMsgListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext context) {
            if(CollectionUtils.isEmpty(msgs)){
                logger.info("received msg is empty!");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            MessageExt messageExt = msgs.get(0);
            String msg = new String(messageExt.getBody());
            logger.info("received msg is>> "+msg);
            if(messageExt.getTopic().equals("MallTopic")){
                if(messageExt.getTags().equals("order_tag")){
                    int reconsumeTimes = messageExt.getReconsumeTimes();
                    if(reconsumeTimes == 3){
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    //todo 业务处理
                    /**消息可能会重复消费，务必注意这里的幂等处理，如使用order_id判断是否已经处理过*/
                    logger.debug("mock the finance moudle received msg>> "+ msg);
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
