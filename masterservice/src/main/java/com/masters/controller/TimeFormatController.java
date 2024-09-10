package com.masters.controller;

import com.masters.entity.TimeFormat;
import com.masters.service.TimeFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/timeFormat")
public class TimeFormatController {
     @Autowired
     private TimeFormatService  timeFormatService;
     @GetMapping
     public List<TimeFormat> getAll(){
          return timeFormatService.getAll();
     }

     @GetMapping("/get/{id}")
     public TimeFormat getById(@PathVariable Long id){
          return timeFormatService.getById(id);
     }
     @PostMapping("/create")
     public TimeFormat saveTimeFormat(@RequestBody TimeFormat format){
          return timeFormatService.saveTimeFormat(format);
     }
     @DeleteMapping("/{id}")
     public void deleteById(@PathVariable Long id){
          timeFormatService.deleteById(id);
     }
     @PutMapping("/update/{id}")
     public TimeFormat updateRecordById(@PathVariable Long id ,@RequestBody TimeFormat timeFormat){
          return timeFormatService.updateRecordById(id,timeFormat);
     }
}
