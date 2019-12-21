package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple4;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Classname T18_Physicalpartitioning
 * @Description  Flink also gives low-level control (if desired) on the exact stream partitioning
 * after a transformation, via the following functions.
 * @Author KOOL
 * @Date  2019/12/20
 * @Version 1.0
 **/
public class T18_Physicalpartitioning {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        environment.setParallelism(1);
        // 创建只有一个元素的Tuple流
        DataStream<Tuple4<String,Integer,String,Float>> tupleStream = environment
                .fromElements(new Tuple4<>("car",123456,"yellow",1.5f));
        // 语法 partitionCustom(Partitioner<K> partitioner, String field)
        // Partitioner：根据总的分区数numPartitions计算一个key被分到的分区的下标
        DataStream<Tuple4<String,Integer,String,Float>> partitionStream1 = tupleStream
                // 这里使用取余算法，计算一个key的目标分区下标
                .partitionCustom((Partitioner<String>) (key, numPartitions) -> Integer.parseInt(key) % numPartitions,1);
        partitionStream1.writeAsText("D:/API/Physicalpartitioning", FileSystem.WriteMode.OVERWRITE);

        System.out.println("vehicle stream data >>>");
        Set<Vehicle> vehicleHashSet = new HashSet<>(10);
        Stream<Vehicle> stream = new Producer().getVehicles().limit(10);
        stream.forEach(vehicleHashSet::add);
        vehicleHashSet.stream().forEach(System.out::println);

        DataStream<Vehicle> vehicleDataStream = environment.fromCollection(vehicleHashSet);
        // 随机分区法，数据均匀地分配到各分区
        vehicleDataStream.shuffle();
        // 轮询算法分区
        vehicleDataStream.rebalance();

        /** 轮询分发，适用场景：一个管道扇出到多个下游算子，此分区法不会触发整体的rebalance(),
         只会对局部数据分区，算法和上下游算子并行度有关，*/
        // 比如上游并行度为2，下游为4，那么上游中一个就固定分发给下游中的两个，即1对2，
        // 如果上游并行度为6，下游为2，那么上游中三个就固定分发给下游中的1个，即3对1，
        // 如果非倍数关系，各对应数就会不同
        vehicleDataStream.rescale();
        // 广播到每个分区，即数据复制多份
        vehicleDataStream.broadcast();

        environment.execute();
    }
}
