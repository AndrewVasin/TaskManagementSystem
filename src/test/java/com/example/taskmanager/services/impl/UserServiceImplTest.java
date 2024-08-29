package com.example.taskmanager.services.impl;

import com.example.taskmanager.exeptions.EmailExistsException;
import com.example.taskmanager.persist.dto.UserCreateRequest;
import com.example.taskmanager.persist.dto.UserResponse;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.services.UserService;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void givenUser_whenServiceFindByEmail_thenCorrect() throws EmailExistsException {
        final UserResponse user = registerUser();
        final User user2 = userService.findByEmail(user.getEmail());
        assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    void givenUser_whenServiceFindOneByUsername_thenCorrect() {
        final UserResponse user = registerUser();
        final User user2 = userService.findOneByUsername(user.getEmail()).get();
        assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    void givenUser_whenServiceFindOneByUsername_thenInCorrect() {
        final Optional<User> user = userService.findOneByUsername("test");
        assertThat(user).isEmpty();
    }

    private UserResponse registerUser() {
        final String email = UUID.randomUUID().toString();
        final String pass = "test";
        final UserCreateRequest userDto = UserCreateRequest.builder()
                .email(email)
                .password(pass)
                .build();
        final UserResponse user = userService.createUser(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(email, user.getEmail());
        return user;
    }
}