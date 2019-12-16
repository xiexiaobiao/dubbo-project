package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@SpringBootApplication
public class T12_IntervalJoin {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置timestamp按照EventTime来处理
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(3);

        System.out.println("vehicle stream 1 >>>");
        Set<Vehicle> vehicleHashSet = new HashSet<>(5);
        Stream<Vehicle> stream = new Producer().getVehicles().limit(5L);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);

        System.out.println("vehicle stream 2 >>>");
        List<Vehicle> VehicleList = new ArrayList<>(5);
        Stream<Vehicle> stream2 = new Producer().getVehicles().limit(5L);
        stream2.forEach(VehicleList::add);
        VehicleList.stream().forEach(System.out::println);
        // flink stream
        KeyedStream<Vehicle,String> KeyedDataStream1 =  environment.fromCollection(vehicleHashSet)
                .assignTimestampsAndWatermarks(new MyTimeAssigner())
                .keyBy(Vehicle::getColor);
        KeyedStream<Vehicle,String> KeyedDataStream2 =  environment.fromCollection(VehicleList)
                .assignTimestampsAndWatermarks(new MyTimeAssigner())
                .keyBy(Vehicle::getColor);
        // intervalJoin：对于来自于两个流的元素e1和e2，使其满足
        // e1.timestamp + lowerBound <= e2.timestamp <= e1.timestamp + upperBound
        // Time-bounded stream joins are only supported in event time
        KeyedDataStream1.intervalJoin(KeyedDataStream2)
                .between(Time.milliseconds(-2), Time.milliseconds(2)) // lower and upper bound
                .upperBoundExclusive()
                .lowerBoundExclusive() // optional
                .process(new ProcessJoinFunction<Vehicle, Vehicle, Object>() {
                    Tuple2<String,String> tuple2 = new Tuple2<>();
                    @Override
                    public void processElement(Vehicle left, Vehicle right, Context ctx, Collector<Object> out) {
                            tuple2.f0 = "e1->"+left.toString() + left.getSequenceTimestamp();
                            tuple2.f1 = "e2->"+right.toString() + right.getSequenceTimestamp();
                            out.collect(tuple2);
                    }
                })
                .writeAsText("D:/API/IntervalJoin", FileSystem.WriteMode.OVERWRITE);
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
