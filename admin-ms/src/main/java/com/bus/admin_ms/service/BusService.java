package com.bus.admin_ms.service;

import com.bus.admin_ms.entity.Bus;
import com.bus.admin_ms.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // Get all buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Get bus by ID
    public Optional<Bus> getBusById(Long id) {
        return busRepository.findById(id);
    }

    // Create or update bus
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Delete bus by ID
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}
