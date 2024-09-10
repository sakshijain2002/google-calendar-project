package com.task.taskservice.service;

import com.task.taskservice.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "http://localhost:8084",value ="user-client")
public interface UserServiceClient {
    @GetMapping("/auth/user/get/{id}")
    UserModel getUserById(@PathVariable Integer id);

}
