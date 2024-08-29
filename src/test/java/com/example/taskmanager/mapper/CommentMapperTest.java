package com.example.taskmanager.mapper;

import com.example.taskmanager.persist.dto.CommentRequest;
import com.example.taskmanager.persist.dto.CommentResponse;
import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.Task;
import com.example.taskmanager.persist.entities.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    @Test
    void givenCommentDTOwithDiffNametoComment_whenMaps_thenCorrect() {

        CommentRequest dto = new CommentRequest();
        dto.setAuthorId(1L);
        dto.setTaskId(5L);

        Comment entity = CommentMapper.INSTANCE.dtoToObj(dto);

        assertEquals(dto.getAuthorId(), entity.getAuthor().getId());
        assertEquals(dto.getTaskId(), entity.getTask().getId());
    }

    @Test
    void givenCommentwithDiffNametoCommentDTO_whenMaps_thenCorrect() {

        User author = new User();
        author.setId(1L);

        Task task = new Task();
        task.setId(5L);

        Comment entity = new Comment();
        entity.setAuthor(author);
        entity.setTask(task);

        CommentResponse dto = CommentMapper.INSTANCE.toDto(entity);

        assertEquals(entity.getAuthor().getId(), dto.getAuthorId());
        assertEquals(entity.getTask().getId(), dto.getTaskId());
    }
}