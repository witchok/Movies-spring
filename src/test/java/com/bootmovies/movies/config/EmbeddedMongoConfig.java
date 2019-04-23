package com.bootmovies.movies.config;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;

@Primary
@Profile("integrating-test")
@EnableMongoRepositories("com.bootmovies.movies.data")
@ComponentScan(basePackages="com.bootmovies.movies.data",basePackageClasses = PasswordEncoderConfig.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MvcConfig.class)})
public class EmbeddedMongoConfig {

    public static final String IP = "localhost";
    public static final int PORT = 27027;

    @Bean
    public EmbeddedMongoFactoryBean mongo(){
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(IP);
        mongo.setPort(PORT);
        return mongo;
    }

    @Bean
    public MongoTemplate mongoTemplate(EmbeddedMongoFactoryBean mongo) throws IOException {
        return new MongoTemplate(mongo.getObject(), "controllerMethodsPointcat");
    }


}
