package com.bus.inventory_ms.repository;

import com.bus.inventory_ms.entity.BusInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusInventoryRepository extends JpaRepository<BusInventory, Integer> {
    Optional<BusInventory> findByBusId(Integer busId);

}


