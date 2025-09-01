package com.bus.inventory_ms.service;

import com.bus.inventory_ms.entity.BusInventory;
import com.bus.inventory_ms.repository.BusInventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final BusInventoryRepository repository;

    public InventoryService(BusInventoryRepository repository) {
        this.repository = repository;
    }

    // Check if requested seats are available
    public boolean checkAvailability(Integer busId, Integer seatsRequested) {
        BusInventory bus = repository.findByBusId(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID " + busId));
        return bus.getAvailableSeats() >= seatsRequested;
    }

    // Update seats after booking
    public BusInventory updateSeats(Integer busId, Integer seatsBooked) {
        BusInventory bus = repository.findByBusId(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID " + busId));
        bus.setAvailableSeats(bus.getAvailableSeats() - seatsBooked);
        return repository.save(bus);
    }
}
