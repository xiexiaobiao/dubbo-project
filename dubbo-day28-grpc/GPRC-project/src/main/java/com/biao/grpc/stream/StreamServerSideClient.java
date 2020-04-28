package com.biao.grpc.stream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

/**
 * @ClassName StreamClient
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/28
 * @Version V1.0
 **/
public class StreamServerSideClient {
    public static void main(String[] args) {
        Stream.RequestData requestData = Stream.RequestData.newBuilder().setText("hello world").build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8883).usePlaintext().build();
        StreamServiceGrpc.StreamServiceBlockingStub stub = StreamServiceGrpc.newBlockingStub(channel);

        // 一个请求对象，服务端可以传回多个结果对象 serverSideStreamFun
        Iterator<Stream.ResponseData> it = stub.serverSideStreamFun(requestData);
        long start = System.currentTimeMillis();
        while (it.hasNext()) {
            System.out.println(it.next().getText());
        }
        channel.shutdown();
        System.out.println(System.currentTimeMillis() - start + " MS");

    }

}
