package com.biao.mall.stock.service;

import com.biao.mall.common.util.MQConnectionUtil;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @Classname MqHandlerService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-08 19:11
 * @Version 1.0
 **/
@Service
public class MqHandlerService {
    private final static String QUEUE_NAME = "simple_queue";

    public void MqSimpleHandler(){
        try(Connection connection = MQConnectionUtil.getConnection()){
            try(Channel channel = connection.createChannel()){
                //注意这里的channel参数
                channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
