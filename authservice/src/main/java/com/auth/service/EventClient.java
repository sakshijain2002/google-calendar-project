package com.auth.service;


import com.auth.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "event-service", url = "http://localhost:8087")
    public interface EventClient {

    @GetMapping("/event/getByEmail/{email}")
    public List<Event> getByEmailId(@PathVariable String email);


}

