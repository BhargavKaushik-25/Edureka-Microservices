package com.bus.payment_ms.service;

import com.bus.payment_ms.entity.Payment;
import com.bus.payment_ms.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Create a new payment with PENDING status
    public Payment createPayment(Payment payment) {
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    // Update payment status
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        Payment updated = paymentRepository.save(payment);

        // Notify Booking-MS if payment is CONFIRMED
        // After payment is completed
        if ("COMPLETED".equalsIgnoreCase(status)) {
            RestTemplate restTemplate = new RestTemplate();
            String bookingUrl = "http://BOOKING-MS/booking/confirm-after-payment/" + payment.getBookingId();
            restTemplate.put(bookingUrl, null);
        }


        return updated;
    }

    // Fetch payment by bookingId
    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findAll()
                .stream()
                .filter(p -> p.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment not found for bookingId " + bookingId));
    }



}
