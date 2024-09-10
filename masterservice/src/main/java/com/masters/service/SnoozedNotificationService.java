package com.masters.service;

import com.masters.entity.SnoozedNotifications;
import com.masters.repository.SnoozedNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnoozedNotificationService {
    @Autowired
    private SnoozedNotificationRepository snoozedNotificationRepository;


    public List<SnoozedNotifications> getAll(){
        return snoozedNotificationRepository.findAll();
    }

    public SnoozedNotifications getById(Long id){
        return snoozedNotificationRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
    }

    public SnoozedNotifications saveSnoozedNotification(SnoozedNotifications snoozedNotifications){
        return snoozedNotificationRepository.save(snoozedNotifications);
    }

    public void deleteById(Long id){
        snoozedNotificationRepository.deleteById(id);
    }
    public SnoozedNotifications updateRecordById(Long id, SnoozedNotifications record) {

        Optional<SnoozedNotifications> snoozedNotificationRecord = snoozedNotificationRepository.findById(id);
        if(snoozedNotificationRecord.isPresent()){
           SnoozedNotifications snoozedNotifications =  snoozedNotificationRecord.get();
            snoozedNotificationRepository.save(snoozedNotifications);

        }
        return snoozedNotificationRepository.save(record);
    }

}
