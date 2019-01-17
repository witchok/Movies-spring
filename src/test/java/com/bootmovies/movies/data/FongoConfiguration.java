package com.bootmovies.movies.data;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import com.github.fakemongo.Fongo;

import java.util.Arrays;
import java.util.Collection;

@Profile("test")
@ComponentScan(basePackages = "com.bootmovies.movies")
@EnableMongoRepositories(basePackageClasses = MovieRepository.class)
@Configuration
public class FongoConfiguration extends MongoConfigurationSupport {


    @Override
    protected String getDatabaseName() {
        return "demo-test";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Arrays.asList(new String[]{"com.bootmovies.movies.domain"});
    }

    @Bean
    public Mongo mongo(){
        return new Fongo("mongo-test").getMongo();
    }
}
