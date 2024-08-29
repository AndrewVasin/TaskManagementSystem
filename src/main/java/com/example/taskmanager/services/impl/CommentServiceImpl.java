package com.example.taskmanager.services.impl;

import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.User;
import com.example.taskmanager.persist.repositories.CommentRepository;
import com.example.taskmanager.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public void save(Comment commentRequest) {

        User author = userService.findById(commentRequest.getAuthor().getId());

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setContent(commentRequest.getContent());
        comment.setTask(commentRequest.getTask());
        commentRepository.save(comment);
    }

    @Override
    public User findAuthorById(Long id) {
        return userService.findById(id);
    }

    @Override
    public Slice<Comment> findByTaskId(Long id, Pageable pageable) {
        return commentRepository.findByTaskId(id, pageable);
    }
}
