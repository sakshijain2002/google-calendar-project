package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {
    private Long id;
    private String title;
    private String description;

    private String date;
    private String startTime;

    private String endTime;
    private String duration;
    private String reminder;
    private Boolean completed =false;


    // Getters and Setters
}
