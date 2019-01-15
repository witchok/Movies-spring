//package com.bootmovies.movies;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.bootmovies.movies.data.MovieRepository;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
////import de.flapdoodle.embed.mongo.MongodExecutable;
////import de.flapdoodle.embed.mongo.MongodStarter;
////import de.flapdoodle.embed.mongo.config.IMongodConfig;
////import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
////import de.flapdoodle.embed.mongo.config.Net;
////import de.flapdoodle.embed.mongo.distribution.Version;
////import de.flapdoodle.embed.process.runtime.Network;
//import org.junit.After;
//import org.junit.Test;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.io.IOException;
//import java.net.UnknownHostException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class HomeControllerTest {
//    private MongodExecutable mongodExecutable;
//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setupDB() throws Exception {
//        String ip="localhost";
//        int port = 27017;
//
//        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//                .net(new Net(ip, port, Network.localhostIsIPv6()))
//                .build();
//
//        MongodStarter mongodStarter = MongodStarter.getDefaultInstance();
//        mongodExecutable = mongodStarter.prepare(mongodConfig);
//        mongodExecutable.start();
//        mongoTemplate = new MongoTemplate(new MongoClient(ip, port),"test");
//    }
//
//    @AfterEach
//    void clean(){
//        mongodExecutable.stop();
//    }
//
////    @Test
////    public void shouldReturnHomeViewName() throws Exception{
////        this.mockMvc.perform(get("/"))
////                .andExpect(status().isOk())
////                .andExpect(view().name("home"));
////    }
//}
