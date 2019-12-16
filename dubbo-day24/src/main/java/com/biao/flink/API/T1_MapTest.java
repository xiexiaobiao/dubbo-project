package com.biao.flink.API;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class T1_MapTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // list1将不可更新操作
        // List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Integer> list = new ArrayList<>(Collections.emptyList());
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        DataStream<Integer> dataStream = environment.fromCollection(list);
        System.out.println("Parallelism is >>>> " + dataStream.getParallelism());
        // lambda表达式实现参数 MapFunction<T, R>
        dataStream.map(t -> t*2).print();
        environment.execute();
    }
}
