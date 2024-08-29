package com.example.taskmanager.services;

import com.example.taskmanager.persist.dto.CommentRequest;
import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface CommentService {
    void save(Comment comment);

    User findAuthorById(Long id);

    Slice<Comment> findByTaskId(Long id, Pageable pageable);
}
