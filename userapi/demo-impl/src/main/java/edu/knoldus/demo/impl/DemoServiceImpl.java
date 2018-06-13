package edu.knoldus.demo.impl;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import edu.knoldus.demo.api.DemoService;
import edu.knoldus.demo.api.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DemoServiceImpl implements DemoService {

    List<UserInfo> userDetails = new ArrayList<>();

    @Override
    public ServiceCall<UserInfo, List<UserInfo>> addUser() {
        return request -> {
            //User newUser = new User(request.userId,request.userName,request.qualification,request.trackAssigned);
            UserInfo userInfo = UserInfo
                    .builder()
                    .userId(request.getUserId())
                    .userName(request.getUserName())
                    .qualification(request.getQualification())
                    .trackAssigned(request.getTrackAssigned()).build();

            userDetails.add(userInfo);
            return CompletableFuture.completedFuture(userDetails);

        };
    }

    @Override
    public ServiceCall<NotUsed, List<UserInfo>> getUser(int id) {
        return request -> CompletableFuture.completedFuture(userDetails.stream()
                .filter(x -> x.userId == id).collect(Collectors.toList()));
    }

    @Override
    public ServiceCall<NotUsed, List<UserInfo>> deleteUser(int id) {
        userDetails.remove(userDetails.stream()
                .filter(x -> x.userId == id).collect(Collectors.toList()).get(0));
        return request -> CompletableFuture.completedFuture(userDetails);
    }
}
