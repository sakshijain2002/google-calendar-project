package com.masters.repository;

import com.masters.entity.DefaultDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultDurationRepository extends JpaRepository<DefaultDuration,Long> {

}
