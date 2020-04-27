package com.biao.grpc.stream;

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
 * <pre>
 * 服务接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: stream.proto")
public final class StreamServiceGrpc {

  private StreamServiceGrpc() {}

  public static final String SERVICE_NAME = "StreamService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getSimpleFunMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SimpleFun",
      requestType = com.biao.grpc.stream.Stream.RequestData.class,
      responseType = com.biao.grpc.stream.Stream.ResponseData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getSimpleFunMethod() {
    io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData> getSimpleFunMethod;
    if ((getSimpleFunMethod = StreamServiceGrpc.getSimpleFunMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getSimpleFunMethod = StreamServiceGrpc.getSimpleFunMethod) == null) {
          StreamServiceGrpc.getSimpleFunMethod = getSimpleFunMethod =
              io.grpc.MethodDescriptor.<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SimpleFun"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.RequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.ResponseData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("SimpleFun"))
              .build();
        }
      }
    }
    return getSimpleFunMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getServerSideStreamFunMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServerSideStreamFun",
      requestType = com.biao.grpc.stream.Stream.RequestData.class,
      responseType = com.biao.grpc.stream.Stream.ResponseData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getServerSideStreamFunMethod() {
    io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData> getServerSideStreamFunMethod;
    if ((getServerSideStreamFunMethod = StreamServiceGrpc.getServerSideStreamFunMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getServerSideStreamFunMethod = StreamServiceGrpc.getServerSideStreamFunMethod) == null) {
          StreamServiceGrpc.getServerSideStreamFunMethod = getServerSideStreamFunMethod =
              io.grpc.MethodDescriptor.<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServerSideStreamFun"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.RequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.ResponseData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("ServerSideStreamFun"))
              .build();
        }
      }
    }
    return getServerSideStreamFunMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getClientSideStreamFunMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClientSideStreamFun",
      requestType = com.biao.grpc.stream.Stream.RequestData.class,
      responseType = com.biao.grpc.stream.Stream.ResponseData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getClientSideStreamFunMethod() {
    io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData> getClientSideStreamFunMethod;
    if ((getClientSideStreamFunMethod = StreamServiceGrpc.getClientSideStreamFunMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getClientSideStreamFunMethod = StreamServiceGrpc.getClientSideStreamFunMethod) == null) {
          StreamServiceGrpc.getClientSideStreamFunMethod = getClientSideStreamFunMethod =
              io.grpc.MethodDescriptor.<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClientSideStreamFun"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.RequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.ResponseData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("ClientSideStreamFun"))
              .build();
        }
      }
    }
    return getClientSideStreamFunMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getTwoWayStreamFunMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TwoWayStreamFun",
      requestType = com.biao.grpc.stream.Stream.RequestData.class,
      responseType = com.biao.grpc.stream.Stream.ResponseData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData,
      com.biao.grpc.stream.Stream.ResponseData> getTwoWayStreamFunMethod() {
    io.grpc.MethodDescriptor<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData> getTwoWayStreamFunMethod;
    if ((getTwoWayStreamFunMethod = StreamServiceGrpc.getTwoWayStreamFunMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getTwoWayStreamFunMethod = StreamServiceGrpc.getTwoWayStreamFunMethod) == null) {
          StreamServiceGrpc.getTwoWayStreamFunMethod = getTwoWayStreamFunMethod =
              io.grpc.MethodDescriptor.<com.biao.grpc.stream.Stream.RequestData, com.biao.grpc.stream.Stream.ResponseData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TwoWayStreamFun"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.RequestData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biao.grpc.stream.Stream.ResponseData.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("TwoWayStreamFun"))
              .build();
        }
      }
    }
    return getTwoWayStreamFunMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub>() {
        @java.lang.Override
        public StreamServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceStub(channel, callOptions);
        }
      };
    return StreamServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub>() {
        @java.lang.Override
        public StreamServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceBlockingStub(channel, callOptions);
        }
      };
    return StreamServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StreamServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub>() {
        @java.lang.Override
        public StreamServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceFutureStub(channel, callOptions);
        }
      };
    return StreamServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 服务接口
   * </pre>
   */
  public static abstract class StreamServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *简单rpc
     * </pre>
     */
    public void simpleFun(com.biao.grpc.stream.Stream.RequestData request,
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      asyncUnimplementedUnaryCall(getSimpleFunMethod(), responseObserver);
    }

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public void serverSideStreamFun(com.biao.grpc.stream.Stream.RequestData request,
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      asyncUnimplementedUnaryCall(getServerSideStreamFunMethod(), responseObserver);
    }

    /**
     * <pre>
     *客户端流式rpc
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.RequestData> clientSideStreamFun(
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      return asyncUnimplementedStreamingCall(getClientSideStreamFunMethod(), responseObserver);
    }

    /**
     * <pre>
     *双向流式rpc
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.RequestData> twoWayStreamFun(
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      return asyncUnimplementedStreamingCall(getTwoWayStreamFunMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSimpleFunMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.biao.grpc.stream.Stream.RequestData,
                com.biao.grpc.stream.Stream.ResponseData>(
                  this, METHODID_SIMPLE_FUN)))
          .addMethod(
            getServerSideStreamFunMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.biao.grpc.stream.Stream.RequestData,
                com.biao.grpc.stream.Stream.ResponseData>(
                  this, METHODID_SERVER_SIDE_STREAM_FUN)))
          .addMethod(
            getClientSideStreamFunMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                com.biao.grpc.stream.Stream.RequestData,
                com.biao.grpc.stream.Stream.ResponseData>(
                  this, METHODID_CLIENT_SIDE_STREAM_FUN)))
          .addMethod(
            getTwoWayStreamFunMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.biao.grpc.stream.Stream.RequestData,
                com.biao.grpc.stream.Stream.ResponseData>(
                  this, METHODID_TWO_WAY_STREAM_FUN)))
          .build();
    }
  }

  /**
   * <pre>
   * 服务接口
   * </pre>
   */
  public static final class StreamServiceStub extends io.grpc.stub.AbstractAsyncStub<StreamServiceStub> {
    private StreamServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *简单rpc
     * </pre>
     */
    public void simpleFun(com.biao.grpc.stream.Stream.RequestData request,
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSimpleFunMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public void serverSideStreamFun(com.biao.grpc.stream.Stream.RequestData request,
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getServerSideStreamFunMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *客户端流式rpc
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.RequestData> clientSideStreamFun(
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getClientSideStreamFunMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *双向流式rpc
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.RequestData> twoWayStreamFun(
        io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getTwoWayStreamFunMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * 服务接口
   * </pre>
   */
  public static final class StreamServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<StreamServiceBlockingStub> {
    private StreamServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *简单rpc
     * </pre>
     */
    public com.biao.grpc.stream.Stream.ResponseData simpleFun(com.biao.grpc.stream.Stream.RequestData request) {
      return blockingUnaryCall(
          getChannel(), getSimpleFunMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *服务器端流式rpc
     * </pre>
     */
    public java.util.Iterator<com.biao.grpc.stream.Stream.ResponseData> serverSideStreamFun(
        com.biao.grpc.stream.Stream.RequestData request) {
      return blockingServerStreamingCall(
          getChannel(), getServerSideStreamFunMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * 服务接口
   * </pre>
   */
  public static final class StreamServiceFutureStub extends io.grpc.stub.AbstractFutureStub<StreamServiceFutureStub> {
    private StreamServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *简单rpc
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.biao.grpc.stream.Stream.ResponseData> simpleFun(
        com.biao.grpc.stream.Stream.RequestData request) {
      return futureUnaryCall(
          getChannel().newCall(getSimpleFunMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SIMPLE_FUN = 0;
  private static final int METHODID_SERVER_SIDE_STREAM_FUN = 1;
  private static final int METHODID_CLIENT_SIDE_STREAM_FUN = 2;
  private static final int METHODID_TWO_WAY_STREAM_FUN = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SIMPLE_FUN:
          serviceImpl.simpleFun((com.biao.grpc.stream.Stream.RequestData) request,
              (io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData>) responseObserver);
          break;
        case METHODID_SERVER_SIDE_STREAM_FUN:
          serviceImpl.serverSideStreamFun((com.biao.grpc.stream.Stream.RequestData) request,
              (io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData>) responseObserver);
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
        case METHODID_CLIENT_SIDE_STREAM_FUN:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.clientSideStreamFun(
              (io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData>) responseObserver);
        case METHODID_TWO_WAY_STREAM_FUN:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.twoWayStreamFun(
              (io.grpc.stub.StreamObserver<com.biao.grpc.stream.Stream.ResponseData>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StreamServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.biao.grpc.stream.Stream.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StreamService");
    }
  }

  private static final class StreamServiceFileDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier {
    StreamServiceFileDescriptorSupplier() {}
  }

  private static final class StreamServiceMethodDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StreamServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (StreamServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamServiceFileDescriptorSupplier())
              .addMethod(getSimpleFunMethod())
              .addMethod(getServerSideStreamFunMethod())
              .addMethod(getClientSideStreamFunMethod())
              .addMethod(getTwoWayStreamFunMethod())
              .build();
        }
      }
    }
    return result;
  }
}
