package com.biao.mall.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/*
* **
Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
Queue:消息的载体,每个消息都会被投到一个或多个队列。
Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
Channel:消息通道,在客户端的每个连接里,可建立多个channel.
/**
 * @Classname RabbitConf
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-08 20:39
 * @Version 1.0
 **/
@Configuration
public class RabbitConf {

private final Logger logger = LoggerFactory.getLogger(RabbitConf.class);

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String V_HOST= "/mall";
    public static final String EXCHANGE_A = "mall.exchange_A";
    public static final String QUEUE_A = "mall.queue_A";
    public static final String DLX_ROUTING_KEY_A = "mall.routingKey_A";
    public static final String DLX_EXCHANGE_B = "mall.dlx.exchange_B";
    public static final String DLQ_QUEUE_B = "mall.dlq.queue_B";

    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(V_HOST);
        //Use full publisher confirms, with correlation data and a callback for each message.
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_A );
    }

    //DLQ队列就是一个普通队列，只是由于其用于接收死信消息，故称为DLQ
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange(DLX_EXCHANGE_B);
    }

    @Bean
    public Queue queueA(){
        Map<String,Object> args = new HashMap<>(3);
        //dlx exchange statement
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_B);
        //dlx routing key statement
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY_A);
        //3分钟
        args.put("x-message-ttl", 18000);
        //注意这里的参数
        return new Queue(QUEUE_A,true,false,false, args);
    }

    @Bean
    public Queue dlqQueue(){
        return new Queue(DLQ_QUEUE_B,true);
    }

    @Bean
    public Binding bindingDLQueue(){
        return BindingBuilder.bind(dlqQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY_A);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueA()).to(directExchange()).with(RabbitConf.DLX_ROUTING_KEY_A);
    }

    //消费者开启多线程
    @Bean(name = "customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(30);
        //这里还可以添加一个ack处理，默认的是auto，自动处理消息确认；
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        configurer.configure(factory,connectionFactory);
        return factory;
    }
}
