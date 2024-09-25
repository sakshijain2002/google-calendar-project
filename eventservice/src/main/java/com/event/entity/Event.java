package com.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDate day;
    private String eventTime;
    private Boolean allDay;
    private String repeatType;
    private String Location;
    private String description;
    private Long timeZoneId;

    private Integer userId;
    private String email;


}
