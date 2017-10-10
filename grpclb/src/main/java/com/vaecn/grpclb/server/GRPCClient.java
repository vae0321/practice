package com.vaecn.grpclb.server;

import com.vaecn.grpclb.discovery.ZookeeperZoneAwareNameResolverProvider;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.util.RoundRobinLoadBalancerFactory;
import lombok.extern.log4j.Log4j2;

/**
 * Created by sifan on 2017/10/10.
 */
@Log4j2
public class GRPCClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("zk://helloService")
                .nameResolverFactory(ZookeeperZoneAwareNameResolverProvider.newBuilder()
                        .setZookeeperAddress("localhost:2181")
                        .build())
                .usePlaintext(true)
                .loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
                .build();

        HelloRpcServiceGrpc.HelloRpcServiceBlockingStub stub = HelloRpcServiceGrpc.newBlockingStub(channel);
        HelloRpcServiceProto.SayHelloResponse response = stub.sayHello(HelloRpcServiceProto.SayHelloRequest.newBuilder()
                .setRequest("hello gRPC !")
                .build());
        log.info("response: {}", response.getResponse());

        channel.shutdownNow();
    }
}
