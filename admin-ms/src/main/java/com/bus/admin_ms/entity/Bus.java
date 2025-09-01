package com.bus.admin_ms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String source;
    private String destination;
    private int totalSeats;

    // Default constructor
    public Bus() {
    }

    // Parameterized constructor
    public Bus(String name, String source, String destination, int totalSeats) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    // Mapping "route" from JSON (like "Delhi - Jaipur")
    public void setRoute(String route) {
        if (route != null && route.contains(" - ")) {
            String[] parts = route.split(" - ");
            this.source = parts[0].trim();
            this.destination = parts[1].trim();
        }
    }

    // Mapping "seats" from JSON
    public void setSeats(int seats) {
        this.totalSeats = seats;
    }
}
