package com.TaskForge.taskForge.Controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final MongoTemplate mongoTemplate;

    public TestController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/db")
    public String testDB() {
        mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
        return "MongoDB Atlas Connected ✅";
    }
}
