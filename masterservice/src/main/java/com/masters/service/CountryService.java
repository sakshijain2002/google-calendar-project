package com.masters.service;

import com.masters.entity.Country;
import com.masters.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    public Country getById(Long id){
        return countryRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public List<Country> getAll(){
        return countryRepository.findAll();
    }

    public Country createCountry(Country country){
        return countryRepository.save(country);
    }

//    public void deleteById(Long id, String email) {
//        // Fetch the user's roles as a string (e.g., "ADMIN, USER")
//        String userRole = userServiceClient.getRoleByEmail(email);
//
//        // Check if the user has the "ADMIN" role (case-insensitive)
//        if (userRole == null || !Arrays.stream(userRole.split(","))
//                .anyMatch(role -> role.trim().equalsIgnoreCase("admin"))) {
//            throw new RuntimeException("Access denied");
//        }
//
//        // Now, check if the language entry exists before deleting
//        if (!countryRepository.existsById(id)) {
//            throw new EntityNotFoundException("Language entry not found with id: " + id);
//        }
//
//        // Proceed with deletion if the user has the ADMIN role
//        countryRepository.deleteById(id);
//    }
    public void deleteById(Long id){
        countryRepository.deleteById(id);
    }

    public Country updateRecordById(Long id, Country record) {

        Optional<Country> countryRecord = countryRepository.findById(id);
        if(countryRecord.isPresent()){
            Country country =  countryRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,country);
            countryRepository.save(country);

        }
        return countryRepository.save(record);
    }

}
