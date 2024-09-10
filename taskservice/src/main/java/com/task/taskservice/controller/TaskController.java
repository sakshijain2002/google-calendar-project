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
public class TaskController {
     @Autowired
     private TaskService taskService;
    @GetMapping
    public List<Task> getTask(){
        return taskService.getAllTask();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Integer userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
      @PostMapping("/{id}/add/{userId}")
      public String addTask(@PathVariable Long id,@RequestBody Task task,@PathVariable Integer userId){
          return taskService.addTask(task,id,userId);
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
      public void deleteTask(@PathVariable Integer id,@PathVariable Long taskListId){
           taskService.removeTaskFromTaskList(id,taskListId);
      }

      @DeleteMapping("/taskList/{id}")
      public String deleteTaskList(@PathVariable Long id){
        return taskService.deleteTaskList(id);
      }
      @PutMapping("/update/{id}")
     public Task updateTask(@PathVariable Integer id,@RequestBody Task task){
        return taskService.updateRecordById(id,task);
      }
    @PutMapping("/{taskId}/star")
    public String starTask(@PathVariable Integer taskId) {
        return taskService.markTaskAsStarred(taskId);
    }

    @PutMapping("/{taskId}/unstar")
    public String unstarTask(@PathVariable Integer taskId) {
        return taskService.markTaskAsUnstarred(taskId);
    }

    @GetMapping("/{id}/tasklists")
    public TaskList getAllTaskLists(@PathVariable Long id) {
        return taskService.getAllTaskLists(id);
    }

}
