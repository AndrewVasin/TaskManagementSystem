package com.example.taskmanager.persist.repositories;

import com.example.taskmanager.persist.entities.models.Comment;
import com.example.taskmanager.persist.entities.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findAuthorById(Long id);

    Slice<Comment> findByTaskId(Long id, Pageable pageable);
}
