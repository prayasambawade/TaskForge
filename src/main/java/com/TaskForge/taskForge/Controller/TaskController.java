package com.TaskForge.taskForge.Controller;

import com.TaskForge.taskForge.DTO.CreateTaskRequest;
import com.TaskForge.taskForge.DTO.UpdateTaskRequest;
import com.TaskForge.taskForge.Model.Task;
import com.TaskForge.taskForge.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.TextStyle;
import java.util.List;
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor


public class TaskController {

    private final TaskService taskService;




    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody CreateTaskRequest request){

        return ResponseEntity.ok(taskService.create(request));

    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll(){
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable String id){
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update (@PathVariable String id, @RequestBody UpdateTaskRequest request){
        return ResponseEntity.ok(taskService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String > delete (@PathVariable String id){
        taskService.delete(id);
        return ResponseEntity.ok("Task Deleted Succesfully");
    }
}
