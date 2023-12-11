package com.example.busticketingsystem.controller;

import com.example.busticketingsystem.model.Ticket;
import com.example.busticketingsystem.service.RouteService;
import com.example.busticketingsystem.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class TicketController {
    private final RouteService routeService;
    private final TicketService ticketService;

    @GetMapping("/buyTicket")
    public String showBuyTicketPage(Model model) {
        List<Long> routeIds = routeService.findRouteIdsAfterCurrentTime();
        model.addAttribute("routeIds", routeIds);


        if (!routeIds.isEmpty()) {
            Long defaultRouteId = routeIds.get(0);
            List<String> destinations = routeService.getRouteById(defaultRouteId).getDestinations();
            model.addAttribute("defaultRouteId", defaultRouteId);
            model.addAttribute("destinations", destinations);
        }
        return "buyTicket";
    }

    @GetMapping("/api/destinations/{routeId}")
    @ResponseBody
    public List<String> getDestinationsForRoute(@PathVariable Long routeId) {
        return routeService.getRouteById(routeId).getDestinations();
    }

    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam("routeId") Long routeId,
                            @RequestParam("destination") String destination,
                            Model model) {
        Ticket ticket = ticketService.buyTicket(routeId, destination);
        model.addAttribute("ticket", ticket);
        return "ticketDetails";
    }

    @GetMapping("/returnTicket")
    public String showReturnTicketPage(Model model) {
        List<Long> ticketIds = ticketService.getAllTicketIds();
        model.addAttribute("ticketIds", ticketIds);
        return "returnTicket";
    }

    @PostMapping("/returnTicket")
    public String returnTicket(@RequestParam("ticketId") Long ticketId) {
        ticketService.returnTicketbyId(ticketId);
        return "redirect:/returnTicket";
    }

    @GetMapping("/boardingPass")
    public String showBoardingPassPage(Model model) {
        List<Long> routeIds = routeService.findRouteIdsAfterCurrentTime();
        model.addAttribute("routeIds", routeIds);
        return "boardingPass";
    }

    @PostMapping("/boardingPass")
    public String generateBoardingPass(@RequestParam("routeId") Long routeId, Model model) {
        List<Ticket> routeTickets = ticketService.getTicketsbyRouteId(routeId);
        int availableSeats = routeService.getRouteById(routeId).getTotalSeats();
        model.addAttribute("routeTickets", routeTickets);
        model.addAttribute("availableSeats", availableSeats);
        List<Long> routeIds = routeService.findRouteIdsAfterCurrentTime();
        model.addAttribute("routeIds", routeIds);
        return "boardingPass";
    }
}
