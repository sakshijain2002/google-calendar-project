package com.event.service;

import com.event.entity.Event;
import com.event.entity.Guest;
import com.event.repository.EventRepository;
import com.event.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailSender;

    public List<Guest> getAll(){
        return guestRepository.findAll();
    }
    public Guest getById(Long id){
        return guestRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public List<Guest> saveAll(List<Guest> guest){
        return guestRepository.saveAll(guest);
    }
    public void deleteById(Long id){
        guestRepository.deleteById(id);
     }

    public Guest updateRecordById(Long id,Guest record){
       Optional<Guest> guestRecord = guestRepository.findById(id);
       if(guestRecord.isEmpty()){
         Guest guest = guestRecord.get();
         modelMapper.map(record,guest);
         guestRepository.save(guest);
       }
       return record;
   }

    @Transactional
    public void updateInvitationStatus(String guestEmail, Long eventId, String status) {
        Guest guest = guestRepository.findByEmailAndEventId(guestEmail, eventId).orElseThrow(()-> new RuntimeException("data not found"));
        if (guest != null) {
            guest.setInvitationStatus(status);
            guestRepository.save(guest);
        } else {
            throw new RuntimeException("Guest or Event not found");
        }
    }

    @Transactional
    public String addGuestToEvent(String guestEmail, Long eventId, String status) {
        // Retrieve or create Guest based on email

        // Retrieve or create the Event by ID
        List<Event> event = guestRepository.findEventsByGuestEmail(guestEmail);

        eventRepository.saveAll(event);

        return "Event has been added to your calendar!";
    }


    public List<Event> getByGuestEmail(String email){
      return guestRepository.findEventsByGuestEmail(email);
    }
}
