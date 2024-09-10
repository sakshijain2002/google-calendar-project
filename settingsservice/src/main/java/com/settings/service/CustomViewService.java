package com.settings.service;

import com.settings.model.AddInvitation;
import com.settings.model.CustomView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value="custom-view")
public interface CustomViewService {

    @GetMapping("/master/customView/get/{id}")
    CustomView getById(@PathVariable Long id);
}
