package com.event.repository;

import com.event.entity.Event;
import com.event.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest,Long> {
//    Guest findByEmailAndEvents(String guestEmail, Long eventId);
    @Query("SELECT g FROM Guest g JOIN g.events e WHERE g.email = :email AND e.id = :eventId")
    Optional<Guest> findByEmailAndEventId(@Param("email") String email, @Param("eventId") Long eventId);

    Optional<Guest> findByEmail(String guestEmail);

    @Query("SELECT e FROM Event e JOIN e.guests g WHERE g.email = :email")
    List<Event> findEventsByGuestEmail(@Param("email") String email);


}
