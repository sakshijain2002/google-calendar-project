package com.settings.service;

import com.settings.model.Language;
import com.settings.model.TimeFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="time-format")
public interface TimeFormatService {

    @GetMapping("/master/timeFormat/get/{id}")
    TimeFormat getById(@PathVariable Long id);
}
