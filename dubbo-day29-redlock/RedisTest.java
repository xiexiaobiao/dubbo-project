package com.biao.study;

import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName RedisTest
 * @Description: TODO
 * @Author Biao
 * @Date 2020/5/8
 * @Version V1.0
 **/
/** compile group: 'redis.clients', name: 'jedis', version: '3.3.0' */
public class RedisTest {
    public static void main(String[] args) {
        Jedis redis = new Jedis("127.0.0.1", 6379);
        //使用第1个库
        redis.select(1);
        redis.flushDB();
        redis.setbit("bit_key",1000L, "1");
        redis.getbit("bit_key",1000L);
        redis.bitcount("bit_key",1L,1500L);
        // 对"bit_key1","bit_key2"做AND位运算，并保存到"dest_key"中
        redis.bitop(BitOP.AND,"dest_key","bit_key1","bit_key2");
        // redis.bitpos("bit_key",false);
        redis.bitpos("bit_key",true);

        List<String> keyList = Arrays.asList("key01","key01");
        List<String> argsList = Arrays.asList("value01","value02");
        // eval(scripts,keyList,argsList)
        redis.eval("return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}", keyList, argsList );
        // eval(scripts,keyCount,...params)
        redis.eval( "return redis.call('set',KEYS[1],'bar')", 1, "foo");

        // 监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断
        redis.watch("k1","k2","k3");
        Transaction transaction = redis.multi();
        // 事务内多个命令
        transaction.setbit("bit_key",100L, Boolean.parseBoolean("1"));
        transaction.mset("k1","v1","k2","v2","k3","v3");
        List<Object> list = transaction.exec();
        // 放弃事务
        //String discard = transaction.discard();
        /**如果在执行 WATCH 命令之后， EXEC 命令或 DISCARD 命令先被执行了的话，那么就不需要再执行 UNWATCH 了
         * 因为 EXEC 命令会执行事务，因此 WATCH 命令的效果已经产生了；而 DISCARD 命令在取消事务的同时也会
         * 取消所有对 key 的监视，因此这两个命令执行之后，就没有必要执行 UNWATCH 了*/
        redis.unwatch();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            redis.set("key_"+i,"v_"+i);
            redis.get("key_"+i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("formal time used >>> " + (endTime - startTime) + " ms");
        //
        startTime = System.currentTimeMillis();
        Pipeline pipeline = redis.pipelined();
        for (int i = 0; i < 50000; i++) {
            // 返回Response<T>，但这里还未直接返回给client
            pipeline.set("key_2"+i,"v_2"+i);
            // 返回Response<T>，但这里还未直接返回给client
            pipeline.get("key_2" + i);
            /**这里使用打印输出会出错*/
            //Response<String> response = pipeline.get("key_2" + i);
            //System.out.println(response.get());
        }
        // Synchronize pipeline by reading all responses. This operation close the pipeline
        // 这里将同步pipeline所有返回结果，并放入List，但不返回
        pipeline.sync();
        // 如果需要返回结果集，可以使用以下方法，但官方建议应尽量避免使用，因为需要对pipeline所有返回结果做同步，很耗时，
        //List<Object> returnAll = pipeline.syncAndReturnAll();
        //System.out.println("completed commands >>> "+returnAll.size());
        //returnAll.forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("pipeline time used >>> " + (endTime - startTime) + " ms");
    }

}
