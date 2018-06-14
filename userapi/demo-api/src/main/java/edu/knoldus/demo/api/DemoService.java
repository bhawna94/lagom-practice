package edu.knoldus.demo.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.List;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.transport.Method.DELETE;
import static com.lightbend.lagom.javadsl.api.transport.Method.POST;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;

public interface DemoService extends Service {

    ServiceCall<UserInfo,String > addUser();
    ServiceCall<NotUsed,List<UserInfo>> getUser(int id);
    ServiceCall<NotUsed,String> deleteUser(int id);
    ServiceCall<NotUsed,String> getUserHeaderCall();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("demo").withCalls(
                Service.restCall(POST, "/api/demo", this::addUser),
                Service.restCall(GET,"/api/getDemo/:id", this::getUser),
                Service.restCall(DELETE,"/api/deleteDemo/:id",this::deleteUser),
                Service.restCall(GET,"/api/getHeader",this::getUserHeaderCall)

                //Service.restCall(PUT,)
        ).withAutoAcl(true);
    }

}
