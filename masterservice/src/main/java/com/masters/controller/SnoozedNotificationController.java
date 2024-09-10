package com.masters.controller;

import com.masters.entity.SnoozedNotifications;
import com.masters.service.SnoozedNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/snoozedNotification")
public class SnoozedNotificationController {
    @Autowired
     private SnoozedNotificationService snoozedNotificationService;

    @GetMapping
    public List<SnoozedNotifications> getAll(){
        return snoozedNotificationService.getAll();
    }

    @GetMapping("/get/{id}")
    public SnoozedNotifications getById(@PathVariable Long id){
        return snoozedNotificationService.getById(id);
    }

    @PostMapping("/create")
    public SnoozedNotifications create(@RequestBody SnoozedNotifications snoozedNotifications){
        return snoozedNotificationService.saveSnoozedNotification(snoozedNotifications);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        snoozedNotificationService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public SnoozedNotifications updateRecordById(@PathVariable Long id, @RequestBody SnoozedNotifications snoozedNotifications){
        return snoozedNotificationService.updateRecordById(id,snoozedNotifications);
    }


}
