package com.TaskForge.taskForge.DTO;

import com.TaskForge.taskForge.Model.TaskStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateTaskRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime dueDate;
}
