package com.auth.service;

import com.auth.model.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "task-service", url = "http://localhost:8085")
public interface TaskClient {

    @GetMapping("/task/{email}")
    List<Task> getTaskByEmail(@PathVariable String email);
}