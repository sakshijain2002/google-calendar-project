package com.task.taskservice.service;

import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import com.task.taskservice.model.UserModel;
import com.task.taskservice.repository.StarredTaskRepository;
import com.task.taskservice.repository.TaskListRepository;
import com.task.taskservice.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public StarredTask save(Long taskListId, StarredTask starredTask, Integer userId) {
        // Fetch the TaskList by ID
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new RuntimeException("TaskList not found"));

        // Fetch the UserModel by userId
        UserModel userModel = userServiceClient.getUserById(userId);
        if (userModel == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the Task exists
        Task task;
        if (starredTask.getTask() != null && starredTask.getTask().getId() != null) {
            // If the Task is already provided in the StarredTask, fetch it
            task = taskRepository.findById(starredTask.getTask().getId())
                    .orElseThrow(() -> new RuntimeException("Task not found"));
        } else {
            // If the Task does not exist, create a new Task
            task = new Task();
            task.setTitle(starredTask.getTask().getTitle()); // Get title from StarredTask
            task.setDescription(starredTask.getTask().getDescription()); // Optional, if needed
            task.setTaskList(taskList); // Associate the new Task with the TaskList
            task.setUserId(userId); // Set user ID if applicable
        }

        // Update the task's starred status and date
        task.setStarredTask(true); // Set the task as starred
        task.setStarredDate(LocalDateTime.now()); // Set the current time as the starred date

        // Save the Task entity (will create or update based on existence)
        task = taskRepository.save(task);

        // Associate the Task with StarredTask
        starredTask.setTask(task); // Link the Task to the StarredTask
        starredTask.setUserId(userId); // Set the user ID
        starredTask.setStarredDate(LocalDateTime.now()); // Set the current date/time
        starredTask.setTaskList(taskList);

        // Save and return the StarredTask
        return starredTaskRepository.save(starredTask);
    }

    public void deleteById(Long id,Long taskListId){

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

