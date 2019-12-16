package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Classname T9_WindowTest2
 * @Description  使用数量模式窗口进行归约
 * @Author KOOL
 * @Date  2019/12/14 23:25
 * @Version 1.0
 **/
@SpringBootApplication
public class T9_WindowTest2 {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(4);
        Set<Vehicle> vehicleHashSet = new HashSet<>(7);
        Stream<Vehicle> stream = new Producer().getVehicles().limit(7);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);
        // flink stream， 这里使用chain语法，非常简洁,
        // 按type逻辑分区，并统计weight值，
        environment.fromCollection(vehicleHashSet)
                // 这里使用count特征窗口，未使用时间特征，可不加时间戳
                // .assignTimestampsAndWatermarks(new MyTimeAssigner())
                .keyBy("type")
                // 建立按记录数特征的WindowedStream,以下为窗口内元素数量为3，窗口每次滑过4个元素，
                .countWindow(2,2)
                .apply(new WindowFunction<Vehicle, Object, Tuple, GlobalWindow>() {
                    @Override
                    public void apply(Tuple tuple, GlobalWindow window, Iterable<Vehicle> input, Collector<Object> out) {
                        Tuple2<String,Float> total = new Tuple2<>();
                        total.f1 = 0.0f;
                        for (Vehicle v : input
                             ) {
                            total.f0 = v.getType();
                            total.f1 += v.getWeight();
                        }
                        out.collect(total);
                    }
                })
                .writeAsText("D:/API/window02", FileSystem.WriteMode.OVERWRITE);
        environment.execute();
    }
}
