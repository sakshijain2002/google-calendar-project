package com.auth.model;

import com.auth.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserActivityDto {

        private List<Task> savedTasks;
        private List<Event> savedEvents;

        private UserCredential credentials;


        public UserActivityDto(UserCredential credentials,List<Event> savedEvents,List<Task> savedTasks) {
            this.credentials =credentials;
            this.savedEvents = savedEvents;
            this.savedTasks = savedTasks;
        }

        // Getters and Setters
    }

