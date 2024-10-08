package com.settings.service;


import com.settings.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:8084", value = "user-client")
public interface UserServiceClient {
    @GetMapping("/auth/user/get/{id}")
    UserModel getUserById(@PathVariable Integer id);

    @GetMapping("/auth/email")
    String extractEmailFromToken(@RequestHeader("Authorization") String token);

}
