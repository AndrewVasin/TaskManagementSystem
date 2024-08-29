package com.example.taskmanager.persist.dto;

import com.example.taskmanager.persist.entities.enums.TaskPriority;
import com.example.taskmanager.persist.entities.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Task DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "TaskRequest", description = "Task DTO")
public class TaskRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("header")
    private String header;

    @JsonProperty("description")
    private String description;

    @JsonProperty("taskStatus")
    private TaskStatus taskStatus;

    @JsonProperty("taskPriority")
    private TaskPriority taskPriority;

    @ManyToOne
    @JsonProperty("author_id")
    private Long authorId;

    @ManyToOne
    @JsonProperty("executor_id")
    private Long executorId;


    public TaskRequest header(String header) {
        this.header = header;
        return this;
    }

    public TaskRequest description(String description) {
        this.description = description;
        return this;
    }

    public TaskRequest taskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public TaskRequest taskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
        return this;
    }

    public TaskRequest authorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public TaskRequest executorId(Long executorId) {
        this.executorId = executorId;
        return this;
    }


    /**
     * Get authorId
     * @return authorId
     */
    @Schema(name = "author_id")
    public Long getAuthorId() {
        return this.authorId;
    }

    @Schema(name = "executor_id")
    public Long getExecutorId() {
        return this.executorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId=authorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId=executorId;
    }

    @Schema(name = "header")
    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Schema(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Schema(name = "taskStatus")
    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Schema(name = "taskPriority")
    public TaskPriority getTaskPriority() {
        return this.taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

}
