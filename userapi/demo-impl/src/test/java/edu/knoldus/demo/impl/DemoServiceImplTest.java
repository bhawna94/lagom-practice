package edu.knoldus.demo.impl;

import com.datastax.driver.core.Session;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import edu.knoldus.demo.api.DemoService;
import edu.knoldus.demo.api.UserInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static edu.knoldus.demo.impl.DemoServiceImplTest.createSchema;
import static org.junit.Assert.assertEquals;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.startServer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DemoServiceImplTest {
    private static ServiceTest.TestServer server;


   @BeforeClass
    public static void setUp() throws Exception{

       final ServiceTest.Setup setup = defaultSetup();

       server = startServer(setup.withCassandra(true));

       CassandraSession cassandraSession = server.injector().instanceOf(CassandraSession.class);

       Session session = cassandraSession.underlying().toCompletableFuture().get();

       createSchema(session);



   }

  public static void createSchema(Session session){
       session.execute("CREATE KEYSPACE user WITH replication = {'class': 'SimpleStrategy', 'replication_factor':1};");
       session.execute("CREATE TABLE user.userdetail(user_id int PRIMARY KEY, username text, qualification text, trackassigned text);");
       session.execute("insert into user.userdetail(user_id, username, qualification, trackassigned) values(1,'bhawna','MCA','stateroom');");
       session.execute("insert into user.userdetail(user_id, username, qualification, trackassigned) values(2,'neel','MCA','commerece');");
   }

   @AfterClass

   public static void tearDown() {
       if (server != null) {
           server.stop();
           server = null;
       }
   }
   DemoService demoService =server.client(DemoService.class);

   @Test
    public void callPostMethod() throws Exception {
    String message = demoService.addUser()
            .invoke(UserInfo.builder().userId(3)
                    .userName("bhawna")
                    .qualification("MCA")
                    .trackAssigned("core-commerce")
                    .build())
                    .toCompletableFuture().get(5, TimeUnit.SECONDS);
    assertEquals("inserted",message);
   }

   @Test
    public void callGetMethod() throws Exception {
       /*List<UserInfo> userInfos = Collections.singletonList(UserInfo.builder()
               .userId(1).userName("bhawna").qualification("MCA").trackAssigned("stateRoom")
               .build());*/

       List<UserInfo> userInfoList = demoService.getUser(1).invoke()
               .toCompletableFuture().get(5,TimeUnit.SECONDS);

       assertEquals("bhawna",userInfoList.get(0).userName);
   }

   @Test
    public void callDeleteMethod() throws Exception {

       String message = demoService.deleteUser(2).invoke().toCompletableFuture().get(5,TimeUnit.SECONDS);
       assertEquals("deleted", message);
   }
}
