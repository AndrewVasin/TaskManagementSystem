package com.example.taskmanager.controllers;

import com.example.taskmanager.exeptions.TaskNotFoundException;
import com.example.taskmanager.exeptions.UserNotFoundException;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.persist.dto.TaskRequest;
import com.example.taskmanager.persist.dto.TaskResponse;
import com.example.taskmanager.persist.entities.models.Task;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.persist.repositories.TaskRepository;
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
@Tag(name = "Task", description = "Контроллер для взаимодействие с задачами Task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UserUtils userUtils;


    /**
     * POST /create : создание новой задачи
     *
     * @param taskRequest (optional)
     * @return Новая задача успешно создана (status code 201)
     *         или Неверные данные (status code 400)
     */
    @Operation(
            operationId = "createNewTask",
            summary = "Создание новой задачи",
            description = "Позволяет создать новую задачу",
            responses = {
                @ApiResponse(responseCode = "201", description = "Новая задача успешно создана"),
                @ApiResponse(responseCode = "400", description = "Неверные данные")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create"
    )
    public ResponseEntity<HttpStatus> createNewTask(@Valid @RequestBody(required = false) TaskRequest taskRequest) {

        Task task = TaskMapper.INSTANCE.dtoToObj(taskRequest);
        task.setAuthor(userUtils.getCurrentUser());

        try {
            taskService.save(task);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Новая задача " + task.getHeader() + " создана.");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * GET /tasks : Получение списка всех задач
     *
     * @return Список задач tasks (status code 200)
     *         или Список задач пустой (status code 204)
     *         или Внутренняя ошибка (status code 500)
     */
    @Operation(
            operationId = "getAllTasks",
            summary = "Получение списка задач",
            description = "Позволяет получить список всех задач",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список задач", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "204", description = "Список пользователей пустой"),
                    @ApiResponse(responseCode = "500", description = "Ошибка")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/tasks",
            produces = { "application/json" }
    )
    public ResponseEntity<Slice<Task>> getAllTasks(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        Slice<Task> tasks = taskRepository.findAllSlice(PageRequest.of(page, limit));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    /**
     * GET /{id} : Получение задачи по id
     *
     * @param id id задачи
     * @return Задача найдена (status code 200)
     *         или Неверные данные (status code 400)
     */
    @Operation(
            operationId = "getTaskById",
            summary = "Получение задачи по id",
            description = "Позволяет получить задачу по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача {id}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные"),
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        try {
            Task task = taskService.findById(id);
            TaskResponse taskResponse = TaskMapper.INSTANCE.toDto(task);
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * GET /author/{id} : Получение всех задач по id автора
     *
     * @param id id автора
     * @return Список задач (status code 200)
     *         или Список задач пустой (status code 204)
     *         или Внутренняя ошибка (status code 500)
     */
    @Operation(
            operationId = "getAllTasksByAuthorId",
            summary = "Получение всех задач по id автора",
            description = "Позволяет получить список всех задач по id автора",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список задач", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "204", description = "Список задач пустой"),
                    @ApiResponse(responseCode = "500", description = "Ошибка")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/author/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<Slice<TaskResponse>> getAllTasksByAuthorId(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit) {

        try {
            Slice<TaskResponse> tasks = taskService
                    .findByAuthorId(id, PageRequest.of(page, limit))
                    .map(TaskMapper.INSTANCE::toDto);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * GET /executor/{id} : Получение всех задач по id исполнителя
     *
     * @param id id исполнителя
     * @return Список задач (status code 200)
     *         или Список задач пустой (status code 204)
     *         или Внутренняя ошибка (status code 500)
     */
    @Operation(
            operationId = "getAllTasksByExecutorId",
            summary = "Получение всех задач по id исполнителя",
            description = "Позволяет получить список всех задач по id исполнителя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список задач", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
                    @ApiResponse(responseCode = "204", description = "Список задач пустой"),
                    @ApiResponse(responseCode = "500", description = "Ошибка")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/executor/{id}",
            produces = { "application/json" }
    )
    public ResponseEntity<Slice<TaskResponse>> getAllTasksByAExecutorId(
            @PathVariable("id") Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit) {

        try {
            Slice<TaskResponse> tasks = taskService
                    .findByExecutorId(id, PageRequest.of(page, limit))
                    .map(TaskMapper.INSTANCE::toDto);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * PATCH /{id} : Изменение задачи по id
     *
     * @param id id задачи
     * @param taskRequest новые значения
     * @return Задача успешно обновлена (status code 200)
     *         или Неверные данные (status code 400)
     *         или Доступ запрещен (status code 403)
     */
    @Operation(
            operationId = "changeTask",
            summary = "Изменение задачи по id",
            description = "Позволяет произвести изменение задачи по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача успешно изменена"),
                    @ApiResponse(responseCode = "400", description = "Неверные данные"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/{id}"
    )
    public ResponseEntity<HttpStatus> changeTask(@PathVariable("id") Long id,
                                                 @Valid @RequestBody TaskRequest taskRequest) {
        try {
            Task task = taskService.findById(id);
            User user = userUtils.getCurrentUser();
            Task changedTask = TaskMapper.INSTANCE.dtoToObj(taskRequest);
            if (task.getAuthor().getEmail().equals(user.getEmail())) {
                taskService.save(changedTask);
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                if (task.getExecutor().getEmail().equals(user.getEmail())) {
                    task.setTaskStatus(changedTask.getTaskStatus());
                    taskService.save(task);
                    return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * DELETE /{id} : Удаление задачи по id
     *
     * @param id id задачи
     * @return Задача успешно удалена (status code 204)
     *         или Неверные данные (status code 400)
     *         или Доступ запрещен (status code 403)
     */
    @Operation(
            operationId = "deleteTask",
            summary = "Удаление задачи по id",
            description = "Позволяет удалить задачу по id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Задача удалена"),
                    @ApiResponse(responseCode = "400", description = "Неверные данные"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            }
    )
    @SecurityRequirement(name = "JWT")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}"
    )
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") Long id) {
        try {
            taskService.findById(id);
            User user = userUtils.getCurrentUser();
            if (taskService.findById(id).getAuthor().getEmail().equals(user.getEmail())) {
                taskService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
