package com.biao.mall.storage.controller;

import com.biao.mall.storage.service.RedisClusterJedis;
import com.biao.mall.storage.service.RedisOneLettuce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @Classname RedisClusterController
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-18 21:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("/redis")
public class RedisClusterController {

    @Autowired
    private RedisClusterJedis jedisCluster;

    @Autowired
    RedisOneLettuce redisOneLettuce;

    @GetMapping("/cluster")
    public String jedisTest(){
        jedisCluster.testKey();
        return "success";
    }

    @GetMapping("/async")
    public String lettuceAsynTest() throws ExecutionException, InterruptedException {
        redisOneLettuce.asyncTest();
        return "success";
    }

    @GetMapping("/sync")
    public String lettuceTest() {
        redisOneLettuce.syncTest();
        return "success";
    }

    @GetMapping("/cluster2")
    public String lettuceClusterTest() {
        redisOneLettuce.redisCluster();
        return "success";
    }
}
