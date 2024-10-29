package com.event.controller;

import com.event.entity.Event;
import com.event.entity.Guest;
import com.event.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public List<Guest> getAll(){
        return guestService.getAll();
    }

    @GetMapping("/{id}")
    public Guest getById(@PathVariable Long id){
        return guestService.getById(id);
    }

    @PostMapping("/save")
    public List<Guest> saveGuest(@RequestBody List<Guest> guest){
        return guestService.saveAll(guest);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        guestService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Guest updateRecordById(@PathVariable Long id,@RequestBody Guest guest){
        return guestService.updateRecordById(id,guest);
    }

    @GetMapping("/addToCalendar")
    public ResponseEntity<String> addToCalendar(
            @RequestParam("guestEmail") String guestEmail,
            @RequestParam("eventId") Long eventId,
            @RequestParam("status") String status) {

        String result = guestService.addGuestToEvent(guestEmail, eventId, status);

        if (result.equals("Event not found")) {
            return ResponseEntity.status(404).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{email}")
    public List<Event> findEventsByGuestEmail(@PathVariable String email){
        return guestService.getByGuestEmail(email);
    }

}
