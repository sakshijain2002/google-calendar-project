package com.event.controller;

import com.event.entity.Event;
import com.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAll(){
        return eventService.getAll();
    }
    @GetMapping("/get/{id}")
    public Event getById(@PathVariable Long id){
        return eventService.getById(id);
    }

    @PostMapping("/create")
    public Event saveEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        eventService.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public Event updateRecordById(@PathVariable Long id,@RequestBody Event event){
        return eventService.updateRecordById(id, event);
    }

}
