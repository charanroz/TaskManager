package com.charan.taskmanager.repository;

import com.charan.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
    List<Task> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);
}