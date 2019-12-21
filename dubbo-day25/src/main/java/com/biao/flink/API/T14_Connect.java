package com.biao.flink.API;

import com.biao.flink.Producer;
import com.biao.flink.Vehicle;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
public class T14_Connect {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置timestamp按照IngestionTime来处理
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置环境全局并行数，将作用于所有operator
        environment.setParallelism(2);

        System.out.println("vehicle stream 1 >>>");
        List<Integer> list1 = Arrays.asList(1,2,3,4,5);
        DataStream<Integer> dataStream1 = environment.fromCollection(list1);
        list1.stream().forEach(System.out::println);

        System.out.println("vehicle stream 2 >>>");
        List<Vehicle> VehicleList = new ArrayList<>(5);
        Stream<Vehicle> stream2 = new Producer().getVehicles().limit(5L);
        stream2.forEach(VehicleList::add);
        VehicleList.stream().forEach(System.out::println);
        // flink stream
        DataStream<Vehicle> dataStream2 =  environment.fromCollection(VehicleList);

        /** connect比 coGroup/join 更为自由，没有key做预匹配，
          可以外部定义共同的key，再分别与两个流的元素单独匹配，即可等效于coGroup/join
          分别对来自两个流的元素做操作，并保留流的元素类型 */
         /**流connect算子使用场景例：流A中有一个规则(rule)集，这个规则集会随着流B中的元素变化。流A中的一个规则集先存储为一个状态并
         等待流B中新元素的到来，接收后，就将之前保存的规则集应用到新元素上来产生一个结果，或(同时)注册一个新的计时器在将来触发新行为。*/
        dataStream1.connect(dataStream2)
                /**ConnectedStreams也可以进行key逻辑分区，并且特别注意两个KeySelector<Integer, *>的第二参数要type一样，否则
                 * 报错：Key types if input KeyedStreams don't match*/
                .keyBy((KeySelector<Integer, Object>) String::valueOf, (KeySelector<Vehicle, Object>) Vehicle::getType)
                .process(new CoProcessFunction<Integer, Vehicle, Object>() {
                    @Override
                    public void processElement1(Integer value, Context ctx, Collector<Object> out) {
                        // set timers, When reacting to the firing of set timers the function can emit yet more elements.
                        // Setting timers is only supported on a keyed streams.
                        ctx.timerService().registerProcessingTimeTimer(11245L);
                        ctx.timerService().registerEventTimeTimer(145142L);
                        if (value % 2 == 0){
                            // 这里的timestamp都是null，请看官君思考下，参考T13_CoGroup
                            out.collect("e1: " + value + " timestamp: "+ ctx.timestamp());
                        }
                    }
                    @Override
                    public void processElement2(Vehicle value, Context ctx, Collector<Object> out) {
                        // query the time (both event and processing)
                        Long timestamp1 = ctx.timerService().currentProcessingTime();
                        Long timestamp2 = ctx.timestamp();
                        if ( Objects.equals("car",value.getType())){
                            out.collect("e2: "+ value+ " ProcessingTime: "+ timestamp1 + " timestamp: "+ timestamp2);
                        }
                    }
                })
                .writeAsText("D:/API/Connect1", FileSystem.WriteMode.OVERWRITE);

        // 以下代码展示process的另一函数参数:KeyedCoProcessFunction，区别CoProcessFunction就是有无需要先keyby
        dataStream1.connect(dataStream2)
                /**ConnectedStreams也可以进行key逻辑分区，并且特别注意两个KeySelector<Integer, *>的第二参数要type一样，
                 * 我这里都转为String*/
                .keyBy((KeySelector<Integer, String>) String::valueOf, (KeySelector<Vehicle, String>) Vehicle::getType)
                // KeyedCoProcessFunction processes elements of two keyed streams and produces a single output one
                .process(new KeyedCoProcessFunction<Object, Integer, Vehicle, Object>() {
                    @Override
                    public void processElement1(Integer value, Context ctx, Collector<Object> out) {
                    }
                    @Override
                    public void processElement2(Vehicle value, Context ctx, Collector<Object> out) {
                    }
                })
                .writeAsText("D:/API/Connect2", FileSystem.WriteMode.OVERWRITE);

        /** CoMapFunction 在两个connected streams上实现了同一个map()转换
         * 因使用同一个函数实例，故这两个流转换能共享状态*/
        dataStream1.connect(dataStream2)
                .map(new CoMapFunction<Integer, Vehicle, Object>() {
                    // 注意这里不能有返回null值，否则NPE
                    @Override
                    public Object map1(Integer value) {
                        return value * 2;
                    }
                    // 注意这里不能有返回null值，否则NPE
                    @Override
                    public Object map2(Vehicle value) {
                        if (Objects.equals("car",value.getType())){
                            return "car -->  suv ：" + value;
                        }
                        return value;
                    }
                })
                .writeAsText("D:/API/Connect3", FileSystem.WriteMode.OVERWRITE);

        // CoFlatMapFunction 和 CoFlatMapFunction类似，类比于FlatMap和Map，map函数返回Object
        // flatMap是void类型，返回值都在Collector中,可容纳多个值，所以能进行flat扁平化操作
        dataStream1.connect(dataStream2)
                .flatMap(new CoFlatMapFunction<Integer, Vehicle, Object>() {
                    @Override
                    public void flatMap1(Integer value, Collector<Object> out) {
                        out.collect(value * 2);
                    }
                    @Override
                    public void flatMap2(Vehicle value, Collector<Object> out) {
                        for (String str : value.toString().split(",")) {
                            out.collect(str);
                        }
                    }
                })
                .writeAsText("D:/API/Connect4", FileSystem.WriteMode.OVERWRITE);;

        environment.execute();
    }
}
