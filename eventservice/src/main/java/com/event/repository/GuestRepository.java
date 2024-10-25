package com.event.repository;

import com.event.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest,Long> {

//    @Query(value = "select g.email from GuestEntity s")
//    List<String> findAllEmails();


//    String findEmail();
}
