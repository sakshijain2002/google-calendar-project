package com.masters.service;

import com.masters.entity.Settings;
import com.masters.model.UserModel;
import com.masters.repository.SettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ModelMapper modelMapper;

    public List<Settings> getAll(){
        return settingsRepository.findAll();
    }

    public Settings getById(Long id){
        return settingsRepository.findById(id).orElseThrow(()->new RuntimeException("data not found exception"));
    }

    public Settings createSettings(Settings settings){
        return settingsRepository.save(settings);
    }
    public void deleteById(Long id){
        settingsRepository.deleteById(id);
    }

    public Settings updateRecordById(Long id, Settings record) {

        Optional<Settings> settingsRecord = settingsRepository.findById(id);
        if(settingsRecord.isPresent()){
            Settings settings =  settingsRecord.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(record,settings);
            settingsRepository.save(settings);

        }
        return record;
    }

    public Settings saveSettings(Integer userId, Settings settings) {
        // Fetch user data from User Microservice using Feign Client
        UserModel user = userServiceClient.getUserById(userId);
        if (user != null) {
            settings.setUserId(user.getId()); // Assuming you have a userId field in Settings
            return settingsRepository.save(settings);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Settings updateSettings(Integer userId, Settings newSettings) {
        Optional<Settings> existingSettings = settingsRepository.findByUserId(userId);
        if(existingSettings.isPresent()){
            Settings settings = existingSettings.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(newSettings,settings);

           return settingsRepository.save(settings);
        }
        else {
            // Handle the case where settings for the given userId do not exist
            throw new EntityNotFoundException("Settings for user with ID " + userId + " not found.");
        }

    }

    public Optional<Settings> getSettingsForUser(Integer userId) {
        return settingsRepository.findByUserId(userId);
    }
}
