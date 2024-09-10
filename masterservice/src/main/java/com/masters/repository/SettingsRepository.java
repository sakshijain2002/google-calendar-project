package com.masters.repository;

import com.masters.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings,Long> {

    Optional<Settings> findByUserId(Integer userId);
}
