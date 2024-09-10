package com.settings.service;

import com.settings.model.CustomView;
import com.settings.model.DefaultDuration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "default-duration")
public interface DefaultDurationService {

    @GetMapping("/master/defaultDuration/get/{id}")
    DefaultDuration getById(@PathVariable Long id);
}
