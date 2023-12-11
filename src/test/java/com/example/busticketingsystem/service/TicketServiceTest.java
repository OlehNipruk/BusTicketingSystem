package com.example.busticketingsystem.service;

import com.example.busticketingsystem.model.Route;
import com.example.busticketingsystem.model.Ticket;
import com.example.busticketingsystem.repository.RouteRepository;
import com.example.busticketingsystem.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testBuyTicket() {
        // Arrange
        Long routeId = 1L;
        String destination = "Destination";
        Route route = new Route();
        route.setId(routeId);
        route.setTotalSeats(10);
        route.setDepartureDateTime(LocalDateTime.now());
        route.setDestinations(Arrays.asList(destination));
        route.setFares(Arrays.asList(10.0));

        when(routeRepository.findRouteById(routeId)).thenReturn(route);
        Ticket boughtTicket = ticketService.buyTicket(routeId, destination);
        assertNotNull(boughtTicket);
        assertEquals(route, boughtTicket.getRoute());
        assertEquals(destination, boughtTicket.getDestination());
        verify(routeRepository, times(1)).save(route);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void testGetAllTicketIds() {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));
        List<Long> ticketIds = ticketService.getAllTicketIds();
        assertEquals(2, ticketIds.size());
        assertTrue(ticketIds.contains(1L));
        assertTrue(ticketIds.contains(2L));
    }

    @Test
    void testReturnTicketById() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        Route route = new Route();
        route.setTotalSeats(5);
        ticket.setRoute(route);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        ticketService.returnTicketbyId(ticketId);

        verify(routeRepository, times(1)).save(route);
        verify(ticketRepository, times(1)).deleteById(ticketId);
        assertEquals(6, route.getTotalSeats()); // Assuming initial totalSeats was 5
    }

    @Test
    void testGetTicketsByRouteId() {
        Long routeId = 1L;
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        when(ticketRepository.findAllByRouteId(routeId)).thenReturn(Arrays.asList(ticket1, ticket2));
        List<Ticket> tickets = ticketService.getTicketsbyRouteId(routeId);
        assertEquals(2, tickets.size());
        assertTrue(tickets.contains(ticket1));
        assertTrue(tickets.contains(ticket2));
    }
}
