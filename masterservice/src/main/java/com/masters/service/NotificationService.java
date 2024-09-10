package com.masters.service;

import com.masters.entity.Notification;
import com.masters.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

     public List<Notification> getAll(){
         return notificationRepository.findAll();
     }

     public Notification getById(Long id){
         return notificationRepository.findById(id).orElseThrow(()->new RuntimeException("data not found"));
     }


     public Notification saveNotification(Notification notification){
         return notificationRepository.save(notification);
     }

     public void deleteById(Long id){
         notificationRepository.deleteById(id);
     }
    public Notification updateRecordById(Long id, Notification record) {

        Optional<Notification> notificationRecord = notificationRepository.findById(id);
        if(notificationRecord.isPresent()){
           Notification notification =  notificationRecord.get();
            notificationRepository.save(notification);

        }
        return notificationRepository.save(record);
    }


}
