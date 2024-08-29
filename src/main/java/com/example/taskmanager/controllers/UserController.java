package com.example.taskmanager.controllers;

import com.example.taskmanager.exeptions.EmailExistsException;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.persist.dto.UserCreateRequest;
import com.example.taskmanager.persist.dto.UserResponse;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.persist.repositories.UserRepository;
import com.example.taskmanager.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "User", description = "Контроллер для взаимодействие с пользователями User")
@Validated
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserServiceImpl userService;

    /**
     * POST /registration : регистрация нового пользователя
     *
     * @param userCreateRequest (optional)
     * @return Новый аккаунт успешно создан (status code 201)
     *         или Неверные данные (status code 400)
     *         или Аккаунт с таким email уже существует (status code 409)
     */
    @Operation(
            operationId = "createNewUser",
            summary = "Регистрация нового пользователя",
            description = "Позволяет создать нового пользователя",
            responses = {
                @ApiResponse(responseCode = "201", description = "Новый аккаунт успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                @ApiResponse(responseCode = "409", description = "Аккаунт с таким email уже существует", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/registration",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<UserResponse> createNewUser(@Parameter(name = "UserCreateRequest") @Valid @RequestBody UserCreateRequest userCreateRequest) {

        try {
            UserResponse userResponse = userService.createUser(userCreateRequest);
            log.info("Новый аккаунт " + userResponse.getEmail() + " создан.");
            return ResponseEntity.created(URI.create("/users/" + userResponse.getId()))
                    .body(userResponse);
        } catch (EmailExistsException e) {
            log.error("Аккаунт с таким email: " + userCreateRequest.getEmail() + " уже существует.");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserResponse(null, userCreateRequest.getEmail()));
        }
    }


    /**
     * GET /users : Получение списка пользователей
     *
     * @return Список пользователей users (status code 200)
     *         или Список пользователей пустой (status code 204)
     *         или Внутренняя ошибка (status code 500)
     */
    @Operation(
            operationId = "getAllUsers",
            summary = "Получение списка пользователей",
            description = "Позволяет получить полный список пользователей",
            responses = {
                @ApiResponse(responseCode = "200", description = "Список пользователей", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "204", description = "Список пользователей пустой"),
                @ApiResponse(responseCode = "500", description = "Ошибка")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users",
            produces = { "application/json" }
    )
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        try {
            List<UserResponse> userResponseList = userRepository
                    .findAll().stream().map(UserMapper.INSTANCE::toDto)
                    .toList();
            if (userResponseList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userResponseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
