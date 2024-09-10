package com.settings.service;

import com.settings.model.CustomView;
import com.settings.model.EmailEventPrivacy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "event-privacy")
public interface EventPrivacyService {
    @GetMapping("/master/emailEvent/get/{id}")
    EmailEventPrivacy getById(@PathVariable Long id);
}
