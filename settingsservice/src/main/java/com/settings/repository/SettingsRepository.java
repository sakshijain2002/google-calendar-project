package com.settings.repository;


import com.settings.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings,Long> {

    Optional<Settings> findByUserId(Integer userId);
}
