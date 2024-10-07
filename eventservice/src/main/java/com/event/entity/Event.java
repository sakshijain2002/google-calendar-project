package com.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;
    private String title;
    private Long day;
//    private String eventTime;
//    private Boolean allDay;
//    private String repeatType;
//    private String Location;
    private String description;
//    private Long timeZoneId;

    private String label;
    private Integer userId;

    @JsonIgnore
    private String email;


}
