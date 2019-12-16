package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
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
public class T4_KeyByTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Set<Vehicle> vehicleHashSet = new HashSet<>(5);
        // java Stream
        Stream<Vehicle> stream = new Producer().getVehicles().limit(5L);
        stream.forEach(vehicleHashSet::add);
        /**
         * 关系比较：
         * DataStreamSource<T> extends SingleOutputStreamOperator<T>
         * SingleOutputStreamOperator<T> extends DataStream<T>
         *
         * The SingleOutputStreamOperator represents a user defined transformation
         * applied on a DataStream with one predefined output type.
         *
         * The DataStreamSource represents the starting point of a DataStream.
         */
        DataStreamSource<Vehicle> dataStream = environment.fromCollection(vehicleHashSet);
        // KeyedStream即DataStream上的操作状态按key分区了,即对数据流做分类，注意这是逻辑分区
        KeyedStream<Vehicle, Tuple> keyedStream = dataStream.keyBy("color");
        keyedStream.print();
        environment.execute();
    }
}
