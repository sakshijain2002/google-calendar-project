package com.task.taskservice.service;

import com.task.taskservice.model.RepeatTypeModel;
import com.task.taskservice.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url=" http://localhost:8086",value = "repeat-type")
public interface RepeatTypeClient {
    @GetMapping("/master/repeatType/get/{id}")
    RepeatTypeModel getRepeatTypeById(@PathVariable Long id);
}
