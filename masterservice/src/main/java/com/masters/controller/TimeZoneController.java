package com.masters.controller;

import com.masters.entity.TimeZone;
import com.masters.service.TimeZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/timeZone")
public class TimeZoneController {
    @Autowired
    private TimeZoneService timeZoneService;
    @GetMapping()
    public List<TimeZone> getAllTimeZone(){
        return timeZoneService.getAllTimeZones();
    }

    @GetMapping("/get/{id}")
    public TimeZone getById(@PathVariable Long id){
        return timeZoneService.getById(id);
    }
    @PostMapping("/create")
    public String createTimeZone(@RequestBody TimeZone timeZone){
        return timeZoneService.createTimeZone(timeZone);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        timeZoneService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public TimeZone updateRecordById(@PathVariable Long id,@RequestBody TimeZone timeZone){
        return timeZoneService.updateRecordById(id,timeZone);
    }

}
