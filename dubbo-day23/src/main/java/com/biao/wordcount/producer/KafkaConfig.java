package com.biao.wordcount.producer;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname KafkaConfig
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/1 18:46
 * @Version 1.0
 **/
@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaTemplate<Integer,String > kafkaTemplate(){
        return new KafkaTemplate<>(this.producerFactory());
    }

    // topic
    @Bean
    public KafkaAdmin admin(){
        Map<String,Object> configs = new HashMap<>(16);
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.221:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    // NewTopic(String name, int numPartitions, short replicationFactor)
    // kafka中每个topic只需创建一次，
    public NewTopic topic(){
        return new NewTopic("streams-plaintext-input",1, (short) 1);
    }

    @Bean
    // NewTopic(String name, int numPartitions, short replicationFactor)
    // kafka中每个topic只需创建一次，
    public NewTopic topic2(){
        return new NewTopic("streams-wordcount-output",1, (short) 1);
    }

    // producer
    @Bean
    public Map<String,Object> producerConfigs(){
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.221:9092");
        props.put("acks","all");
        props.put("retries",2);
        props.put("batch.size",16384);
        props.put("linger.ms",1);
        props.put("buffer.memory",33554432);
        props.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("key.converter","org.apache.kafka.connect.storage.IntegerConverter");
//        props.put("value.converter","org.apache.kafka.connect.storage.StringConverter");
        return props;
    }

    @Bean
    public ProducerFactory<Integer,String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(this.producerConfigs());
    }

    // consumer
    @Bean
    public Map<String,Object> consumerConfigs(){
        HashMap<String,Object> props =  new HashMap<>(16);
        props.put("bootstrap.servers","192.168.1.221:9092");
        props.put("group.id","foo");
        props.put("enable.auto.commit","true");
        // WordCountApplication 的consumer消费对象是统计的结果 key-value
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.LongDeserializer");
        props.put("formatter","kafka.tools.DefaultMessageFormatter");
        props.put("print.key","true");
        props.put("value.key","true");
//        props.put("key.converter","org.apache.kafka.connect.storage.IntegerConverter");
//        props.put("value.converter","org.apache.kafka.connect.storage.StringConverter");
        return props;
    }

    @Bean
    public ConsumerFactory<Integer,String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(this.consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer,String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Integer,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory());
        return factory;
    }

    @Bean
    public SimpleConsumer simpleConsumerLister(){
        return new SimpleConsumer();
    }
}
