package com.event.service;

import com.event.entity.Event;
import com.event.model.UserModel;
import com.event.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<Event> getAll(){
        return eventRepository.findAll();
    }

    public List<Event> getEventByUserId(Integer userId){
        UserModel userModel = userServiceClient.getUserById(userId);
        if(userModel == null){
            throw new RuntimeException("user not found");
        }
        return eventRepository.findByUserId(userId);

    }
    public List<Event> getEventByEmail(String email){
        UserModel userModel = userServiceClient.getUserByEmail(email);
        if(userModel == null){
            throw new RuntimeException("user not found");
        }
        return eventRepository.findByEmail(email);

    }
    public Event getById(Long id){
        return eventRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public Event saveEvent(Event event,Integer id){
        UserModel userModel = userServiceClient.getUserById(id);
        if(userModel == null){
           throw new RuntimeException("user not found");
        }
        event.setUserId(id);
        // Save the event to the repository
        return eventRepository.save(event);
    }
    public Event saveEventByEmail(Event event,String email){
        UserModel userModel = userServiceClient.getUserByEmail(email);
        if(userModel == null){
            throw new RuntimeException("user not found");
        }
        event.setUserId(userModel.getId());
        event.setDay(LocalDate.now());
        // Save the event to the repository
        return eventRepository.save(event);
    }



    public void deleteById(Long id){
        eventRepository.deleteById(id);
    }

    public Event updateRecordById(Long id, Event record) {

        Optional<Event> eventRecord = eventRepository.findById(id);
        if(eventRecord.isPresent()){
            Event event =  eventRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,event);
            eventRepository.save(event);

        }
        return record;
    }



}
