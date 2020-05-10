package com.biao.study;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedLock
 * @Description: TODO
 * @Author Biao
 * @Date 2020/5/9
 * @Version V1.0
 **/
/** compile group: 'org.redisson', name: 'redisson', version: '3.12.5' */
public class RedLockTest {
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        // 集群模式
        config.useClusterServers()
                .addNodeAddress("127.0.0.1:6379")
                .addNodeAddress("127.0.0.1:6380")
                .addNodeAddress("127.0.0.1:6381");
        RedissonClient redisson = Redisson.create(config);
        // RLock：Redis based implementation of {java.util.concurrent.locks.Lock}
        RLock lock1 = redisson.getLock("lock1");
        RLock lock2 = redisson.getLock("lock2");
        RLock lock3 = redisson.getLock("lock3");
        // 严格来讲，此处多个RLock应该从独立的Redis实例上获取，再组合为RedLock
        // 将多个独立的RLock组合为一个RedLock
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        // lock实际上调用的是有默认值的tryLock，
        // 返回void
        redLock.lock();
        // 异步请求锁，返回RFuture
        redLock.lockAsync();
        // 返回Boolean
        redLock.tryLock(10L,5L,TimeUnit.SECONDS);
        try{
            // 业务逻辑处理
            System.out.println("RedLock acquire success.");
        }finally {
            redLock.unlock();
        }
    }
}
