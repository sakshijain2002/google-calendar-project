package com.masters.controller;

import com.masters.entity.AlternativeCalendar;
import com.masters.service.AlternativeCalendarService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    @DeleteMapping("/{id}/{email}")
    public void deleteById(@PathVariable Long id, @PathVariable String email){
        alternativeCalendarService.deleteById(id,email);
    }

    @PutMapping("/update/{id}")
    public AlternativeCalendar updateRecordById(@PathVariable Long id ,@RequestBody AlternativeCalendar alternativeCalendar){
        return alternativeCalendarService.updateRecordById(id,alternativeCalendar);
    }

}
