package com.masters.repository;

import com.masters.entity.GuestPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestPermissionRepository extends JpaRepository<GuestPermission,Long> {
}
