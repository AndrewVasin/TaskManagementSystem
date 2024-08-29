package com.example.taskmanager.mapper;

import com.example.taskmanager.persist.dto.CommentRequest;
import com.example.taskmanager.persist.dto.CommentResponse;
import com.example.taskmanager.persist.entities.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CommentMapper.class)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentResponse toDto(Comment comment);

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "task.id", source = "taskId")
    Comment dtoToObj(CommentRequest commentRequest);
}
