package com.task.taskservice.repository;

import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StarredTaskRepository extends JpaRepository<StarredTask,Long> {
    Optional<StarredTask> findByTask(Task task);


    Optional<StarredTask> findByTaskId(Long id);


    int deleteByTaskId(Long taskId);
}
