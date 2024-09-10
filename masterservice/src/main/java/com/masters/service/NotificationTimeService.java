package com.masters.service;


import com.masters.entity.NotificationTime;
import com.masters.repository.NotificationTimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationTimeService {

    @Autowired
    private NotificationTimeRepository notificationTimeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<NotificationTime> getAll(){
        return notificationTimeRepository.findAll();
    }

    public NotificationTime getById(Long id){
        return notificationTimeRepository.findById(id).orElseThrow(()-> new RuntimeException("data not found"));
    }
    public NotificationTime save(NotificationTime notificationTime){
        return notificationTimeRepository.save(notificationTime);
    }
    public void deleteById(Long id){
        notificationTimeRepository.deleteById(id);
    }
    public NotificationTime updateRecordById(Long id,NotificationTime record){
        Optional<NotificationTime> timeRecord = notificationTimeRepository.findById(id);
        if(timeRecord.isPresent()){
            NotificationTime time =  timeRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,time);
            notificationTimeRepository.save(time);

        }
        return record;

    }
}
