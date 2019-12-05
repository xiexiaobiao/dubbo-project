package com.biao.wordcount;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname PipeApplication
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/3 10:50
 * @Version 1.0
 **/
public class WordCountApplication {
    public static void main(String[] args) {
        System.out.println("WordCountApplication starting .........");
        Properties props = new Properties();
        // StreamsConfig已经预定义了很多参数名称，运行时console会输出所有StreamsConfig values
        // 这里没有使用springboot的application.properties来配置
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"streams-word-count");
        // kafka虚拟机linux地址
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.221:9092");
        // kafka流都是byte[],必须有序列化，不同的对象使用不同的序列化器
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());

        // kafka流计算是一个各broker连接的拓扑结构，以下使用builder来构造拓扑
        final StreamsBuilder builder = new StreamsBuilder();
        // 构建一个KStream流对象，元素是<String, String>类型的key-value对值，topic:streams-plaintext-input
        KStream<String, String> source = builder.stream("streams-plaintext-input");

        // 以下使用stream链式语法+lambda，具体分开的过程语句我就不写了
        // flatMapValues将text line使用空格分隔成words
        source.flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
                .groupBy(((key, value) -> value))
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
                .toStream()
                .to("streams-wordcount-output", Produced.with(Serdes.String(),Serdes.Long()));

        // 查看具体构建的拓扑结构
        final Topology topology = builder.build();
        System.out.println(topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology,props);
        // 控制运行次数，一次后就结束
        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook"){
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try{
            streams.start();
            latch.await();
        }catch (Throwable e){
            System.exit(1);
        }
        System.exit(0);
    }
}
