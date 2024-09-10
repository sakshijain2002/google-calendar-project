package com.settings.service;

import com.settings.model.CustomView;
import com.settings.model.GuestPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8086",value = "guest-permission")
public interface GuestPermissionService {
    @GetMapping("/master/guestPermission/get/{id}")
    GuestPermission getById(@PathVariable Long id);
}
