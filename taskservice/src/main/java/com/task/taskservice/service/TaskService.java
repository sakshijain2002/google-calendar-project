package com.task.taskservice.service;

import com.task.taskservice.entity.CompletedTask;
import com.task.taskservice.entity.StarredTask;
import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import com.task.taskservice.model.UserModel;
import com.task.taskservice.repository.CompletedTaskRepository;
import com.task.taskservice.repository.StarredTaskRepository;
import com.task.taskservice.repository.TaskListRepository;
import com.task.taskservice.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StarredTaskRepository starredTaskRepository;
    @Autowired
    private CompletedTaskRepository completedTaskRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public Task addTask(Task task,Long id,Integer userId){
        TaskList taskList = taskListRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Task List not found"));
        UserModel userDto = userServiceClient.getUserById(userId);


        if (userDto == null) {
            throw new RuntimeException("User not found");
        }

        // Set TaskList and user-related data to Task
        task.setTaskList(taskList);
        task.setUserId(userId);
        // Or any other user-related field
        return taskRepository.save(task);

    }

    public List<Task> getTasksByEmailId(String email){
      return   taskRepository.findTaskByEmail(email);
    }

    @Transactional
    public Task addTask(Task task, String token) {
        String userEmail = userServiceClient.extractEmailFromToken(token);
        task.setEmail(userEmail);
        return taskRepository.save(task);
    }


    public List<Task> addAllTasks(List<Task> tasks, String token) {

        // Fetch user details using the email
//        UserModel userDto = userServiceClient.getUserByEmail(email);
        String userEmail = userServiceClient.extractEmailFromToken(token);
        for (Task task : tasks) {
            task.setEmail(userEmail); // Assuming your Task class has a method setEmail()
        }
        return taskRepository.saveAll(tasks);

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
    public List<Task> getTasksByEmail(String email) {
        // Optionally, you could use the Feign Client to validate the user exists
        UserModel userDto = userServiceClient.getUserByEmail(email);
        if (userDto == null) {
            throw new RuntimeException("User not found");
        }

        // Fetch and return tasks for the given userId
        return taskRepository.findUserByEmail(email);
    }
    public TaskList createTaskList(String name) {
        TaskList taskList = new TaskList();
        taskList.setName(name);
        return taskListRepository.save(taskList);
    }
    @Transactional
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
    @Transactional
    public void removeTaskFromTaskList(Long taskId, Long taskListId) {
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
    public List<Task> getAllTask(String token){
        String email;
        email = userServiceClient.extractEmailFromToken(token);
         return taskRepository.findTaskByEmail(email);
//        return taskRepository.findAll();
    }
    public List<Task> getTasksByTaskListName(Long id) {
        return taskRepository.findTasksByTaskListId(id);
    }

    public Task updateRecordById(Long id, Task record) {

        Optional<Task> taskRecord = taskRepository.findById(id);
        if(taskRecord.isPresent()){
          Task taskEntity =  taskRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,taskEntity);

            taskRepository.save(taskEntity);

        }
return taskRepository.save(record);
    }
    public List<Task> getStarredTasks() {
        return taskRepository.findByStarredTaskTrue();
    }
    public String markTaskAsStarred(Long taskId) {
        // Fetch the task from the repository
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task with ID " + taskId + " not found"));

        // Check if the task is already starred
        if (!task.getStarredTask()) {
            // Mark the task as starred
            task.setStarredTask(true);
            task.setStarredDate(LocalDateTime.now());
            taskRepository.save(task); // Save the updated task

            // Create and save the task in the StarredTask entity
            StarredTask starredTask = new StarredTask();
            starredTask.setTask(task);
            starredTask.setStarredDate(LocalDateTime.now()); // Save the current timestamp
            starredTaskRepository.save(starredTask); // Save the starred task details
        } else {
            return "Task is already starred";
        }

        return "Task marked as starred";
    }

    @Transactional
    public boolean markTaskAsUnstarred(Long taskId) {
        // Fetch the task by taskId
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Check if the task is currently starred
        if (task.getStarredTask()) {
            // Mark the task as unstarred
            task.setStarredTask(false);
            task.setStarredDate(null);
            taskRepository.save(task); // Save the updated task

            // Find the corresponding StarredTask entry
            StarredTask starredTask = starredTaskRepository.findByTask(task)
                    .orElseThrow(() -> new RuntimeException("StarredTask entry not found for this task"));

            // Remove the StarredTask from the TaskList if it's associated with one
            TaskList taskList = starredTask.getTaskList();
            if (taskList != null) {
                taskList.getStarredTasks().remove(starredTask);
                taskListRepository.save(taskList); // Save the updated TaskList
            }

            // Now delete the StarredTask entry
            starredTaskRepository.delete(starredTask);

            return true; // Successfully unstarred and removed from StarredTask
        } else {
            logger.info("Task with ID {} is not starred", taskId);
            return false; // Task is not starred
        }
    }


    @Transactional
    public boolean markTaskAsComplete(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);

        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setCompleted(true); // Mark the task as completed

            // Create a CompletedTask object
            CompletedTask completedTask = new CompletedTask();
            completedTask.setId(task.getId());
            completedTask.setTitle(task.getTitle());
            completedTask.setDescription(task.getDescription());
            completedTask.setAllDay(task.getAllDay());
            completedTask.setUserId(task.getUserId());
            completedTask.setRepeatTypeId(task.getRepeatTypeId());
            completedTask.setCompleted(true);

            try {
                // If the task is starred, remove its entry from StarredTask
                if (task.getStarredTask()) {
                    // Check if the task has associated starred tasks
                    List<StarredTask> starredTasks = task.getStarredTasks();
                    for (StarredTask starredTask : starredTasks) {
                        starredTaskRepository.delete(starredTask);
                    }
                    task.getStarredTasks().clear(); // Clear the starred tasks from the task
                }

                // Step 1: Save the completed task
                completedTaskRepository.save(completedTask); // Persist completed task

                // Step 2: Remove the task from the TaskList if it exists
                TaskList taskList = task.getTaskList();
                if (taskList != null) {
                    taskList.getTasks().remove(task); // Remove Task from TaskList
                    taskListRepository.save(taskList); // Save the updated TaskList
                }

                // Step 3: Now delete the Task safely
                taskRepository.delete(task); // Delete the task

                logger.info("Task deleted: {}", taskRepository.findById(id).isEmpty());
                return true;

            } catch (Exception e) {
                logger.error("Failed to mark task as complete: {}", e.getMessage());
                return false; // Handle failure gracefully
            }
        }

        return false; // Task not found
    }




    public List<Task> getSortedRecords(String sortBy) {
        List<Task> tasksEntityList = taskRepository.findAll();
        if (Objects.nonNull(tasksEntityList) && tasksEntityList.size() > 0) {
            Comparator<Task> comparator = findSuitableComparator(sortBy);
            List<Task> tasksModelList = tasksEntityList.stream()
                    .sorted(comparator)
                    .map(taskEntity -> {
                        Task task = modelMapper.map(taskEntity, Task.class);
                        return task;
                    })
                    .collect(Collectors.toList());
            return tasksModelList;

        } else return Collections.emptyList();
    }
    private static Comparator<Task> findSuitableComparator(String sortBy) {
        Comparator<Task> comparator;
        switch (sortBy) {
            case "day": {
                comparator = Comparator.comparing(Task::getDay);
                break;
            }
            case "starredRecently": {
                comparator = Comparator.comparing(Task::getStarredDate, Comparator.nullsLast(Comparator.reverseOrder()));
                break;
            }
            default: {
                comparator = Comparator.comparing(Task::getId);
            }
        }
        return comparator;
    }


}
