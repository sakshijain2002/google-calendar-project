package com.masters.service;

import com.masters.entity.AlternativeCalendar;
import com.masters.model.Role;
import com.masters.repository.AlternativeCalendarRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlternativeCalendarService {
    @Autowired
    private AlternativeCalendarRepository alternativeCalendarRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    public Optional<AlternativeCalendar> getById(Long id){
        return alternativeCalendarRepository.findById(id);
    }

    public List<AlternativeCalendar> getAll(){
        return alternativeCalendarRepository.findAll();
    }

    public AlternativeCalendar save(AlternativeCalendar alternativeCalendar){
        return alternativeCalendarRepository.save(alternativeCalendar);
    }

    public void deleteById(Long id, String email) {
        // Fetch the user's role as a string (e.g., "ADMIN, USER")
        String userRole = userServiceClient.getRoleByEmail(email);

        // Check if the user has the "ADMIN" role
        if (!userRole.contains("ADMIN")) {
            System.out.println("Access denied");
            return; // Exit the method if the user is not an ADMIN
        }

        // Proceed with deletion if the user has the ADMIN role
        alternativeCalendarRepository.deleteById(id);
    }


    public AlternativeCalendar updateRecordById(Long id, AlternativeCalendar record) {

        Optional<AlternativeCalendar> calendarRecord= alternativeCalendarRepository.findById(id);
        if(calendarRecord.isPresent()){
            AlternativeCalendar calendar =  calendarRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,calendar);
            alternativeCalendarRepository.save(calendar);

        }
        return alternativeCalendarRepository.save(record);
    }

}
