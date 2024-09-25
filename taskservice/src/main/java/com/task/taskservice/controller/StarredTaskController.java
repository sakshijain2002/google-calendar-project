package com.task.taskservice.controller;

import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.service.StarredTaskService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/starredTask")
@CrossOrigin
public class StarredTaskController {
    @Autowired
    private StarredTaskService starredTaskService;
    @GetMapping
    public List<StarredTask> getAll(){
        return starredTaskService.getAll();
    }
    @GetMapping("/get/{id}")
    public StarredTask getById(Long id){
        return starredTaskService.getById(id);
    }
    @PostMapping("{id}/create/{userId}")
    public StarredTask saveStarredTask(@PathVariable Long id, @RequestBody StarredTask starredTask, @PathVariable Integer userId){
        return starredTaskService.save(id,starredTask,userId);
    }
    @PutMapping("/update/{id}")
    public StarredTask updateRecordById(@PathVariable Long id,@RequestBody StarredTask starredTask){
        return starredTaskService.updateRecordById(id,starredTask);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        starredTaskService.deleteById(id);
    }
}
