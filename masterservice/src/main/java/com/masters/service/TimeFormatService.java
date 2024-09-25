package com.masters.service;

import com.masters.entity.TimeFormat;
import com.masters.repository.TimeFormatRepository;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeFormatService {
    @Autowired
     private  TimeFormatRepository timeFormatRepository;
    @Autowired
    private ModelMapper modelMapper;
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

        Optional<TimeFormat> timeFormatRecord = timeFormatRepository.findById(id);
        if(timeFormatRecord.isPresent()){
            TimeFormat timeFormat =  timeFormatRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,timeFormat);
            timeFormatRepository.save(timeFormat);

        }
        return timeFormatRepository.save(record);
    }

}
