package com.settings.service;


import com.settings.entity.Settings;
import com.settings.model.UserModel;
import com.settings.repository.SettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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
    private LanguageService languageService;

    @Autowired
    private ModelMapper modelMapper;

    public List<Settings> getAll() {
        return settingsRepository.findAll();
    }

    public Optional<Settings> getAll(String token){
        String email;
        email = userServiceClient.extractEmailFromToken(token);
        return settingsRepository.findSettingsByEmail(email);
    }

    public Settings getById(Long id) {
        return settingsRepository.findById(id).orElseThrow(() -> new RuntimeException("data not found exception"));
    }

//    public Settings createSettings(Settings settings, String token) {
//      String userEmail = userServiceClient.extractEmailFromToken(token);
//      settings.setEmail(userEmail);
//        return settingsRepository.save(settings);
//    }
    @Transactional
    public Settings createOrUpdateSettings(Settings settings, String token) {
    // Extract the user email from the token
    String userEmail = userServiceClient.extractEmailFromToken(token);

    // Try to find an existing settings record for the user
    Optional<Settings> existingSettings = settingsRepository.findSettingsByEmail(userEmail);

    if (existingSettings.isPresent()) {
        // If the settings exist, update the existing record with new data
        Settings existingRecord = existingSettings.get();

        // Use ModelMapper or manually update fields from the new settings to the existing record
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(settings, existingRecord);

        // Save the updated settings
        return settingsRepository.save(existingRecord);
    } else {
        // If no settings exist, create a new settings record
        settings.setEmail(userEmail);
        return settingsRepository.save(settings);
    }
}


    public void deleteById(Long id) {
        settingsRepository.deleteById(id);
    }

    public Settings updateRecordById(String token, Settings record) {
        // Log the token value
        System.out.println("Token passed to the repository: [" + token + "]");
        String trimmedToken = token.trim();  // Trim any leading/trailing spaces
        String userEmail = userServiceClient.extractEmailFromToken(trimmedToken);

        Optional<Settings> settingsRecord = settingsRepository.findSettingsByEmail(userEmail);

        // Log the result from the repository
        if (settingsRecord.isPresent()) {
            System.out.println("Settings found for email: " + settingsRecord.get().getEmail());
        } else {
            System.out.println("No settings found for email: [" + trimmedToken + "]");
            throw new RuntimeException("Settings record not found for email: " + trimmedToken);
        }

        Settings settings = settingsRecord.get();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(record, settings);
        settingsRepository.save(settings);

        return settings;
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
        if (existingSettings.isPresent()) {
            Settings settings = existingSettings.get();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(newSettings, settings);

            return settingsRepository.save(settings);
        } else {
            // Handle the case where settings for the given userId do not exist
            throw new EntityNotFoundException("Settings for user with ID " + userId + " not found.");
        }

    }

    public Optional<Settings> getSettingsForUser(Integer userId) {
        return settingsRepository.findByUserId(userId);
    }
}
