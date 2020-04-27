package filetransfer;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: filetransfer.proto")
public final class FileTransferServiceGrpc {

  private FileTransferServiceGrpc() {}

  public static final String SERVICE_NAME = "filetransfer.FileTransferService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<filetransfer.Filetransfer.RequestData,
      filetransfer.Filetransfer.ResponseData> getServerSideStreamFunMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServerSideStreamFun",
      requestType = filetransfer.Filetransfer.RequestData.class,
      responseType = filetransfer.Filetransfer.ResponseData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<filetransfer.Filetransfer.RequestData,
      filetransfer.Filetransfer.ResponseData> getServerSideStreamFunMethod() {
    io.grpc.MethodDescriptor<filetransfer.Filetransfer.RequestData, filetransfer.Filetransfer.ResponseData> getServerSideStreamFunMethod;
    if ((getServerSideStreamFunMethod = FileTransferServiceGrpc.getServerSideStreamFunMethod) == null) {
      synchronized (FileTransferServiceGrpc.class) {
        if ((getServerSideStreamFunMethod = FileTransferServiceGrpc.getServerSideStreamFunMethod) == null) {
          FileTransferServiceGrpc.getServerSideStreamFunMethod = getServerSideStreamFunMethod =
              io.grpc.MethodDescriptor.<filetransfer.Filetransfer.RequestData, filetransfer.Filetransfer.ResponseData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerSideStreamFun"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filetransfer.Filetransfer.RequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  filetransfer.Filetransfer.ResponseData.getDefaultInstance()))
              .setSchemaDescriptor(new FileTransferServiceMethodDescriptorSupplier("ServerSideStreamFun"))
              .build();
        }
      }
    }
    return getServerSideStreamFunMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FileTransferServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceStub>() {
        @java.lang.Override
        public FileTransferServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileTransferServiceStub(channel, callOptions);
        }
      };
    return FileTransferServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FileTransferServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceBlockingStub>() {
        @java.lang.Override
        public FileTransferServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileTransferServiceBlockingStub(channel, callOptions);
        }
      };
    return FileTransferServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FileTransferServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FileTransferServiceFutureStub>() {
        @java.lang.Override
        public FileTransferServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FileTransferServiceFutureStub(channel, callOptions);
        }
      };
    return FileTransferServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class FileTransferServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public void serverSideStreamFun(filetransfer.Filetransfer.RequestData request,
        io.grpc.stub.StreamObserver<filetransfer.Filetransfer.ResponseData> responseObserver) {
      asyncUnimplementedUnaryCall(getServerSideStreamFunMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getServerSideStreamFunMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                filetransfer.Filetransfer.RequestData,
                filetransfer.Filetransfer.ResponseData>(
                  this, METHODID_SERVER_SIDE_STREAM_FUN)))
          .build();
    }
  }

  /**
   */
  public static final class FileTransferServiceStub extends io.grpc.stub.AbstractAsyncStub<FileTransferServiceStub> {
    private FileTransferServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileTransferServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileTransferServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public void serverSideStreamFun(filetransfer.Filetransfer.RequestData request,
        io.grpc.stub.StreamObserver<filetransfer.Filetransfer.ResponseData> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getServerSideStreamFunMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FileTransferServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<FileTransferServiceBlockingStub> {
    private FileTransferServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileTransferServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileTransferServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public java.util.Iterator<filetransfer.Filetransfer.ResponseData> serverSideStreamFun(
        filetransfer.Filetransfer.RequestData request) {
      return blockingServerStreamingCall(
          getChannel(), getServerSideStreamFunMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FileTransferServiceFutureStub extends io.grpc.stub.AbstractFutureStub<FileTransferServiceFutureStub> {
    private FileTransferServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FileTransferServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FileTransferServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SERVER_SIDE_STREAM_FUN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FileTransferServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FileTransferServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SERVER_SIDE_STREAM_FUN:
          serviceImpl.serverSideStreamFun((filetransfer.Filetransfer.RequestData) request,
              (io.grpc.stub.StreamObserver<filetransfer.Filetransfer.ResponseData>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class FileTransferServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FileTransferServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return filetransfer.Filetransfer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FileTransferService");
    }
  }

  private static final class FileTransferServiceFileDescriptorSupplier
      extends FileTransferServiceBaseDescriptorSupplier {
    FileTransferServiceFileDescriptorSupplier() {}
  }

  private static final class FileTransferServiceMethodDescriptorSupplier
      extends FileTransferServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    FileTransferServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FileTransferServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FileTransferServiceFileDescriptorSupplier())
              .addMethod(getServerSideStreamFunMethod())
              .build();
        }
      }
    }
    return result;
  }
}
