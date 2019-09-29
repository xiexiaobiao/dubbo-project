package com.biao.mall.business.service;

import com.biao.mall.common.util.MQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Classname SimpleMq
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-07 21:29
 * @Version 1.0
 **/
@Service
public class SimpleMqService {
    private final static String QUEUE_NAME = "dlx.queue";

    public void setSimpleModelMsg(String msg){
        try(Connection connection = MQConnectionUtil.getConnection()){
            try(Channel channel = connection.createChannel();){

                /**
                 * 队列名
                 * 是否持久化
                 *  是否排外  即只允许该channel访问该队列   一般等于true的话用于一个队列只能有一个消费者来消费的场景
                 *  是否自动删除  消费完删除
                 *  其他属性
                 */
                channel.queueDeclare(QUEUE_NAME,false,false,false,null);
                /**
                 * 交换机
                 * 队列名
                 * 其他属性  路由
                 * 消息body
                 */
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                System.out.println("[x]Sent '"+msg + "'");
            }catch (IOException | TimeoutException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //关闭通道，,channel/connection都实现了 AutoCloseable 可以使用try-with-resource简化
//        channel.close();
//        connection.close();
    }


}
