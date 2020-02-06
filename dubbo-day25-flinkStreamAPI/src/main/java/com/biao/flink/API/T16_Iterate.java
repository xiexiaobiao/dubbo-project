package com.biao.flink.API;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class T16_Iterate {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(1);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        DataStream<Integer> dataStream = environment.fromCollection(list);
        // 迭代流的通常使用场景：对输出的结果流做流分离，对部分筛选出来的数据送入迭代流中反复迭代计算，直到满足条件；
        // 这里定义一个1-10的流，每个元素的迭代处理就是加上100，如果迭代后的值还是小于300，则放回流，直到满足大于300才输出；
        // The iterative data stream represents the start of an iteration in a DataStream
        IterativeStream<Integer> iterativeStream = dataStream.iterate(2000L);
        // 迭代算法定义
        DataStream<Integer> iterationBody = iterativeStream.map(value -> value + 100);
        iterationBody.writeAsText("D:/API/Iterate/iterationBody", FileSystem.WriteMode.OVERWRITE);
        DataStream<Integer> feedback = iterationBody.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer value) {
                return value  < 300;
                // 改为如下则无限迭代下去，但只迭代偶数元素
                //return value % 2 == 0;
            }
        });
        // 语法： closeWith(DataStream<T>) 满足feedback流过滤条件的放回
        iterativeStream.closeWith(feedback);
        iterativeStream.writeAsText("D:/API/Iterate/iterativeStream", FileSystem.WriteMode.OVERWRITE);

        DataStream<Integer> output = iterationBody.filter(value -> value >= 300);
        output.writeAsText("D:/API/Iterate/output", FileSystem.WriteMode.OVERWRITE);

        environment.execute();
    }
}
