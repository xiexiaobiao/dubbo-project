package com.biao.mall.storage.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;

/**
 * @Classname RedisOneLettuce
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-19 09:14
 * @Version 1.0
 **/
@Service
public class RedisOneLettuce {

    public void syncTest(){
        RedisClient client = RedisClient.create("redis://test123@192.168.1.204:6379/0");
        StatefulRedisConnection<String, String> connection = client.connect();
        System.out.println("Connected to Redis");
        //配置为同步方式
        RedisStringCommands sync = connection.sync();
        String value = (String) sync.get("xiao");
        System.out.println(value);
        connection.close();
        client.shutdown();
    }

    public void asyncTest() throws ExecutionException, InterruptedException {
        RedisClient redisClient = RedisClient.create("redis://test123@192.168.1.204:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");
        //配置为异步方式
        RedisStringAsyncCommands<String, String> async = connection.async();
        RedisFuture<String> set = async.set("yao", "qin");
        RedisFuture<String> get = async.get("yao");

//        async.awaitAll(set, get) == true;

        System.out.println(set.get() == "OK");
        System.out.println(get.get());
        connection.close();
        redisClient.shutdown();
    }

    public void redisCluster(){
        // Syntax: redis://[password@]host[:port]
        RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:6379");

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to RedisCluster");

        connection.close();
        redisClient.shutdown();
    }
}
