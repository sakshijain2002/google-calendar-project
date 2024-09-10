package com.task.taskservice.service;

import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import com.task.taskservice.model.UserModel;
import com.task.taskservice.repository.TaskListRepository;
import com.task.taskservice.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private UserServiceClient userServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public String addTask(Task task,Long id,Integer userId){
        TaskList taskList = taskListRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Task List not found"));
        UserModel userDto = userServiceClient.getUserById(userId);

        if (userDto == null) {
            throw new RuntimeException("User not found");
        }

        // Set TaskList and user-related data to Task
        task.setTaskList(taskList);
        task.setUserId(userId); // Or any other user-related field
        taskRepository.save(task);
        return "task added successfully";
    }

    public TaskList getAllTaskLists(Long id) {
        return taskListRepository.findById(id).orElseThrow(()-> new RuntimeException("Task List not found"));
    }
    public List<Task> getTasksByUserId(Integer userId) {
        // Optionally, you could use the Feign Client to validate the user exists
        UserModel userDto = userServiceClient.getUserById(userId);
        if (userDto == null) {
            throw new RuntimeException("User not found");
        }

        // Fetch and return tasks for the given userId
        return taskRepository.findByUserId(userId);
    }
    public TaskList createTaskList(String name) {
        TaskList taskList = new TaskList();
        taskList.setName(name);
        return taskListRepository.save(taskList);
    }
    @Transactional
    public void deleteTask(Integer id){
        taskRepository.deleteById(id);
    }
    @Transactional
    public void removeTaskFromTaskList(Integer taskId, Long taskListId) {
        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new EntityNotFoundException("TaskList not found"));

        Task taskToRemove = taskList.getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        taskList.getTasks().remove(taskToRemove);
        taskRepository.delete(taskToRemove);
    }
    public String deleteTaskList(Long id){
        taskListRepository.deleteById(id);
        return "list deleted";
    }
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }
    public List<Task> getTasksByTaskListName(Long id) {
        return taskRepository.findTasksByTaskListId(id);
    }

    public Task updateRecordById(Integer id, Task record) {

        Optional<Task> taskRecord = taskRepository.findById(id);
        if(taskRecord.isPresent()){
          Task taskEntity =  taskRecord.get();
            taskRepository.save(taskEntity);

        }
return taskRepository.save(record);
    }
    public List<Task> getStarredTasks() {
        return taskRepository.findByStarredTaskTrue();
    }
    public String markTaskAsStarred(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->new RuntimeException("TASK NOT FOUND"));
        if( task.getStarredTask()==false){
            task.setStarredTask(true);
        }
        taskRepository.save(task);
//
        return "starred task";
    }
    public String markTaskAsUnstarred(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if(task.getStarredTask()==true ) {
            task.setStarredTask(false);
        }
        taskRepository.save(task);
        return "marked unstarred";

    }

}
