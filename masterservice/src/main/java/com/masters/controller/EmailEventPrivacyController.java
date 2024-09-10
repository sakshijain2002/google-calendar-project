package com.masters.controller;

import com.masters.entity.EmailEventPrivacy;
import com.masters.service.EmailEventPrivacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/emailEvent")
public class EmailEventPrivacyController {
    @Autowired
    private EmailEventPrivacyService emailEventPrivacyService;

    @GetMapping
    public List<EmailEventPrivacy> getAll(){
        return emailEventPrivacyService.getAll();
    }

    @GetMapping("/get/{id}")
    public EmailEventPrivacy getById(@PathVariable Long id){
        return emailEventPrivacyService.getById(id);
    }

    @PostMapping("/create")
    public EmailEventPrivacy save(@RequestBody EmailEventPrivacy emailEventPrivacy){
        return emailEventPrivacyService.save(emailEventPrivacy);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        emailEventPrivacyService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public EmailEventPrivacy updateRecordById(@PathVariable Long id ,@RequestBody EmailEventPrivacy emailEventPrivacy){
        return emailEventPrivacyService.updateRecordById(id,emailEventPrivacy);
    }


}
