package com.bus.booking_ms.controller;

import com.bus.booking_ms.entity.Booking;
import com.bus.booking_ms.repository.BookingRepository;
import com.bus.booking_ms.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bus.booking_ms.config.AppConfig;


@RestController
@RequestMapping("/booking") // keep this to match your Postman URL
public class BookingController {

    private final BookingService bookingService;

    private final AppConfig appConfig;

    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, AppConfig appConfig, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.appConfig = appConfig;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping  // <- changed from /create to default POST
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        // Step 1: Call Inventory-MS to check seat availability
        String inventoryUrl = "http://INVENTORY-MS/inventory/check-availability/"
                + booking.getBusId() + "/" + booking.getSeatsBooked();
        Boolean available = appConfig.restTemplate().getForObject(inventoryUrl, Boolean.class);

        // Step 2: Return error if seats are not available
        if (available == null || !available) {
            return ResponseEntity
                    .badRequest()
                    .body("Seats not available for Bus ID: " + booking.getBusId());
        }

        // Step 3: Update inventory seats
        String updateUrl = "http://INVENTORY-MS/inventory/update-seats?busId="
                + booking.getBusId() + "&seatsBooked=" + booking.getSeatsBooked();
        appConfig.restTemplate().postForObject(updateUrl, null, Object.class);

        // Step 4: Save booking
        Booking savedBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(savedBooking);
    }


//    @PutMapping("/update-status/{bookingId}")
//    public ResponseEntity<?> updateBookingStatus(
//            @PathVariable Long bookingId,
//            @RequestParam String status) {
//
//        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
//        return ResponseEntity.ok(updatedBooking);
//    }
    @PutMapping("/update-status/{bookingId}")
    public Booking updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(status);

        // If booking is CONFIRMED, finalize inventory seats
        if ("CONFIRMED".equalsIgnoreCase(status)) {
            String INVENTORY_URL = "http://INVENTORY-MS/inventory/update-seats";
            appConfig.restTemplate().postForObject(
                    INVENTORY_URL + "?busId=" + booking.getBusId() + "&seatsBooked=" + booking.getSeatsBooked(),
                    null, Object.class
            );
        }

        return bookingRepository.save(booking);
    }

    // Endpoint to be called by Payment-MS after payment completion
    @PutMapping("/confirm-after-payment/{bookingId}")
    public ResponseEntity<?> confirmBookingAfterPayment(@PathVariable Long bookingId) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(bookingId, "CONFIRMED");
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
