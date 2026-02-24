package com.TaskForge.taskForge.Service;

import com.TaskForge.taskForge.DTO.CreateTaskRequest;
import com.TaskForge.taskForge.DTO.UpdateTaskRequest;
import com.TaskForge.taskForge.Model.Task;
import com.TaskForge.taskForge.Model.TaskStatus;
import com.TaskForge.taskForge.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(CreateTaskRequest request){

        TaskStatus status = request.getStatus() == null
                ? TaskStatus.TODO
                : request.getStatus();

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(status)
                .dueDate(request.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task getById(String id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task Not found " + id));
    }
    public Task update(String id, UpdateTaskRequest request){
        Task task = getById(id);

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void delete(String id){
        if (!taskRepository.existsById(id)){
            throw new RuntimeException("Task not found" +id);

        }
        taskRepository.deleteById(id);
    }


}
