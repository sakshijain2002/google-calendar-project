package com.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eventTitle;
    private String dayDate;
    private String eventTime;
    private Boolean allDay;
    private String repeatType;
    private String Location;
    private String description;
    private Long timeZoneId;


}
