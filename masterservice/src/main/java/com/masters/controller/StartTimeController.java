package com.masters.controller;


import com.masters.entity.StartTime;
import com.masters.service.StartTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/startTime")
public class StartTimeController {
    @Autowired
    private StartTimeService startTimeService;

    @GetMapping
    public List<StartTime> getAll(){
        return startTimeService.getAll();
    }
    @GetMapping("/{id}")
    public StartTime getById(@PathVariable Long id){
        return startTimeService.getById(id);
    }
    @PostMapping("/create")
    public StartTime save(@RequestBody StartTime startTime){
        return startTimeService.save(startTime);
    }
    @PutMapping("/update/{id}")
    public StartTime updateRecordById(@PathVariable Long id,@RequestBody StartTime startTime){
        return startTimeService.updateRecordById(id,startTime);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        startTimeService.deleteById(id);
    }

}
