package com.masters.service;

import com.masters.entity.TimeZone;
import com.masters.repository.TimeZoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeZoneService {
    @Autowired
    private TimeZoneRepository timeZoneRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TimeZone> getAllTimeZones(){
        return timeZoneRepository.findAll();
    }

    public TimeZone getById(Long id){
       return timeZoneRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }
    public String createTimeZone(TimeZone timeZone){
        timeZoneRepository.save(timeZone);
        return "created";
    }
    public void deleteById(Long id){
        timeZoneRepository.deleteById(id);
    }
    public TimeZone updateRecordById(Long id, TimeZone record) {

        Optional<TimeZone> timeZoneRecord = timeZoneRepository.findById(id);
        if(timeZoneRecord.isPresent()){
           TimeZone timeZone =  timeZoneRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,timeZone);
            timeZoneRepository.save(timeZone);

        }
        return timeZoneRepository.save(record);
    }





}
