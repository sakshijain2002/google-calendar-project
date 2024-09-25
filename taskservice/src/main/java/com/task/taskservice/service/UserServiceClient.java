package com.task.taskservice.service;

import com.task.taskservice.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url= "http://localhost:8084",value ="user-client")
public interface UserServiceClient {
    @GetMapping("/auth/user/get/{id}")
    UserModel getUserById(@PathVariable Integer id);
    @GetMapping("/auth/user/getEmail/{email}")
    UserModel getUserByEmail(@PathVariable String email);

    @GetMapping("/auth/get/user")
    UserModel getUserProfile(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/validate")
    boolean isValidToken(@RequestHeader("Authorization") String token);

    @GetMapping("/auth/email")
    String extractEmailFromToken(@RequestHeader("Authorization") String token);
}


