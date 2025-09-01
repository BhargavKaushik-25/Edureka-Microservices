package com.bus.admin_ms.repository;

import com.bus.admin_ms.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    // No extra code needed here for now
    // JpaRepository provides basic CRUD methods
}
