package com.example.busticketingsystem.service;
import com.example.busticketingsystem.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.busticketingsystem.model.Route;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    public void addRoute(Route route) {
        routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public Set<String> getAllDestinations() {
        List<Route> allRoutes = routeRepository.findAll();
        Set<String> uniqueDestinations = allRoutes.stream()
                .flatMap(route -> route.getDestinations().stream())
                .collect(Collectors.toSet());
        return uniqueDestinations;
    }

    public Optional<Route> findNearestRouteToDestination(String destination) {
        LocalDateTime currentTime = LocalDateTime.now();
        return routeRepository.findNearestRouteToDestination(currentTime, destination);
    }

    public List<Long> findRouteIdsAfterCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return routeRepository.findRouteIdsAfterTime(currentTime);
    }

    public Route getRouteById(Long routeId) {
        return routeRepository.findRouteById(routeId);
    }
}






