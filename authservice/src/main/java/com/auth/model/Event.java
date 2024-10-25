package com.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Event {
    private Long id;
    private String title;
    private String description;

    private String label;
    private Long day;


    // Getters and Setters
}