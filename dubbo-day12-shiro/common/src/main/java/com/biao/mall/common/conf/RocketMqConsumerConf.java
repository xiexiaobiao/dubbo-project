package com.biao.mall.common.conf;

import com.biao.mall.common.component.RocketConsumeMsgListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RocketMqConsumerConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-29 22:08
 * @Version 1.0
 **/
@Configuration
//@Slf4j
public class RocketMqConsumerConf {
    private static final Logger log = LoggerFactory.getLogger(RocketMqConsumerConf.class);
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    private RocketConsumeMsgListener mqMessageListenerProcessor;

    @Autowired
    public RocketMqConsumerConf(RocketConsumeMsgListener mqMessageListenerProcessor){
        this.mqMessageListenerProcessor = mqMessageListenerProcessor;
    }

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception{

        if (StringUtils.isEmpty(groupName)){
            throw new Exception();
        }
        if (StringUtils.isEmpty(namesrvAddr)){
            throw new Exception();
        }
        if(StringUtils.isEmpty(topics)){
            throw new Exception();
        }
        //MQPushConsumer注意这里是push，还有pull类型DefaultMQPullConsumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        //加入一个消息监听器，监听消息消费情况
        consumer.registerMessageListener(mqMessageListenerProcessor);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，
             * 则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
        	/*String[] topicTagsArr = topics.split(";");
        	for (String topicTags : topicTagsArr) {
        		String[] topicTag = topicTags.split("~");
        		consumer.subscribe(topicTag[0],topicTag[1]);
			}*/
            consumer.subscribe("MallTopic", "*");
            consumer.start();
            log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}",groupName,topics,namesrvAddr);
        } catch (Exception e) {
            throw new Exception();
        }
        return consumer;
    }

}

