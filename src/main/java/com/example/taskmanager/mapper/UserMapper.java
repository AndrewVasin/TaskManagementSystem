package com.example.taskmanager.mapper;

import com.example.taskmanager.persist.dto.UserResponse;
import com.example.taskmanager.persist.entities.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toDto(User user);
}
