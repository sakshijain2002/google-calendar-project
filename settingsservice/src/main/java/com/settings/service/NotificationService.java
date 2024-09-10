package com.settings.service;

import com.settings.model.Language;
import com.settings.model.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="notification-client")
public interface NotificationService {

    @GetMapping("/master/notification/get/{id}")
    Notification getById(@PathVariable Long id);
}
