package com.TaskForge.taskForge.Controller;

import com.TaskForge.taskForge.DTO.CreateTaskRequest;
import com.TaskForge.taskForge.DTO.UpdateTaskRequest;
import com.TaskForge.taskForge.Model.Priority;
import com.TaskForge.taskForge.Model.Task;
import com.TaskForge.taskForge.Model.TaskStatus;
import com.TaskForge.taskForge.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(taskService.create(request));
    }


    @GetMapping
    public ResponseEntity<Page<Task>> getAll(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(taskService.getAllPaged(status, priority, q, pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable String id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable String id, @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        taskService.delete(id);
        return ResponseEntity.ok("Task Deleted Successfully");
    }
}