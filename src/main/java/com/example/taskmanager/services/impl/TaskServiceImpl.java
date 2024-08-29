package com.example.taskmanager.services.impl;

import com.example.taskmanager.exeptions.TaskNotFoundException;
import com.example.taskmanager.exeptions.UserNotFoundException;
import com.example.taskmanager.persist.entities.models.Task;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.persist.repositories.TaskRepository;
import com.example.taskmanager.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public Task save(Task taskRequest) throws UserNotFoundException {

        User author = userService.findById(taskRequest.getAuthor().getId());
        User executor = userService.findById(taskRequest.getExecutor().getId());

        Task task = new Task();
        task.setAuthor(author);
        task.setExecutor(executor);
        task.setHeader(taskRequest.getHeader());
        task.setDescription(taskRequest.getDescription());
        task.setTaskStatus(taskRequest.getTaskStatus());
        task.setTaskPriority(taskRequest.getTaskPriority());
        taskRequest = taskRepository.save(task);
        return taskRequest;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Slice<Task> findAllSlice(Pageable pageable) {
        return taskRepository.findAllSlice(pageable);
    }

    @Override
    public Task findById(Long id) throws TaskNotFoundException {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Slice<Task> findByAuthorId(Long id, Pageable pageable) {
        return taskRepository.findByAuthorId(id, pageable);
    }

    @Override
    public Slice<Task> findByExecutorId(Long id, Pageable pageable) {
        return taskRepository.findByExecutorId(id, pageable);
    }
}
