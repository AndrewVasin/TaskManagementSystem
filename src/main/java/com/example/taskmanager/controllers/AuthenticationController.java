package com.example.taskmanager.controllers;

import com.example.taskmanager.persist.dto.AuthRequest;
import com.example.taskmanager.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Auth", description = "Контроллер для аутентификации пользователя")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * POST /login : аутентификация пользователя
     *
     * @param authRequest (optional)
     * @return Пользователь успешно аутентифицирован (status code 200)
     *         или Неверные данные (status code 400)
     *         или Пользователь не аутентифицирован (status code 401)
     */
    @Operation(
            operationId = "createToken",
            summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные"),
                    @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/login",
            produces = { "text/plain" },
            consumes = { "application/json" }
    )
    public ResponseEntity<String> createToken(@RequestBody AuthRequest authRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());
        log.info(authentication.toString());

        authenticationManager.authenticate(authentication);

        String token = jwtUtils.generateToken(authRequest.getUsername());
        log.info(token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
