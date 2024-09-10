package com.masters.service;

import com.masters.entity.DateFormat;
import com.masters.repository.DateFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DateFormatService {
    @Autowired
    private DateFormatRepository dateFormatRepository;

    public List<DateFormat> getAllDateFormats() {
        return dateFormatRepository.findAll();
    }

    public DateFormat getDateFormatById(Integer id) {
        return dateFormatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Date format not found"));
    }

    public DateFormat createDateFormat(DateFormat dateFormat) {
        return dateFormatRepository.save(dateFormat);
    }

    public void deleteById(Integer id){
        dateFormatRepository.deleteById(id);
    }

    public DateFormat updateRecordById(Integer id, DateFormat record) {

        Optional<DateFormat> dateFormatRecord = dateFormatRepository.findById(id);
        if(dateFormatRecord.isPresent()){
            DateFormat format =  dateFormatRecord.get();
            dateFormatRepository.save(format);

        }
        return dateFormatRepository.save(record);
    }
}
