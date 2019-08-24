package com.biao.mall.common.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Classname ConnectionUtil
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-08-07 21:06
 * @Version 1.0
 **/
public class ConnectionUtil {

    //todo 20190808
    /*频繁创建MQ连接和断开操作，可以使用线程池来优化，*/
    public static Connection getConnection(){
        //连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");
        //连接5672端口  注意15672为工具界面端口  25672为集群端口
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/mall");
        connectionFactory.setUsername("mall");
        connectionFactory.setPassword("mall");

        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
