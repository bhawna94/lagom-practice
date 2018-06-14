package edu.knoldus.demo.impl;

import akka.NotUsed;
import akka.japi.Pair;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import edu.knoldus.demo.api.DemoService;
import edu.knoldus.demo.api.UserInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DemoServiceImpl implements DemoService {

    private static List<UserInfo> userDetails = new ArrayList<>();
    private final CassandraSession cassandraSession;

    private  Pair<ResponseHeader, UserInfo> concatHeader(ResponseHeader responseHeader, String id) {
        CompletionStage<UserInfo> user = cassandraSession.selectOne("select * from user.userdetail where user_id =?",id).thenApply(
                row -> (UserInfo.builder().userName(row.get().getString("username"))
                        .qualification(row.get().getString("qualification"))
                        .trackAssigned(row.get().getString("trackassigned")).build()
                ));

               user.
        return Pair.create(responseHeader, );
    }
    @Inject
    public DemoServiceImpl(CassandraSession cassandraSession) {
        this.cassandraSession = cassandraSession;
    }


    @Override
    public ServiceCall<UserInfo, String> addUser() {
        return request ->
                //User newUser = new User(request.userId,request.userName,request.qualification,request.trackAssigned);
                cassandraSession.executeWrite("insert into user.userdetail(user_id,qualification, trackassigned, username) values(?,?,?,?)"
                        , request.getUserId(), request.getQualification(), request.getTrackAssigned(), request.getUserName())
                        .thenApply(done -> "inserted");


    }

    @Override
    public ServiceCall<NotUsed, List<UserInfo>> getUser(int id) {
        return request -> cassandraSession.selectOne("select * from user.userdetail where user_id =?", id)
                .thenApply(row -> {
                    System.out.println("row is " + row.get());
                    return Arrays.asList(UserInfo.builder().userName(row.get().getString("username"))
                            .qualification(row.get().getString("qualification"))
                            .trackAssigned(row.get().getString("trackassigned"))
                            .build());
                });
    }

    @Override
    public ServiceCall<NotUsed, String> deleteUser(int id) {
        return request -> cassandraSession.executeWrite("delete from user.userdetail where user_id = ?", id)
                .thenApply(done -> "deleted");

    }

    @Override
    public HeaderServiceCall<NotUsed, String> getUserHeaderCall() {
        return (requestHeader, request) -> {
            String id = requestHeader.getHeader("id").get();


            return CompletableFuture.completedFuture(concatHeader(ResponseHeader.OK.withStatus(200),id));

        };


    }
}
