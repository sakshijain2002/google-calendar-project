package com.event.service;

import com.event.entity.Event;
import com.event.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Event> getAll(){
        return eventRepository.findAll();
    }
    public Event getById(Long id){
        return eventRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public Event saveEvent(Event event){
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
