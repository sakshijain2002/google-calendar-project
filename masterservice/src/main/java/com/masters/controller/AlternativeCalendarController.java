package com.masters.controller;

import com.masters.entity.AlternativeCalendar;
import com.masters.service.AlternativeCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/master/alternativeCalendar")
public class AlternativeCalendarController {
    @Autowired
    private AlternativeCalendarService alternativeCalendarService;

    @GetMapping("/get/{id}")
    public Optional<AlternativeCalendar> getById(@PathVariable Long id){
        return alternativeCalendarService.getById(id);
    }

    @GetMapping
    public List<AlternativeCalendar> getAll(){
        return alternativeCalendarService.getAll();
    }
    @PostMapping("/create")
    public AlternativeCalendar save(@RequestBody AlternativeCalendar alternativeCalendar){
        return alternativeCalendarService.save(alternativeCalendar);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        alternativeCalendarService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public AlternativeCalendar updateRecordById(@PathVariable Long id ,@RequestBody AlternativeCalendar alternativeCalendar){
        return alternativeCalendarService.updateRecordById(id,alternativeCalendar);
    }

}
