package com.masters.controller;


import com.masters.entity.NotificationTime;
import com.masters.service.NotificationTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/notificationTime")
public class NotificationTimeController {

    @Autowired
    private NotificationTimeService notificationTimeService;

    @GetMapping
    public List<NotificationTime> getAll(){
        return notificationTimeService.getAll();
    }
    @GetMapping("/get/{id}")
    public NotificationTime getById(Long id){
        return notificationTimeService.getById(id);
    }
    @PostMapping("/create")
    public NotificationTime save(NotificationTime notificationTime){
        return notificationTimeService.save(notificationTime);
    }
    @PutMapping("/update/{id}")
    public NotificationTime updateRecordById(Long id,NotificationTime notificationTime){
        return notificationTimeService.updateRecordById(id,notificationTime);
    }
    @DeleteMapping("/{id}")
    public void deleteById(Long id){
        notificationTimeService.deleteById(id);
    }


}
