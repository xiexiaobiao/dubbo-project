package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@SpringBootApplication
public class T11_WindowJoin {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(2);

        System.out.println("vehicle stream 1 >>>");
        Set<Vehicle> vehicleHashSet = new HashSet<>(8);
        Stream<Vehicle> stream = new Producer().getVehicles().limit(8L);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);

        System.out.println("vehicle stream 2 >>>");
        List<Vehicle> VehicleList = new ArrayList<>(8);
        Stream<Vehicle> stream2 = new Producer().getVehicles().limit(8L);
        stream2.forEach(VehicleList::add);
        VehicleList.stream().forEach(System.out::println);
        // flink stream
        DataStream<Vehicle> dataStream1 =  environment.fromCollection(vehicleHashSet)
                .assignTimestampsAndWatermarks(new MyTimeAssigner());
        DataStream<Vehicle> dataStream2 =  environment.fromCollection(VehicleList)
                .assignTimestampsAndWatermarks(new MyTimeAssigner());
        // JoinedStreams 可按照SQL的 inner join理解, DB是从两个table范围内取，
        // 这里是在一个共用的window中，将两个流按笛卡尔积取元素对(想象两个足球队入场握手)
        dataStream1.join(dataStream2)
                // KeySelector指定为按type+color属性比较来自两个流的元素对
                .where((KeySelector<Vehicle, Object>) value -> value.getType() + value.getColor())
                .equalTo((KeySelector<Vehicle, Object>) value -> value.getType() + value.getColor())
                .window(TumblingEventTimeWindows.of(Time.of(2, TimeUnit.SECONDS)))
                // JoinFunction is called with each pair of joining elements.
                // 对符合条件的元素对的操作，我这里将他们放入一个tuple
                .apply(new JoinFunction<Vehicle, Vehicle, Object>() {
                    Tuple2<String,String> tuple2 = new Tuple2<>();
                    @Override
                    public Object join(Vehicle first, Vehicle second) {
                        tuple2.f0 = "e1: "+ first;
                        tuple2.f1 = "e2: "+ second;
                        return tuple2;
                    }
                })
                .writeAsText("D:/API/WindowJoin", FileSystem.WriteMode.OVERWRITE);
        environment.execute();
    }

    // 对元素添加 event time 时间戳
    static class MyTimeAssigner implements AssignerWithPunctuatedWatermarks<Vehicle> {
        // emit a watermark. called right after extractTimestamp(Object, long)} method.
        @Nullable
        @Override
        public Watermark checkAndGetNextWatermark(Vehicle lastElement, long extractedTimestamp) {
            return null;
        }

        // Assigns a timestamp to an element
        @Override
        public long extractTimestamp(Vehicle element, long previousElementTimestamp) {
            return element.getSequenceTimestamp();
        }
    }
}
