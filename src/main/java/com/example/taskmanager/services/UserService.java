package com.example.taskmanager.services;

import com.example.taskmanager.exeptions.EmailExistsException;
import com.example.taskmanager.persist.dto.UserCreateRequest;
import com.example.taskmanager.persist.dto.UserResponse;
import com.example.taskmanager.persist.entities.models.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findOneByUsername(String username);

    User findByEmail(String email);

    UserResponse createUser(UserCreateRequest userCreateRequest) throws EmailExistsException;

    User findById(Long id);

    boolean emailExists(String email);
}
