package com.example.taskmanager.persist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CommentRequest", description = "Comment DTO")
public class CommentRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JsonProperty("author_id")
    private Long authorId;

    @ManyToOne
    @JsonProperty("task_id")
    private Long taskId;

    @JsonProperty("content")
    private String content;

    public CommentRequest authorId(Long userId) {
        this.authorId = userId;
        return this;
    }

    public CommentRequest taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public CommentRequest content(String content) {
        this.content = content;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
