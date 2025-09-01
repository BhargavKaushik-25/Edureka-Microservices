package com.bus.admin_ms.controller;

import com.bus.admin_ms.entity.Bus;
import com.bus.admin_ms.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")  // Base path for bus endpoints
public class BusController {

    @Autowired
    private BusRepository busRepository;

    // GET all buses
    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // GET bus by ID
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new bus
    @PostMapping
    public Bus createBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }

    // PUT (update) an existing bus
    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus busDetails) {
        Optional<Bus> busOptional = busRepository.findById(id);
        if (!busOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Bus bus = busOptional.get();
        bus.setName(busDetails.getName());
        bus.setSource(busDetails.getSource());
        bus.setDestination(busDetails.getDestination());
        bus.setTotalSeats(busDetails.getTotalSeats());
        Bus updatedBus = busRepository.save(bus);
        return ResponseEntity.ok(updatedBus);
    }

    // DELETE a bus
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        if (!bus.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        busRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
