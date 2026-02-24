package com.TaskForge.taskForge.Repository;

import com.TaskForge.taskForge.Model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository  extends MongoRepository<Task, String> {

    List<Task> findByUserId(String userid );


    Optional<Task> findByIdAndUserId(String id, String userId);
}
