package com.settings.service;

import com.settings.model.Language;
import com.settings.model.StartWeek;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="start-week")
public interface StartWeekService {

    @GetMapping("/master/startWeek/get/{id}")
    StartWeek getById(@PathVariable Long id);
}
