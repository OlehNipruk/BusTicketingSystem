package com.example.busticketingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "route_destinations", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "destination")
    private List<String> destinations;

    @ElementCollection
    @CollectionTable(name = "route_fares", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "fare")
    private List<Double> fares;

    @Column(name = "departureDateTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime departureDateTime;

    @Column(name = "totalSeats")
    private int totalSeats;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}

