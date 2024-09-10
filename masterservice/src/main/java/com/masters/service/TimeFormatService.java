package com.masters.service;

import com.masters.entity.TimeFormat;
import com.masters.repository.TimeFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeFormatService {
    @Autowired
     private  TimeFormatRepository timeFormatRepository;
     public List<TimeFormat> getAll(){
         return timeFormatRepository.findAll();
     }

     public TimeFormat getById(Long id){
         return timeFormatRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
     }
     public TimeFormat saveTimeFormat(TimeFormat timeFormat){
         return timeFormatRepository.save(timeFormat);

     }
     public void deleteById(Long id){
         timeFormatRepository.deleteById(id);
     }

    public TimeFormat updateRecordById(Long id, TimeFormat record) {

        Optional<TimeFormat> taskRecord = timeFormatRepository.findById(id);
        if(taskRecord.isPresent()){
            TimeFormat taskEntity =  taskRecord.get();
            timeFormatRepository.save(taskEntity);

        }
        return timeFormatRepository.save(record);
    }

}
