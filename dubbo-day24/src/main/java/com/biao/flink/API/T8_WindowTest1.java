package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.commons.compress.utils.Lists;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootApplication
public class T8_WindowTest1 {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置运行环境的时间特征，可以三选一，缺省为ProcessingTime:
        // environment.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        // environment.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // cause all operators (such as map,batchReduce) to run with x parallel instances
        environment.setParallelism(2);
        Set<Vehicle> vehicleHashSet = new HashSet<>(7);
        // java Stream
        Stream<Vehicle> stream = new Producer().getVehicles().limit(7L);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);
        // flink stream
        SingleOutputStreamOperator<Vehicle> dataStream = environment.fromCollection(vehicleHashSet)
                // 这里特别注意：对记录加eventTime时间戳，因为这里是从collection自建的流，没自带时间戳，如果从kafka取，一般会自带
                // 否则报错：java.lang.RuntimeException: Record has Long.MIN_VALUE timestamp (= no timestamp marker)
                .assignTimestampsAndWatermarks(new MyTimeAssigner());

        // Sets the parallelism for this operator.但此实例流无法使用并行，请看官君思考下why
        // dataStream.setParallelism(3);

        // KeyedStream即DataStream上的操作状态按key分区了(即对数据流做分类)，注意这是逻辑分区
        // 先按type分区流的所有记录
        KeyedStream<Vehicle, Tuple> keyedStream = dataStream.keyBy("type");
        // 建立按记录数的WindowedStream,以下为窗口内元素数量为5，窗口每次滑过10个元素，
        /*  windowStream = keyedStream.countWindow(5,10); */
        // 建立按时间特征划分的WindowedStream，
        WindowedStream<Vehicle, Tuple, TimeWindow> windowStream = keyedStream
                //  以下建立 翻转模式窗口，窗口时间跨度1秒
                // .window(TumblingEventTimeWindows.of(Time.seconds(1));
                // 以下建立 滑动模式窗口，窗口时长500，每次滑动时长1000
                .window(SlidingEventTimeWindows.of(Time.milliseconds(500),Time.milliseconds(1000)));
        // 可以在window内reduce/fold/aggregations，这里是将一个window内的全部元素放入到一个list中
        SingleOutputStreamOperator<Object> dataStream1 = windowStream
                .apply(new WindowFunction<Vehicle, Object, Tuple, TimeWindow>() {
                    @Override
                    public void apply(Tuple tuple, TimeWindow window, Iterable<Vehicle> input, Collector<Object> out) throws Exception {
                        List<Vehicle> vehicles = new ArrayList<>(8);
                        for (Vehicle v : input
                             ) {
                            vehicles.add(v);
                        }
                        out.collect(vehicles);
                    }
                });
        dataStream1.print();
        dataStream1.writeAsText("D:/API/window", FileSystem.WriteMode.OVERWRITE);
        environment.execute();
    }

    // 自定义时间戳Timestamp和时间watermark水印分配器：给元素分配event time timestamps，并
    // 生成标识流event time处理进度的低位水印(low watermarks)
    // 水印：是一个eventTime标识，watermarks之后只有eventTime大于此水印时间戳的元素产生
    // AssignerWithPeriodicWatermarks<T> generate watermarks in a periodical interval
    // AssignerWithPunctuatedWatermarks<T> emitted only if it is non-null and its timestamp
    //	 * is larger than that of the previously emitted watermark
    static class MyTimeAssigner implements AssignerWithPunctuatedWatermarks<Vehicle>{
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
