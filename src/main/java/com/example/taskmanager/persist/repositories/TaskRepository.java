package com.example.taskmanager.persist.repositories;

import com.example.taskmanager.persist.entities.models.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Slice<Task> findByAuthorId(Long id, Pageable pageable);

    Slice<Task> findByExecutorId(Long id, Pageable pageable);

    @Query("SELECT p FROM Task p")
    Slice<Task> findAllSlice(Pageable pageable);
}
