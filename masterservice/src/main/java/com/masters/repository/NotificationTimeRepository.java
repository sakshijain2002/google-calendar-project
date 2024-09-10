package com.masters.repository;


import com.masters.entity.NotificationTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTimeRepository extends JpaRepository<NotificationTime,Long> {
}
