package com.event.repository;

import com.event.entity.Event;
import com.event.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {


    List<Event> findByEmail(String email);

    List<Event> findEventByEmail(String email);


    Event findSingleEventByEmail(String email);

//    List<Guest> findGuestByEvent(Event event);


}
