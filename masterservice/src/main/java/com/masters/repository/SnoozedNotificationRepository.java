package com.masters.repository;

import com.masters.entity.SnoozedNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnoozedNotificationRepository extends JpaRepository<SnoozedNotifications,Long> {

}
