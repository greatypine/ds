package com.guoanshequ.dc.das.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:/config/mongodb.properties")
@EnableMongoRepositories
public class MongodbConfig extends AbstractMongoConfiguration {

    private @Value("${mongodb.hosts}") String[] hosts;
    private @Value("${mongodb.ports}") int[] ports;
    private @Value("${mongodb.database}") String database;
    private @Value("${mongodb.username}") String username;
    private @Value("${mongodb.password}") String password;

    @Override
    public MongoClient mongoClient() {
        int hostCnt = hosts.length;
        List<ServerAddress> serverAddresses = new ArrayList<>();
        List<MongoCredential> mongoCredentials = new ArrayList<>();
        mongoCredentials.add(MongoCredential.createCredential(username, database, password.toCharArray()));
        for(int i = 0; i < hostCnt; i++) {
            serverAddresses.add(new ServerAddress(hosts[i], ports[i]));
        }
        return new MongoClient(serverAddresses, mongoCredentials);
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {

        //remove _class
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);
        return mongoTemplate;
    }

}
