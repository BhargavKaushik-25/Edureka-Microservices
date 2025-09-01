package com.bus.inventory_ms.controller;

import com.bus.inventory_ms.entity.BusInventory;
import com.bus.inventory_ms.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Check availability
    @GetMapping("/check-availability/{busId}/{seats}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Integer busId,
                                                     @PathVariable Integer seats) {
        boolean available = service.checkAvailability(busId, seats);
        return ResponseEntity.ok(available);
    }

    // Update seats after booking
    @PostMapping("/update-seats")
    public ResponseEntity<BusInventory> updateSeats(@RequestParam Integer busId,
                                                    @RequestParam Integer seatsBooked) {
        BusInventory updatedBus = service.updateSeats(busId, seatsBooked);
        return ResponseEntity.ok(updatedBus);
    }
}
