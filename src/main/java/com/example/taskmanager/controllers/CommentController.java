package com.example.taskmanager.controllers;

import com.example.taskmanager.exeptions.TaskNotFoundException;
import com.example.taskmanager.mapper.CommentMapper;
import com.example.taskmanager.persist.dto.CommentRequest;
import com.example.taskmanager.persist.dto.CommentResponse;
import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.Task;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.services.impl.CommentServiceImpl;
import com.example.taskmanager.services.impl.TaskServiceImpl;
import com.example.taskmanager.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/tasks")
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Comment", description = "Контроллер для взаимодействие с комментариями Comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UserUtils userUtils;

    /**
     * POST /comment/{id} : создание нового комментария к задаче
     *
     * @param commentRequest (optional)
     * @return Новый комментарий успешно создан (status code 201)
     *         или Неверные данные (status code 400)
     */
    @Operation(
            operationId = "createNewComment",
            summary = "Создание нового комментария",
            description = "Позволяет создать новый комментарий к задаче",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Новый комментарий успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Неверные данные")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/comment/{id}"
    )
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") Long id,
                                                 @Valid @RequestBody CommentRequest commentRequest) {
        try {
            Task task = taskService.findById(id);
            User user = userUtils.getCurrentUser();
            Comment comment = CommentMapper.INSTANCE.dtoToObj(commentRequest);
            comment.setAuthor(user);
            comment.setTask(task);
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * GET /comment/{id} : Получение всех комментариев по id задачи
     *
     * @param id id задачи
     * @return Список комментариев (status code 200)
     *         или Список комментариев пустой (status code 204)
     *         или Внутренняя ошибка (status code 500)
     */
    @Operation(
            operationId = "getAllCommentsByTaskId",
            summary = "Получение всех комментариев по id задачи",
            description = "Позволяет получить список всех комментариев по id задачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список комментариев", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))),
                    @ApiResponse(responseCode = "204", description = "Список комментариев пустой"),
                    @ApiResponse(responseCode = "500", description = "Ошибка")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/comment/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<Slice<CommentResponse>> getAllCommentsByTaskId(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit) {

        try {
            Slice<CommentResponse> commentResponses = commentService
                    .findByTaskId(id, PageRequest.of(page, limit))
                    .map(CommentMapper.INSTANCE::toDto);
            return new ResponseEntity<>(commentResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
