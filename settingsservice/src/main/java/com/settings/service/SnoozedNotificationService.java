package com.settings.service;

import com.settings.model.Language;
import com.settings.model.SnoozedNotifications;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="snoozed-notification")
public interface SnoozedNotificationService {

    @GetMapping("/master/snoozedNotification/get/{id}")
    SnoozedNotifications getById(@PathVariable Long id);
}
