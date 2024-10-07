package com.task.taskservice.controller;

import com.task.taskservice.entity.Task;
import com.task.taskservice.entity.TaskList;
import com.task.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getTask(@RequestHeader("Authorization") String token) {
        return taskService.getAllTask(token);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Integer userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/getAll/{email}")
    public List<Task> getTasksEmail(@PathVariable String email) {
       return taskService.getTasksByEmail(email);

    }

    @PostMapping("/{id}/add/{userId}")
    public Task addTask(@PathVariable Long id, @RequestBody Task task, @PathVariable Integer userId) {
        return taskService.addTask(task, id, userId);
    }

    @PutMapping("/addAllTasks")
    public List<Task> addTasks(@RequestBody List<Task> tasks,  @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return taskService.addAllTasks(tasks,token);
}
//    @PutMapping("/addTask/{email}")
//    public Task addTask(@RequestBody Task task,@PathVariable String email){
//        return taskService.addTask(task,email);
//    }
    @PostMapping("/addTask")
    public Task addTask(@RequestBody Task task, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return taskService.addTask(task,token);
    }
      @PostMapping("/taskList")
      public String createTaskList(@RequestParam String name){
        taskService.createTaskList(name);
                return "List created successfully";
      }
    @GetMapping("/by-tasklist-name")
    public List<Task> getTasksByTaskListName(@RequestParam Long id) {
        return taskService.getTasksByTaskListName(id);
    }

      @DeleteMapping("/deleteTask/{id}/{taskListId}")
      public void deleteTask(@PathVariable Long id,@PathVariable Long taskListId){
           taskService.removeTaskFromTaskList(id,taskListId);
      }

      @DeleteMapping("/taskList/{id}")
      public String deleteTaskList(@PathVariable Long id){
        return taskService.deleteTaskList(id);
      }
      @PutMapping("/update/{id}")
     public Task updateTask(@PathVariable Long id,@RequestBody Task task){
        return taskService.updateRecordById(id,task);
      }
    @PutMapping("/complete/{id}")
    public boolean markTaskComplete(@PathVariable Long id) {
        return taskService.markTaskAsComplete(id);
    }

    @PutMapping("/{taskId}/star")
    public String starTask(@PathVariable Long taskId) {
        return taskService.markTaskAsStarred(taskId);
    }

    @PutMapping("/{taskId}/unstar")
    public String unstarTask(@PathVariable Long taskId) {
        return taskService.markTaskAsUnstarred(taskId);
    }

    @GetMapping("/{id}/tasklists")
    public TaskList getAllTaskLists(@PathVariable Long id) {
        return taskService.getAllTaskLists(id);
    }
    @GetMapping("/sorted")
    public List<Task> getSortedTasks(@RequestParam String sortBy) {
        return taskService.getSortedRecords(sortBy);
    }

}
