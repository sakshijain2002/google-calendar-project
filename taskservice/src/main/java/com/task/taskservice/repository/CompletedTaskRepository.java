package com.task.taskservice.repository;

import com.task.taskservice.entity.CompletedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedTaskRepository extends JpaRepository<CompletedTask,Long> {
}
