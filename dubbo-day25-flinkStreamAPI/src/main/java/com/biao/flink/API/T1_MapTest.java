package com.biao.flink.API;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Classname T1_MapTest
 * @Description  todo
 * @Author KOOL
 * @Date  2019/12/20 23:08
 * @Version 1.0
 **/
public class T1_MapTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // list1将不可更新操作
        // List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Integer> list = new ArrayList<>(Collections.emptyList());
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        System.out.println("environment.getParallelism >>>> " + environment.getParallelism());
        DataStream<Integer> dataStream = environment
                .setParallelism(6) // 设置执行环境并行度
                .fromCollection(list);
        System.out.println("dataStream Parallelism is >>>> " + dataStream.getParallelism());
        // lambda表达式实现参数 MapFunction<T, R>
        dataStream.map(t -> t + 100)
                .setParallelism(6)
                .writeAsText("D:/API/MapTest", FileSystem.WriteMode.OVERWRITE);
         //     .print();  //打印到控制台
        environment.execute();
    }
}
