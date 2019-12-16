package com.biao.flink;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {

    // 通过bean注入可以直接创建一个topic并指定topic的属性,也可使用AdminClient来创建
    @Bean
    NewTopic topic(){
        // NewTopic(String name, int numPartitions, short replicationFactor)
        return new NewTopic("vehicle",3,(short) 3);
    }

    // 使用AdminClient的静态create方法创建一个管理端，用于管理topic
    @Bean(name = "adminClient")
    AdminClient adminClient(){
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.221:9092,192.168.1.222:9092,192.168.1.223:9092");
        return AdminClient.create(properties);
    }


}
