package com.bus.payment_ms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;      // Booking reference
    private Double amount;       // Payment amount
    private String status;       // PENDING, COMPLETED, FAILED
}
