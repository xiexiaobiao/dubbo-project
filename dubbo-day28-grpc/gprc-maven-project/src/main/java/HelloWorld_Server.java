
import com.biao.grpc.helloworld.GreeterGrpc;
import com.biao.grpc.helloworld.HelloWorldProto;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;


import java.io.IOException;
import java.util.logging.Logger;


/**
 * @ClassName HelloWorld_Server
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/26
 * @Version V1.0
 **/
public class HelloWorld_Server {
    private static final Logger logger = Logger.getLogger(HelloWorld_Server.class.getName());


    private int port = 50051;
    private Server server;

    private void start() throws IOException{
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on "+ port);

        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run(){

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorld_Server.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop(){
        if (server != null){
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }


    public  static  void main(String[] args) throws IOException, InterruptedException {

        final HelloWorld_Server server = new HelloWorld_Server();
        server.start();
        server.blockUntilShutdown();
    }


    // 实现 定义一个实现服务接口的类
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloWorldProto.HelloRequest req, StreamObserver<HelloWorldProto.HelloReply> responseObserver){
            HelloWorldProto.HelloReply reply = HelloWorldProto.HelloReply.newBuilder().setMessage(("Hello "+req.getName())).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            System.out.println("Message from gRPC-Client:" + req.getName());
        }
    }
}
