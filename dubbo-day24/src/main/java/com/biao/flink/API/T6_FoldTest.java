package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootApplication
public class T6_FoldTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Set<Vehicle> vehicleHashSet = new HashSet<>(5);
        // java Stream
        Stream<Vehicle> stream = new Producer().getVehicles().limit(5L);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);
        DataStreamSource<Vehicle> dataStream = environment.fromCollection(vehicleHashSet);
        // KeyedStream即DataStream上的操作状态按key分区了,即对数据流做分类，注意这是逻辑分区
        KeyedStream<Vehicle, Tuple> keyedStream = dataStream.keyBy("type");
        //fold is @Deprecated
        /*DataStream<Vehicle> dataStream1 = keyedStream
                .fold();
        dataStream1.print();
        dataStream1.writeAsText("D:/API/Reduce", FileSystem.WriteMode.OVERWRITE);*/
        environment.execute();
    }
}
