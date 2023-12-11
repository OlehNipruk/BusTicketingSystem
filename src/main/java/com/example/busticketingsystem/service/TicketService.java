package com.example.busticketingsystem.service;

import com.example.busticketingsystem.model.Route;
import com.example.busticketingsystem.model.Ticket;
import com.example.busticketingsystem.repository.RouteRepository;
import com.example.busticketingsystem.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;

    public Ticket buyTicket(Long routeId, String destination) {
        Ticket ticket = new Ticket();
        Route route = routeRepository.findRouteById(routeId);
        route.setTotalSeats(route.getTotalSeats() - 1);
        routeRepository.save(route);

        ticket.setRoute(route);
        ticket.setDepartureDateTime(route.getDepartureDateTime());
        ticket.setDestination(destination);
        int index = route.getDestinations().indexOf(destination);
        ticket.setTicketPrice(route.getFares().get(index));
        ticketRepository.save(ticket);
        return ticket;
    }

    public List<Long> getAllTicketIds() {
        return ticketRepository.findAll().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
    }
    public void returnTicketbyId(Long id) {
        Route route = ticketRepository.findById(id).get().getRoute();
        route.setTotalSeats(route.getTotalSeats() + 1);
        routeRepository.save(route);
        ticketRepository.deleteById(id);
    }
    public List<Ticket> getTicketsbyRouteId(Long routeId) {
        return ticketRepository.findAllByRouteId(routeId);
    }
}
