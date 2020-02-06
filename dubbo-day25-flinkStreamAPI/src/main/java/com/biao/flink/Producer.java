package com.biao.flink;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.admin.AdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
//@Slf4j
public class Producer {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private Logger log = LoggerFactory.getLogger(Producer.class);
    private String time = LocalDateTime.now().toString();

    public void send() {
        log.info("send elements to kafka started at :{}",time);
        Random random = new Random();
        // kafkaTemplate.sendDefault() 为异步方法，返回 ListenerFuture<T>，
        // 如果运行到此前没有显示创建topic：vehicle，send方法会缺省创建复制因子为1的topic
        this.getVehicles()
                .forEach(item -> kafkaTemplate.send("vehicle",String.valueOf(random.nextInt(10)), JSON.toJSONString(item)));
    }

    // 使用随机方法产生车辆流
    public Stream<Vehicle> getVehicles(){
        List<Vehicle> vehicleList = new ArrayList<>(75);
        Random random = new Random();
        List<String> colors = Arrays.asList("red","yellow","black","white");
        List<String> types = Arrays.asList("car","truck","suv","pickup","other");
        List<Float> weights = Arrays.asList(1.0f,1.5f,2.0f,2.5f);
        // 使用Random生成IntStream流
        IntStream intStream1 = random.ints(25,100000,999999);
        intStream1.limit(25)
                .forEach(num -> {vehicleList.add(new Vehicle(types.get(random.nextInt(5)),num,
                        colors.get(random.nextInt(4)),weights.get(random.nextInt(4))));});
        // 使用 IntStream静态方法生成流
        IntStream intStream2 = IntStream.rangeClosed(100000,999999);
        intStream2.limit(25)
                .forEach(num -> {vehicleList.add(new Vehicle(types.get(random.nextInt(5)),num,
                        colors.get(random.nextInt(4)),weights.get(random.nextInt(4))));});
        // 使用Stream静态迭代器方法生成流
        Stream<Integer> intStream3 = Stream.iterate(100000, n->n+3);
        intStream3.limit(25)
                .forEach( t -> {vehicleList.add(new Vehicle(types.get(random.nextInt(5)),t,
                        colors.get(random.nextInt(4)),weights.get(random.nextInt(4))));});

        // 用于输出测试
        // vehicleList.stream().forEach(System.out::println);
        return vehicleList.stream();
    }
}
