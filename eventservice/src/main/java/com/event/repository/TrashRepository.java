package com.event.repository;

import com.event.entity.Trash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrashRepository extends JpaRepository<Trash,Long> {
//    Optional<Trash> findByEmail(String email);
    List<Trash> findByEmail(String email);
}
