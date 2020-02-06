package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
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
public class T13_CoGroup {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置timestamp按照IngestionTime来处理
        environment.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
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
        // coGroup 与 join的区别：其他一样，只是apply参数不同，join是两个泛型，coGroup是两个迭代集，
        // join比较的是笛卡尔积元素对，coGroup直接操作两个迭代集，更为自由，比如可做排序，而join不可
        // join是coGroup的特例，一般建议使用coGroup
        dataStream1.coGroup(dataStream2)
                // KeySelector指定为按type+color属性比较来自两个流的元素对
                .where((KeySelector<Vehicle, Object>) value -> value.getType())
                .equalTo((KeySelector<Vehicle, Object>) value -> value.getType())
                .window(TumblingEventTimeWindows.of(Time.of(2, TimeUnit.SECONDS)))
                // 我这里直接将两个集做m*n输出
                .apply(new CoGroupFunction<Vehicle, Vehicle, Object>() {
                    @Override
                    public void coGroup(Iterable<Vehicle> first, Iterable<Vehicle> second, Collector<Object> out) {
                        Tuple1<String> tuple1 = new Tuple1<>();
                        first.forEach( x ->
                            second.forEach( y ->
                                            {tuple1.f0 = x.toString() +" / "+ y.toString();
                                            out.collect(tuple1);
                                            }
                                    )
                        );
                    }
                })
                .writeAsText("D:/API/CoGroup", FileSystem.WriteMode.OVERWRITE);
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
