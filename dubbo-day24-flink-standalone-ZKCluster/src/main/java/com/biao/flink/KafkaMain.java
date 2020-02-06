package com.biao.flink;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewPartitions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class KafkaMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Flink Application starting .........");
        ConfigurableApplicationContext context = SpringApplication.run(KafkaMain.class,args);

        // 使用kafka AdminClient示例
        AdminClient adminClient = (AdminClient) context.getBean("adminClient");
        // 批量创建kafka topic
        // adminClient.createTopics(...);
        // 打印输出所有的topic
        adminClient.listTopics().names().get().forEach(System.out::println);
        // 打印vehicle的topic信息
        System.out.println("info>>>" + adminClient.describeTopics(Collections.singletonList("vehicle")).values());
        // 删除vehicle2的topic，注意删除前要关闭所有consumer
        System.out.println("delete>>>" +adminClient.deleteTopics(Collections.singletonList("vehicle2")).values());
        // 修改partitions属性,仅对新Partitions起作用，原有Partitions状态不变，且replicationFactor不能修改
        // 以下例子设定原vehicle已有3个Partitions，replicationFactor为3，现增加到4个Partitions
        List<List<Integer>> lists = new ArrayList<>(Collections.emptyList());
        lists.add(Arrays.asList(0,1,2));
        NewPartitions newPartitions = NewPartitions.increaseTo(4,lists);
        Map<String, NewPartitions> map = new HashMap<>(Collections.emptyMap());
        map.put("vehicle",newPartitions);
        adminClient.createPartitions(map);

        // kafka生产者进行发送记录
        Producer producer = context.getBean(Producer.class);
        producer.send();
    }
}
