package com.TaskForge.taskForge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoUriCheck implements CommandLineRunner {

    @Value("${spring.mongodb.uri:NOT_FOUND}")
    private String mongoUri;

    @Value("${spring.mongodb.database:taskforge}")
    private String dbName;

    @Override
    public void run(String... args) {
        System.out.println("✅ spring.mongodb.uri = " + mongoUri);
        System.out.println("✅ spring.mongodb.database = " + dbName);
    }
}