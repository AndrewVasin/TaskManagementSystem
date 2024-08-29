package com.example.taskmanager.services.impl;

import com.example.taskmanager.exeptions.EmailExistsException;
import com.example.taskmanager.exeptions.UserNotFoundException;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.persist.dto.UserCreateRequest;
import com.example.taskmanager.persist.dto.UserResponse;
import com.example.taskmanager.persist.entities.enums.UserRole;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.persist.repositories.UserRepository;
import com.example.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findOneByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) throws EmailExistsException {
        if (emailExists(userCreateRequest.getEmail())) {
            throw new EmailExistsException(userCreateRequest.getEmail());
        }
        User user = new User();
        user.setEmail(userCreateRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole(UserRole.USER);
        user.setActive(true);

        user = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
