package com.vaecn.grpclb.server;

import io.grpc.stub.StreamObserver;

/**
 * Created by sifan on 2017/10/9.
 */
public class UserRpcServiceImpl extends UserRpcServiceGrpc.UserRpcServiceImplBase {

    private UserRpcServiceImpl() {
    }

    private static class UserRpcServiceImplHolder {
        private static final UserRpcServiceImpl instance = new UserRpcServiceImpl();
    }

    public static UserRpcServiceImpl getInstance() {
        return UserRpcServiceImplHolder.instance;
    }

    @Override
    public void getAllUsers(UserRpcServiceProto.GetAllUsersRequest request,
                            StreamObserver<UserRpcServiceProto.Users> responseObserver) {
        super.getAllUsers(request, responseObserver);
    }

    @Override
    public void addUser(UserRpcServiceProto.AddUserRequest request,
                        StreamObserver<UserRpcServiceProto.User> responseObserver) {
        super.addUser(request, responseObserver);
    }
}