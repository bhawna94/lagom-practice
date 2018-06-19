package edu.knoldus.demo.impl;

import edu.knoldus.demo.api.api.UserInfo;
import edu.knoldus.demo.api.impl.DemoServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DemoServiceImplTest {

@BeforeClass
        public static void initialise() throws Exception{
    List<UserInfo> userDetails = new ArrayList<>();
    userDetails.add(UserInfo.builder().userId(2).userName("bhawna")
            .qualification("MCA")
            .trackAssigned("stateroom")
            .build());
}
    DemoServiceImpl service = new DemoServiceImpl();

    @Test
    public void shouldInvokeAddUser() throws Exception {
        List<UserInfo> userInfo = Collections.singletonList(UserInfo.builder().userId(1).userName("bhawna")
                .qualification("MCA")
                .trackAssigned("stateroom")
                .build());
        List<UserInfo> user1 = service.addUser().invoke(UserInfo.builder()
                .userId(1)
                .userName("bhawna")
                .qualification("MCA")
                .trackAssigned("stateroom")
                .build()).toCompletableFuture().get(5, TimeUnit.SECONDS);
        assertEquals(userInfo, user1);

    }


    @Test
    public void shouldInvokeGetUser() throws Exception {
//        List<UserInfo> userDetails = new ArrayList<>();
//        userDetails.add(UserInfo.builder().userId(1).userName("bhawna")
//                .qualification("MCA")
//                .trackAssigned("stateroom")
//                .build());
        List<UserInfo> userInfo =new ArrayList<>();
        List<UserInfo> user1 = service.getUser(1).invoke().toCompletableFuture().get(5, TimeUnit.SECONDS);
        assertEquals(userInfo, user1);
    }

//    @Test
//    public void shouldInvokeDeleteUser() throws Exception {
//
//        List<UserInfo> userInfo = new ArrayList<>();
//        // DemoService service = server.client(DemoService.class);
//        List<UserInfo> userDetails = new ArrayList<>();
//        userDetails.add(UserInfo.builder().userId(2).userName("bhawna")
//                .qualification("MCA")
//                .trackAssigned("stateroom")
//                .build());
//        System.out.println(userDetails);
//        List<UserInfo> user1 = service.deleteUser(2).invoke().toCompletableFuture().get(5, TimeUnit.SECONDS);
//        assertEquals(userInfo, user1);
//
//    }


}