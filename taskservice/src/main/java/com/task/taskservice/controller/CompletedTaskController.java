package com.task.taskservice.controller;

import com.task.taskservice.entity.CompletedTask;
import com.task.taskservice.service.CompletedTaskService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/completedTask")
@CrossOrigin
public class CompletedTaskController {

    @Autowired
    private CompletedTaskService completedTaskService;

    @GetMapping
    public List<CompletedTask> getAll(){
        return completedTaskService.getAll();
    }

    @GetMapping("/get/{id}")
    public CompletedTask getById(Long id){
        return completedTaskService.getById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(Long id){
        completedTaskService.deleteById(id);
    }

}
