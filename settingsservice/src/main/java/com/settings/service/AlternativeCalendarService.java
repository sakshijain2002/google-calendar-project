package com.settings.service;

import com.settings.model.AddInvitation;
import com.settings.model.AlternativeCalendar;
import com.settings.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="alternative-calendar")
public interface AlternativeCalendarService {

    @GetMapping("/master/alternativeCalendar/get/{id}")
    AlternativeCalendar getById(@PathVariable Long id);
}
