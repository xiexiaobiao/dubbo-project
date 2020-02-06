package com.biao.flink.API;

import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple4;

import java.util.Arrays;
import java.util.List;

public class T17_Project {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        environment.setParallelism(1);
        // 创建只有一个元素的Tuple流
        DataStream<Tuple4<String,Integer,String,Float>> fromStream = environment.fromElements(new Tuple4<>("car",123456,"yellow",1.5f));
        // 流投影，取原始流的元素的部分属性，只适用于Tuple类型的流，
        // 语法 project(int... fieldIndexes)
        DataStream<Tuple2<Float,String>> toStream = fromStream.project(3,0);
        toStream.writeAsText("D:/API/Project", FileSystem.WriteMode.OVERWRITE);
        environment.execute();
    }
}
