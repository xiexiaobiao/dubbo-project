package com.biao.mall.common.conf;

import com.biao.mall.common.exception.RocketMQException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @Classname RocketMqConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-29 20:43
 * @Version 1.0
 **/
@Configuration
//@Slf4j
public class RocketTransMqProducerConf {
    private final static Logger log = LoggerFactory.getLogger(RocketTransMqProducerConf.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;
    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize ;
    /**
     * 消息发送超时时间，默认3秒
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Bean
    public TransactionMQProducer transactionMQProducer(){
        if (StringUtils.isEmpty(this.groupName)){
            throw new RocketMQException("groupName is blank");
        }
        if (StringUtils.isEmpty(this.namesrvAddr)){
            throw new RocketMQException("nameServerAddr is blank");
        }
        //MQProducer有几种，这里因使用事务型，还有DefaultMQProducer
        TransactionMQProducer transactionMQProducer;
        transactionMQProducer = new TransactionMQProducer(this.groupName);
        transactionMQProducer.setNamesrvAddr(this.namesrvAddr);
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);
        if (this.maxMessageSize != null){
            transactionMQProducer.setMaxMessageSize(this.maxMessageSize);
        }
        if (this.sendMsgTimeout != null){
            transactionMQProducer.setSendMsgTimeout(this.sendMsgTimeout);
        }
        if (this.retryTimesWhenSendFailed !=null){
            transactionMQProducer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        }
        try {
            transactionMQProducer.start();
        }catch (MQClientException e){
            log.error(e.getMessage());
            throw new RocketMQException(e.getErrorMessage());
        }
        return transactionMQProducer;
    }
}
