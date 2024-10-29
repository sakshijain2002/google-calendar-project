package com.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_guests",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )

    private Set<Guest> guests;


    @JsonIgnore
    private String email;

    // Method to convert emails to Guest entities and add them to guests


}
