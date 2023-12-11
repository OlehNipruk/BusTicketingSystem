package com.example.busticketingsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routeid")
    private Route route;

    @Column(name = "destination")
    private String destination;

    @Column(name = "seatNumber")
    private int seatNumber;

    @Column(name = "ticketPrice")
    private double ticketPrice;

    @Column(name = "departureDateTime", columnDefinition = "TIMESTAMP")
    private LocalDateTime departureDateTime;
}
