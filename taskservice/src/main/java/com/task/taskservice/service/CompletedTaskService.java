package com.task.taskservice.service;

import com.task.taskservice.entity.CompletedTask;
import com.task.taskservice.repository.CompletedTaskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedTaskService {

    @Autowired
    private CompletedTaskRepository completedTaskRepository;

    public List<CompletedTask> getAll(){
        return completedTaskRepository.findAll();
    }

    public CompletedTask getById(Long id){
        return completedTaskRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }

    public void deleteById(Long id){
        completedTaskRepository.deleteById(id);
    }
}
