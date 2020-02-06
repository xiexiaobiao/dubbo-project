package com.biao.flink.API;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Classname T3_FilterTest
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/20 23:08
 * @Version 1.0
 **/
@SpringBootApplication
public class T3_FilterTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // list将不可更新操作
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        DataStream<Integer> dataStream = environment.fromCollection(list);
        System.out.println("Parallelism is >>>> " + dataStream.getParallelism());
        // lambda表达式实现函数参数 FilterFunction<T>
        dataStream.filter(t -> t % 2 == 0)
                .writeAsText("D:/API/FlatMap", FileSystem.WriteMode.OVERWRITE);
                // print 和 writeAsText都是sink终端类算子，只能有一个，
                //.print();
        environment.execute();
    }
}
