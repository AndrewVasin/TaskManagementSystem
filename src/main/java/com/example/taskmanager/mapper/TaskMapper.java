package com.example.taskmanager.mapper;

import com.example.taskmanager.persist.dto.TaskRequest;
import com.example.taskmanager.persist.dto.TaskResponse;
import com.example.taskmanager.persist.entities.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TaskMapper.class)
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "executorId", source = "executor.id")
    TaskResponse toDto(Task task);

    @Mapping(target = "author.id", source = "authorId")
    @Mapping(target = "executor.id", source = "executorId")
    Task dtoToObj(TaskRequest taskRequest);
}
