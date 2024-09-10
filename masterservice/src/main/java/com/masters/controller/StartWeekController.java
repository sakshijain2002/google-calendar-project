package com.masters.controller;

import com.masters.entity.StartWeek;
import com.masters.service.StartWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/startWeek")
public class StartWeekController {
    @Autowired
    private StartWeekService startWeekService;
    @GetMapping
    public List<StartWeek> getAll(){
        return startWeekService.getAll();
    }

    @GetMapping("/get/{id}")
    public StartWeek getById(@PathVariable Long id){
        return startWeekService.getById(id);
    }
    @PostMapping("/create")
    public StartWeek create(@RequestBody StartWeek startWeek){
        return startWeekService.create(startWeek);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        startWeekService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public StartWeek updateRecordById(@PathVariable Long id,@RequestBody StartWeek startWeek){
        return startWeekService.updateRecordById(id,startWeek);
    }
}
