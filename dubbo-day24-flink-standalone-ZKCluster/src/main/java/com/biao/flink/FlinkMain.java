package com.biao.flink;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class FlinkMain {
    public static void main(String[] args) throws Exception {
        // 流式执行上下文，还有非流式的ExecutionEnvironment，缺省为Local模式，
        // 远程的使用RemoteStreamEnvironment可在远程Flink集群上运行jar
        // RemoteStreamEnvironment remoteStreamEnv = new RemoteStreamEnvironment(String host, int port, String... jarFiles);
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        // StreamsConfig已经预定义了很多参数名称，运行时console会输出所有StreamsConfig values
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"Flink Application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.221:9092,192.168.1.222:9092,192.168.1.223:9092");
        // kafka流都是byte[],必须有序列化，不同的对象使用不同的序列化器
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());
        // 构造一个KafkaConsumer，使用kafka topic做源头
        // FlinkKafkaConsumer(topic,DeserializationSchema,properties),其中的DeserializationSchema可以自定义反序列化模式，但
        // 强烈建议使用通用序列化，自定义序列化迁移和维护困难
        FlinkKafkaConsumer<String> flinkKafkaConsumer = new FlinkKafkaConsumer<>("vehicle",new SimpleStringSchema(),props);
        // flink连接Kafka数据源，并生成流
        // 使用map计算将每个string元素转换为Vehicle对象，JSON -> Vehicle
        DataStream<Vehicle> stream = environment.addSource(flinkKafkaConsumer)
                .map(str -> JSONObject.parseObject(str,Vehicle.class))
                .setParallelism(2);
        // 保存到文件测试，写入mysql前可以用来测试数据,本地windows运行和上传到linux上运行，注意文件路径写法不用
        // stream.writeAsText("D:/flinkOutput01", FileSystem.WriteMode.OVERWRITE);
        stream.writeAsText("/usr/flink1.9/fileOutPut01", FileSystem.WriteMode.OVERWRITE);
        // 保存到mysql测试
        stream.addSink(new Sink2Mysql());
        /**计数式滑动窗口测试：countWindowAll(windowSize,slideSize)，以下窗口大小10个记录，窗口一次滑动5个记录位置
         * ，特别注意 -> countWindowAll模式无法并行，因所有记录均需通过一个窗口*/
        SingleOutputStreamOperator<Vehicle> operator = stream
                .keyBy("type")
                .countWindowAll(5L,10L)
                .sum("weight");
        // operator.writeAsText("D:/flinkOutput02", FileSystem.WriteMode.OVERWRITE);
        operator.writeAsText("/usr/flink1.9/fileOutPut02", FileSystem.WriteMode.OVERWRITE);
        // Triggers the program execution.
        environment.execute();
    }
}
