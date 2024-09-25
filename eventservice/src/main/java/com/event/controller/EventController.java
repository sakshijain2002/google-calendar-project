package com.event.controller;

import com.event.entity.Event;
import com.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/user/{userId}")
    public List<Event> getEventByUserId(@PathVariable Integer userId){
        return eventService.getEventByUserId(userId);
    }
    @GetMapping("/user/{email}")
    public List<Event> getEventByEmail(@PathVariable String email){
        return eventService.getEventByEmail(email);
    }
    @PostMapping("/create/{userId}")
    public Event saveEvent(@RequestBody Event event, @PathVariable Integer userId){

        return eventService.saveEvent(event,userId);
    }
    @PostMapping("/add/{email}")
    public Event saveEventByEmail(@RequestBody Event event, @PathVariable String email){

        return eventService.saveEventByEmail(event,email);
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
