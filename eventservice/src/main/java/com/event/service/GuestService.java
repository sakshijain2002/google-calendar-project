package com.event.service;

import com.event.entity.Guest;
import com.event.repository.GuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private ModelMapper modelMapper;

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


}
