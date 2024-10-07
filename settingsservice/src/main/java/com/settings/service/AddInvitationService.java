package com.settings.service;

import com.settings.model.AddInvitation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8086", value = "add-invitation")
public interface AddInvitationService {

    @GetMapping("/master/addInvitaion/get/{id}")
    AddInvitation getById(@PathVariable Long id);
}
