package com.bootmovies.movies.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.net.UnknownHostException;

@Primary
@Profile("test")
@EnableMongoRepositories("com.bootmovies.movies.data")
public class EmbeddedMongoConfig {

    public static final String IP = "localhost";
    public static final int PORT = 27027;

//
//    @Autowired
//    private MongoProperties properties;
//
//    @Autowired(required = false)
//    private MongoClientOptions options;

    @Bean
    public MongodStarter mongodStarter(){
        return MongodStarter.getDefaultInstance();
    }

    @Bean
    public IMongodConfig mongodConfig() throws IOException {

        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();
        return mongodConfig;
    }

    @Bean
    public MongodExecutable mongodExecutable(IMongodConfig config, MongodStarter mongodStarter){
        return mongodStarter.prepare(config);
    }

//    @Bean
//    public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
//        return mongodExecutable.start();
//    }

//    @Bean

//    public Mongo mongo(MongodProcess mongodProcess) throws UnknownHostException {
//        Net net = mongodProcess.getConfig().net();
//        properties.setHost(net.getServerAddress().getHostName());
//        properties.setPort(net.getPort());
//        return properties.createMongoClient(this.options);
//    }
    @Bean
    public EmbeddedMongoFactoryBean mongo(){
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(IP);
        mongo.setPort(PORT);
        return mongo;
    }

    @Bean
    public MongoTemplate mongoTemplate(EmbeddedMongoFactoryBean mongo) throws IOException {
        return new MongoTemplate(mongo.getObject(), "test");
    }

//    @Bean
//    public MongoClient mongo(){
//        return new MongoClient(IP, PORT);
//    }

//    @Bean
//    public MongoDbFactory mongoDbFactory(Mongo mongo){
//        return new SimpleMongoDbFactory(),
//                properties.getDatabase());
//    }
}
