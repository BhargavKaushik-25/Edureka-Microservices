package com.bus.inventory_ms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bus_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long busId;

    private Integer totalSeats;
    private Integer availableSeats;
}
