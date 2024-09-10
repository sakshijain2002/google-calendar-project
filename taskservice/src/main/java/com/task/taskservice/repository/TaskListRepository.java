package com.task.taskservice.repository;

import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList,Long> {
    Optional<TaskList> findByName(String name);

}
