package com.event.repository;

import com.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findByUserId(Integer userId);

    List<Event> findByEmail(String email);
}
