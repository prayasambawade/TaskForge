package com.TaskForge.taskForge.Repository;

import com.TaskForge.taskForge.Model.Priority;
import com.TaskForge.taskForge.Model.Task;
import com.TaskForge.taskForge.Model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    // ----- existing (non-paged) -----
    List<Task> findByUserId(String userId);

    // ----- ownership-safe single item -----
    Optional<Task> findByIdAndUserId(String id, String userId);
    boolean existsByIdAndUserId(String id, String userId);
    void deleteByIdAndUserId(String id, String userId);

    // ----- pagination base -----
    Page<Task> findByUserId(String userId, Pageable pageable);

    // ----- filters -----
    Page<Task> findByUserIdAndStatus(String userId, TaskStatus status, Pageable pageable);
    Page<Task> findByUserIdAndPriority(String userId, Priority priority, Pageable pageable);
    Page<Task> findByUserIdAndStatusAndPriority(String userId, TaskStatus status, Priority priority, Pageable pageable);

    // ----- search (title OR description) -----
    Page<Task> findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndDescriptionContainingIgnoreCase(
            String userId1, String title,
            String userId2, String description,
            Pageable pageable
    );
}