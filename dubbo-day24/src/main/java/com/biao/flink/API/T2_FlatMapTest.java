package com.biao.flink.API;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class T2_FlatMapTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        List<String> list = Arrays.asList("this is FlatMapTest","by xiaobiao","what a nice day");
        DataStream<String> dataStream = environment.fromCollection(list);
        dataStream.flatMap(flatMapFunction).print();
        environment.execute();
    }

    private static FlatMapFunction<String, String> flatMapFunction = new FlatMapFunction<String, String>() {
        @Override
        public void flatMap(String value, Collector<String> out) {
            for(String word: value.split(" ")){
                out.collect(word);
            }
        }
    };

}
