package com.task.taskservice.service;

import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.entity.TaskList;
import com.task.taskservice.model.UserModel;
import com.task.taskservice.repository.StarredTaskRepository;
import com.task.taskservice.repository.TaskListRepository;
import com.task.taskservice.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StarredTaskService {
    @Autowired
    private StarredTaskRepository starredTaskRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<StarredTask> getAll(){
        return starredTaskRepository.findAll();
    }
    public StarredTask getById(Long id){
        return starredTaskRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }
    public StarredTask save(Long id,StarredTask starredTask,Integer userId){
        TaskList taskList = taskListRepository.findById(id)
                .orElseThrow(()->new RuntimeException("data not found"));
        UserModel userModel = userServiceClient.getUserById(userId);
        if(userModel == null){
            throw new RuntimeException("data not found");
        }
        starredTask.setTaskList(taskList);
        starredTask.setUserId(userId);
        return starredTaskRepository.save(starredTask);
    }

    public void deleteById(Long id){
        starredTaskRepository.deleteById(id);
    }
    public StarredTask updateRecordById(Long id, StarredTask record){
        Optional<StarredTask> starredTaskRecord = starredTaskRepository.findById(id);
        if(starredTaskRecord.isPresent()){
            StarredTask starredTask =  starredTaskRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,starredTask);

            starredTaskRepository.save(starredTask);

        }
        return starredTaskRepository.save(record);
    }

    }

