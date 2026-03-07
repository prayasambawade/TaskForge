package com.TaskForge.taskForge.Service;

import com.TaskForge.taskForge.DTO.CreateTaskRequest;
import com.TaskForge.taskForge.DTO.UpdateTaskRequest;
import com.TaskForge.taskForge.Model.Priority;
import com.TaskForge.taskForge.Model.Task;
import com.TaskForge.taskForge.Model.TaskStatus;
import com.TaskForge.taskForge.Model.User;
import com.TaskForge.taskForge.Repository.TaskRepository;
import com.TaskForge.taskForge.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task create(CreateTaskRequest request) {

        String userId = getCurrentUserId();

        TaskStatus status = (request.getStatus() == null)
                ? TaskStatus.TODO
                : request.getStatus();

        Task task = Task.builder()
                .userId(userId)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(status)
                .priority(request.getPriority()) // make sure CreateTaskRequest has priority
                .dueDate(request.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return taskRepository.save(task);
    }

    // Old non-paged (optional)
    public List<Task> getAll() {
        String userId = getCurrentUserId();
        return taskRepository.findByUserId(userId);
    }

    // ✅ Pagination + Filtering
    public Page<Task> getAllPaged(TaskStatus status, Priority priority, String q, Pageable pageable) {

        String userId = getCurrentUserId();

        boolean hasStatus = (status != null);
        boolean hasPriority = (priority != null);
        boolean hasQ = (q != null && !q.trim().isEmpty());

        if (hasQ) {
            String query = q.trim();
            return taskRepository
                    .findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndDescriptionContainingIgnoreCase(
                            userId, query, userId, query, pageable
                    );
        }

        if (hasStatus && hasPriority) {
            return taskRepository.findByUserIdAndStatusAndPriority(userId, status, priority, pageable);
        }

        if (hasStatus) {
            return taskRepository.findByUserIdAndStatus(userId, status, pageable);
        }

        if (hasPriority) {
            return taskRepository.findByUserIdAndPriority(userId, priority, pageable);
        }

        return taskRepository.findByUserId(userId, pageable);
    }

    public Task getById(String id) {
        String userId = getCurrentUserId();
        return taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task update(String id, UpdateTaskRequest request) {
        Task task = getById(id);

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());

        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void delete(String id) {
        String userId = getCurrentUserId();

        if (!taskRepository.existsByIdAndUserId(id, userId)) {
            throw new RuntimeException("Task not found");
        }

        taskRepository.deleteByIdAndUserId(id, userId);
    }

    private String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            throw new RuntimeException("Unauthenticated user");
        }

        String email = authentication.getName();
        System.out.println("Authenticated email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return user.getId();
    }
}