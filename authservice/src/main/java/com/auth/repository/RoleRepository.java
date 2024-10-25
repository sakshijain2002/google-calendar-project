package com.auth.repository;

import com.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);

    Set<Role> findByRoleIn(Collection<String> roles);
}

//    Role getRoleByUser(Integer id);

