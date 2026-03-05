package com.TaskForge.taskForge.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {

    @GetMapping("/")
    public String home(){
        return "TaskForge API is Running";
    }
}
