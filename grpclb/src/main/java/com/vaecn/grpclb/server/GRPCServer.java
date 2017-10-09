package com.vaecn.grpclb.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * Created by sifan on 2017/10/9.
 */
public class GRPCServer {

    public static void main(String[] args) {
        Server server1 = ServerBuilder.forPort(5050).addService(UserRpcServiceImpl.getInstance())
                .build();

        Server server2 = ServerBuilder.forPort(5051).addService(UserRpcServiceImpl.getInstance())
                .build();

        try {
            server1.start();
            server1.awaitTermination();
            server2.start();
            server2.awaitTermination();


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
