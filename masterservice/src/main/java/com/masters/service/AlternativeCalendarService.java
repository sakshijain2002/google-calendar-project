package com.masters.service;

import com.masters.entity.AlternativeCalendar;
import com.masters.repository.AlternativeCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlternativeCalendarService {
    @Autowired
    private AlternativeCalendarRepository alternativeCalendarRepository;

    public Optional<AlternativeCalendar> getById(Long id){
        return alternativeCalendarRepository.findById(id);
    }

    public List<AlternativeCalendar> getAll(){
        return alternativeCalendarRepository.findAll();
    }

    public AlternativeCalendar save(AlternativeCalendar alternativeCalendar){
        return alternativeCalendarRepository.save(alternativeCalendar);
    }

    public void deleteById(Long id){
        alternativeCalendarRepository.deleteById(id);
    }

    public AlternativeCalendar updateRecordById(Long id, AlternativeCalendar record) {

        Optional<AlternativeCalendar> calendarRecord= alternativeCalendarRepository.findById(id);
        if(calendarRecord.isPresent()){
            AlternativeCalendar calendar =  calendarRecord.get();
            alternativeCalendarRepository.save(calendar);

        }
        return alternativeCalendarRepository.save(record);
    }

}
