package com.example.taskmanager.mapper;

import com.example.taskmanager.persist.dto.CommentRequest;
import com.example.taskmanager.persist.dto.TaskRequest;
import com.example.taskmanager.persist.dto.TaskResponse;
import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.Task;
import com.example.taskmanager.persist.entities.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    @Test
    void givenTaskDTOwithDiffNametoTask_whenMaps_thenCorrect() {

        TaskRequest dto = new TaskRequest();
        dto.setAuthorId(1L);
        dto.setExecutorId(5L);

        Task entity = TaskMapper.INSTANCE.dtoToObj(dto);

        assertEquals(dto.getAuthorId(), entity.getAuthor().getId());
        assertEquals(dto.getExecutorId(), entity.getExecutor().getId());
    }

    @Test
    void givenTaskwithDiffNametoTaskDTO_whenMaps_thenCorrect() {

        User author = new User();
        author.setId(1L);

        User executor = new User();
        executor.setId(5L);

        Task entity = new Task();
        entity.setAuthor(author);
        entity.setExecutor(executor);

        TaskResponse dto = TaskMapper.INSTANCE.toDto(entity);

        assertEquals(entity.getAuthor().getId(), dto.getAuthorId());
        assertEquals(entity.getExecutor().getId(), dto.getExecutorId());
    }
}