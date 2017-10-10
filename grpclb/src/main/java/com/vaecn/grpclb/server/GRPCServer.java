package com.vaecn.grpclb.server;

import com.vaecn.grpclb.discovery.ZookeeperServiceRegistrationOps;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.net.URI;

/**
 * Created by sifan on 2017/10/9.
 */
public class GRPCServer {

    public static void main(String[] args) {
        int port1 = 5050;
        int port2 = 5051;
        Server server1 = ServerBuilder.forPort(port1).addService(new HelloRpcServiceImpl(port1))
                .build();

        Server server2 = ServerBuilder.forPort(port2).addService(new HelloRpcServiceImpl(port2))
                .build();

        try {
            server1.start();
            server2.start();

            ZookeeperServiceRegistrationOps zookeeperServiceRegistrationOps = new ZookeeperServiceRegistrationOps("localhost:2181");
            zookeeperServiceRegistrationOps.removeServiceRegistry("helloService");
            zookeeperServiceRegistrationOps.registerService("helloService", URI.create("dns://localhost:" + port1));
            zookeeperServiceRegistrationOps.registerService("helloService", URI.create("dns://localhost:" + port2));

            server1.awaitTermination();
            server2.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
