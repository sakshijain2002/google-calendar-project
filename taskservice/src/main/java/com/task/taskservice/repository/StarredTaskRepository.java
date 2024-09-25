package com.task.taskservice.repository;

import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StarredTaskRepository extends JpaRepository<StarredTask,Long> {
    Optional<StarredTask> findByTask(Task task);
}
