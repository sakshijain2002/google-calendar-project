package com.event.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String email;

    private String invitationStatus;
    private Long eventId;


    @ManyToMany(mappedBy = "guests",cascade =  CascadeType.PERSIST)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();
}
