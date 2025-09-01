package com.bus.booking_ms.service;

import com.bus.booking_ms.entity.Booking;
import com.bus.booking_ms.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    // URLs for microservices
    private final String INVENTORY_URL = "http://INVENTORY-MS/inventory";
    private final String PAYMENT_URL = "http://PAYMENTMSAPPLICATION/payment";

    public BookingService(BookingRepository bookingRepository, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    public Booking createBooking(Booking booking) {
        // Step 1: Check availability from Inventory-MS
        String inventoryCheckUrl = INVENTORY_URL + "/check-availability/" + booking.getBusId() + "/" + booking.getSeatsBooked();
        Boolean available = restTemplate.getForObject(inventoryCheckUrl, Boolean.class);

        if (available != null && available) {
            // Step 2: Reduce seats in inventory temporarily
            restTemplate.postForObject(
                    INVENTORY_URL + "/update-seats?busId=" + booking.getBusId() + "&seatsBooked=" + booking.getSeatsBooked(),
                    null,
                    Object.class
            );

            // Step 3: Save booking as PENDING
            booking.setStatus("PENDING");
            Booking savedBooking = bookingRepository.save(booking);

            // Step 4: Create payment entry in Payment-MS
            PaymentRequest paymentRequest = new PaymentRequest(savedBooking.getId(), 500.0, "PENDING"); // Amount can be dynamic
            restTemplate.postForObject(PAYMENT_URL + "/create", paymentRequest, Object.class);

            return savedBooking;
        } else {
            throw new RuntimeException("Requested seats not available for Bus ID " + booking.getBusId());
        }
    }

    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);

        // If CONFIRMED, update inventory again to finalize seats
        if ("CONFIRMED".equalsIgnoreCase(status)) {
            restTemplate.postForObject(
                    INVENTORY_URL + "/update-seats?busId=" + booking.getBusId() + "&seatsBooked=" + booking.getSeatsBooked(),
                    null,
                    Object.class
            );
        }

        return bookingRepository.save(booking);
    }

    // Inner class to send payment request to Payment-MS
    static class PaymentRequest {
        private Long bookingId;
        private Double amount;
        private String status;

        public PaymentRequest(Long bookingId, Double amount, String status) {
            this.bookingId = bookingId;
            this.amount = amount;
            this.status = status;
        }

        public Long getBookingId() { return bookingId; }
        public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
