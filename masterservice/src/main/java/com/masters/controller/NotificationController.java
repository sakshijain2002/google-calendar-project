package com.masters.controller;

import com.masters.entity.Notification;
import com.masters.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/notification")
public class NotificationController {
    @Autowired

    private NotificationService notificationService;
    @GetMapping
    public List<Notification> getAll(){
        return notificationService.getAll();
    }

    @GetMapping("/get/{id}")
    public Notification getById(@PathVariable Long id){
        return notificationService.getById(id);
    }
    @PostMapping("/create")
    public Notification saveNotification(@RequestBody Notification notification){
        return notificationService.saveNotification(notification);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        notificationService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Notification updateRecordById(@PathVariable Long id,@RequestBody Notification notification){
        return notificationService.updateRecordById(id,notification);
    }
}
