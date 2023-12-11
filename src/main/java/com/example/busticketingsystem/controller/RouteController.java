package com.example.busticketingsystem.controller;

import com.example.busticketingsystem.model.Route;
import com.example.busticketingsystem.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping({"/", "/index"})
    public String showIndexPage(Model model) {
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes);
        return "index";
    }

    @GetMapping("/addRoute")
    public String showAddRoutePage(Model model) {
        model.addAttribute("route", new Route());
        return "addRoute";
    }

    @PostMapping("/addRoute")
    public String addRoute(@ModelAttribute("route") Route route, Model model) {
        routeService.addRoute(route);
        return "redirect:/addRoute";
    }

    @PostMapping("/deleteRoute/{id}")
    public String deleteRoute(@PathVariable Long id, Model model) {
        routeService.deleteRoute(id);
        return "redirect:/";
    }

    @GetMapping("/selectNearestRoute")
    public String showSelectNearestRoutePage(Model model) {
        Set<String> destinations = routeService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "selectNearestRoute";
    }

    @PostMapping("/findNearestRoute")
    public String findNearestRoute(@RequestParam String destination, Model model) {
        Optional<Route> nearestRoute = routeService.findNearestRouteToDestination(destination);
        if (nearestRoute.isPresent()) {
            model.addAttribute("nearestRoute", nearestRoute.get());
        } else {

            model.addAttribute("message", "No available routes to the specified destination with free seats.");
        }
        Set<String> destinations = routeService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        return "selectNearestRoute";
    }
}
