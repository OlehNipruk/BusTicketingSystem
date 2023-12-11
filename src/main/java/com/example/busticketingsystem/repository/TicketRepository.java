package com.example.busticketingsystem.repository;

import com.example.busticketingsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByRouteId(Long route_id);
}