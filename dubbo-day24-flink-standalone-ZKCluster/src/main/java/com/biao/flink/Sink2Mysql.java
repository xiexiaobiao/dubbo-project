package com.biao.flink;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;

// 也可使用顶级接口 SinkFunction，RichSinkFunction也实现了SinkFunction
public class Sink2Mysql extends RichSinkFunction<Vehicle> {
    private PreparedStatement preparedStatement;
    private DruidDataSource dataSource;
    private Connection connection;

    // Initialization method for the function.
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql://192.168.1.107:3306/data_center?characterEncoding=UTF-8&serverTimezone=UTC");
        connection = dataSource.getConnection();
        preparedStatement = connection.prepareStatement("insert into vehicle(type, plate, color, weight) values(?, ?, ?, ?);");

    }

    // Tear-down method for the user code. It is called after the last call to the main working methods
    @Override
    public void close() throws Exception {
        super.close();
        // 需try-catch，我直接简写了
        preparedStatement.close();
        connection.close();
        dataSource.close();
    }

    // Writes the given value to the sink. This function is called for every record.
    @Override
    public void invoke(Vehicle value, Context context) throws Exception {
        preparedStatement.setString(1,value.getType());
        preparedStatement.setInt(2,value.getPlate());
        preparedStatement.setString(3,value.getColor());
        preparedStatement.setFloat(4,value.getWeight());
        boolean result = preparedStatement.execute();
        System.out.println("DB saved record success >>>>> " );
        /*preparedStatement.addBatch();
        int[] updateNums = preparedStatement.executeBatch();
        System.out.println("DB saved items >>>>> " + updateNums.length);*/
    }
}
