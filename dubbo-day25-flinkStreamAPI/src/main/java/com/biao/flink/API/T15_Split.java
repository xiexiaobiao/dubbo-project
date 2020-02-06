package com.biao.flink.API;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T15_Split {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(1);
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        DataStream<Integer> dataStream = environment.fromCollection(list);
        // SplitStream<Integer> 被 @Deprecated 标注
        SplitStream<Integer> splitStream = dataStream.split(new OutputSelector<Integer>() {
            @Override
            public Iterable<String> select(Integer value) {
                List<String> list1 = new ArrayList<>(16);
                if (value % 2 == 0){
                    list1.add("even");
                }else {
                    list1.add("odd");
                }
                return list1;
            }
        });
        // 原流虽然被分割，但本身打印出来还是原流
        splitStream.writeAsText("D:/API/Split", FileSystem.WriteMode.OVERWRITE);

        DataStream<Integer> even = splitStream.select("even");
        even.writeAsText("D:/API/Split/even", FileSystem.WriteMode.OVERWRITE);

        DataStream<Integer> odd = splitStream.select("odd");
        odd.writeAsText("D:/API/Split/odd", FileSystem.WriteMode.OVERWRITE);

        DataStream<Integer> all = splitStream.select("even","odd");
        all.writeAsText("D:/API/Split/all", FileSystem.WriteMode.OVERWRITE);

        environment.execute();
    }
}
