package com.masters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageSource messageSource;

    // API to get all messages for the current locale
    @GetMapping
    public Map<String, String> getAllMessages(@RequestParam(name = "lang", defaultValue = "en") String lang) {
        Locale locale = new Locale(lang);
        return getMessagesForLocale(locale);
    }

    // Helper method to fetch all messages for the current locale


    private Map<String, String> getMessagesForLocale(Locale locale) {
        Map<String, String> messages = new HashMap<>();

        // Load messages using ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("messages/messages", locale);

        // Loop through keys and get their corresponding values
        bundle.keySet().forEach(key -> {
            String message = bundle.getString(key);
            messages.put(key, message);
        });

        return messages;
    }
}



