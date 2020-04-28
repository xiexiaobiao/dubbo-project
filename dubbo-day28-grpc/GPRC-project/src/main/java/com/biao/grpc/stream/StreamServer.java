package com.biao.grpc.stream;

import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @ClassName StreamServer
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/28
 * @Version V1.0
 **/
public class StreamServer {

    private static int port = 8883;
    private static io.grpc.Server server;

    public void run() {
        ServiceImpl serviceImpl = new ServiceImpl();
        server = io.grpc.ServerBuilder.forPort(port).addService(serviceImpl).build();
        try {
            server.start();
            System.out.println("Server start success on port:" + port);
            server.awaitTermination();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 实现 定义一个实现服务接口的类
    private static class ServiceImpl extends StreamServiceGrpc.StreamServiceImplBase {

        @Override
        public void serverSideStreamFun(Stream.RequestData request, StreamObserver<Stream.ResponseData> responseObserver) {
            // TODO Auto-generated method stub
            System.out.println("请求参数：" + request.getText());
            for (int i = 0; i < 10; i++) {
                responseObserver.onNext(Stream.ResponseData.newBuilder()
                        .setText("你好" + i)
                        .build());
            }
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<Stream.RequestData> clientSideStreamFun(StreamObserver<Stream.ResponseData> responseObserver) {
            // TODO Auto-generated method stub
            return new StreamObserver<Stream.RequestData>() {
                private Stream.ResponseData.Builder builder = Stream.ResponseData.newBuilder();

                @Override
                public void onNext(Stream.RequestData value) {
                    // TODO Auto-generated method stub
                    System.out.println("请求参数：" + value.getText());

                }

                @Override
                public void onError(Throwable t) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onCompleted() {
                    // TODO Auto-generated method stub
                    builder.setText("数据接收完成");
                    responseObserver.onNext(builder.build());
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<Stream.RequestData> twoWayStreamFun(StreamObserver<Stream.ResponseData> responseObserver) {
            // TODO Auto-generated method stub
            return new StreamObserver<Stream.RequestData>() {

                @Override
                public void onNext(Stream.RequestData value) {
                    // TODO Auto-generated method stub
                    System.out.println("请求参数：" + value.getText());
                    responseObserver.onNext(Stream.ResponseData.newBuilder()
                            .setText(value.getText() + "，欢迎你的加入")
                            .build());
                }

                @Override
                public void onError(Throwable t) {
                    // TODO Auto-generated method stub
                    t.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    // TODO Auto-generated method stub
                    responseObserver.onCompleted();
                }
            };
        }
    }

    public static void main(String[] args) {
        StreamServer server = new StreamServer();
        server.run();
    }

}
