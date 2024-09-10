package com.settings.service;

import com.settings.model.TimeZoneModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8086",value = "time-zone")
public interface TimeZoneService {

    @GetMapping("/master/timeZone/get/{id}")
    TimeZoneModel getTimeZoneById(@PathVariable Long id);
}
