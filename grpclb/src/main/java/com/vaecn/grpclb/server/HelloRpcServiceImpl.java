package com.vaecn.grpclb.server;

import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;

/**
 * Created by sifan on 2017/10/9.
 */
@Log4j2
public class HelloRpcServiceImpl extends HelloRpcServiceGrpc.HelloRpcServiceImplBase {

    private int port;

    public HelloRpcServiceImpl(int port) {
        this.port = port;
    }

//    private HelloRpcServiceImpl() {
//    }
//
//    private static class UserRpcServiceImplHolder {
//        private static final HelloRpcServiceImpl instance = new HelloRpcServiceImpl();
//    }
//
//    public static HelloRpcServiceImpl getInstance() {
//        return UserRpcServiceImplHolder.instance;
//    }

    @Override
    public void sayHello(HelloRpcServiceProto.SayHelloRequest request, StreamObserver<HelloRpcServiceProto.SayHelloResponse> responseObserver) {
        log.info("port: {} received request: {}", port, request.getRequest());
        responseObserver.onNext(HelloRpcServiceProto.SayHelloResponse.newBuilder()
                .setResponse("nice to meet you!")
                .build());
        responseObserver.onCompleted();
    }
}