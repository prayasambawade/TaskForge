package com.TaskForge.taskForge.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "TaskForge API is Running";
    }


}
