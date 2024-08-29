package com.example.taskmanager.services;

import com.example.taskmanager.exeptions.TaskNotFoundException;
import com.example.taskmanager.exeptions.UserNotFoundException;
import com.example.taskmanager.persist.dto.TaskRequest;
import com.example.taskmanager.persist.dto.TaskResponse;
import com.example.taskmanager.persist.entities.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface TaskService {

    Task save(Task task) throws UserNotFoundException;

    void delete(Long id);

    Slice<Task> findAllSlice(Pageable pageable);

    Task findById(Long id) throws TaskNotFoundException;

    Slice<Task> findByAuthorId(Long id, Pageable pageable);

    Slice<Task> findByExecutorId(Long id, Pageable pageable);
}
