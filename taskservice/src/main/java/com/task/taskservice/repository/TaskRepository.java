package com.task.taskservice.repository;

import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStarredTaskTrue();

//    Optional<Task> findByUserId(Integer id);

        List<Task> findByUserId(Integer userId);


    @Query(value = "SELECT * FROM task WHERE task_list_id = :taskListId", nativeQuery = true)
    List<Task> findTasksByTaskListId(@Param("taskListId") Long taskListId);


    List<Task> findUserByEmail(String email);

    List<Task> findTaskByEmail(String email);
}
