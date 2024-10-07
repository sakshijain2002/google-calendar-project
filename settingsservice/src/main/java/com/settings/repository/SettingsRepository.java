package com.settings.repository;


import com.settings.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings,Long> {

    Optional<Settings> findByUserId(Integer userId);

    @Query("SELECT s FROM Settings s WHERE LOWER(TRIM(s.email)) = LOWER(TRIM(:email))")
    Optional<Settings> findSettingsByEmail(@Param("email") String email);
}
