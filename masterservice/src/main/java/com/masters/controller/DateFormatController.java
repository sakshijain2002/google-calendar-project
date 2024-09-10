package com.masters.controller;

import com.masters.entity.DateFormat;
import com.masters.service.DateFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master/dateFormat")
public class DateFormatController {
    @Autowired
    private DateFormatService dateFormatService;
    @GetMapping
    public List<DateFormat> getAll(){
        return dateFormatService.getAllDateFormats();
    }

    @GetMapping("/getById/{id}")
    public DateFormat getById(@PathVariable Integer id){
        return dateFormatService.getDateFormatById(id);
    }
    @PostMapping("/create")
    public DateFormat createDateFormat(@RequestBody DateFormat dateFormat){
        return dateFormatService.createDateFormat(dateFormat);
    }

    @PutMapping("/update/{id}")
    public DateFormat updateRecordById(@PathVariable Integer id, @RequestBody DateFormat dateFormat){
        return dateFormatService.updateRecordById(id,dateFormat);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        dateFormatService.deleteById(id);
    }

}
