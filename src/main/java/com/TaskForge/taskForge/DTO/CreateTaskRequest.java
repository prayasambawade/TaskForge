package com.TaskForge.taskForge.DTO;

import com.TaskForge.taskForge.Model.Priority;
import com.TaskForge.taskForge.Model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateTaskRequest {

    @NotBlank(message = "Title is requied")
    private String title;

    private String description;

    private TaskStatus status;

    private LocalDateTime dueDate;

    private Priority priority;
}
