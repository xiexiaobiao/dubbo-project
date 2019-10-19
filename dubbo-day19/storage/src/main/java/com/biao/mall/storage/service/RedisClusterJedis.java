package com.biao.mall.storage.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname RedisCluster
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-18 21:05
 * @Version 1.0
 **/
@Service
public class RedisClusterJedis {

    public void testKey(){
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.1.204",6379));
        hostAndPorts.add(new HostAndPort("192.168.1.204",6380));
        hostAndPorts.add(new HostAndPort("192.168.1.204",6381));
//        hostAndPorts.add(new HostAndPort("192.168.1.204",6382));
//        hostAndPorts.add(new HostAndPort("192.168.1.204",6383));
//        hostAndPorts.add(new HostAndPort("192.168.1.204",6384));
        // 连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMinIdle(1);
        // 获取连接时的最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(2000);
        // 对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts,jedisPoolConfig);
        System.out.println("");
        System.out.println("判断\"user\"键是否存在："+jedisCluster.exists("user"));
        System.out.println("新增<'user','xiao'>的键值对："+jedisCluster.set("user", "xiao"));
        System.out.println("设置键user的过期时间为10s:"+jedisCluster.expire("user", 10));
        System.out.println("查看键user的剩余生存时间："+jedisCluster.ttl("user"));
        System.out.println("移除键user的生存时间："+jedisCluster.persist("user"));
        System.out.println("查看键user的剩余生存时间："+jedisCluster.ttl("user"));
        System.out.println("查看键user所存储的值的类型："+jedisCluster.type("user"));
    }
}
