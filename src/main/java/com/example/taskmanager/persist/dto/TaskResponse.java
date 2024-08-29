package com.example.taskmanager.persist.dto;

import com.example.taskmanager.persist.entities.enums.TaskPriority;
import com.example.taskmanager.persist.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Task Response DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "TaskResponse", description = "Task Response DTO")
@Getter
@Setter
public class TaskResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("header")
    private String header;

    @JsonProperty("description")
    private String description;

    @JsonProperty("taskStatus")
    private TaskStatus taskStatus;

    @JsonProperty("taskPriority")
    private TaskPriority taskPriority;

    @ManyToOne
    @JsonProperty("authorId")
    private Long authorId;

    @ManyToOne
    @JsonProperty("executorId")
    private Long executorId;

    public TaskResponse header(String header) {
        this.header = header;
        return this;
    }

    public TaskResponse description(String description) {
        this.description = description;
        return this;
    }

    public TaskResponse taskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public TaskResponse taskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
        return this;
    }

    public TaskResponse authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public TaskResponse executorId(Long executorId) {
        this.executorId = executorId;
        return this;
    }

}
