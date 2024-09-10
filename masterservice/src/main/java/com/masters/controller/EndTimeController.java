package com.masters.controller;


import com.masters.entity.EndTime;
import com.masters.service.EndTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/endTime")
public class EndTimeController {
    @Autowired
    private EndTimeService endTimeService;

    @GetMapping
    public List<EndTime> getAll(){
        return endTimeService.getAll();
    }
    @GetMapping("/get/{id}")
    public EndTime getById(Long id){
        return endTimeService.getById(id);
    }
    @PostMapping("/create")
    public EndTime save(@RequestBody EndTime endTime){
        return endTimeService.save(endTime);
    }
    @PutMapping("/update/{id}")
    public EndTime updateRecordById(@PathVariable Long id, @RequestBody EndTime endTime){
        return endTimeService.updateRecordById(id,endTime);
    }
}
