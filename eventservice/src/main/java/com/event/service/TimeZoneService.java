package com.event.service;


import com.event.model.TimeZoneModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "timeZone")
public interface TimeZoneService {

    @GetMapping("/settings/timeZone")
    TimeZoneModel getById(@PathVariable Long id);
}
