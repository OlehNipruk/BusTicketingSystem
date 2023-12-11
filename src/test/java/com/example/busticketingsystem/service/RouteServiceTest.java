package com.example.busticketingsystem.service;

import com.example.busticketingsystem.model.Route;
import com.example.busticketingsystem.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddRoute() {
        Route route = new Route();
        routeService.addRoute(route);
        verify(routeRepository, times(1)).save(route);
    }

    @Test
    void testGetAllRoutes() {
        Route route1 = new Route(1L, Arrays.asList("Destination1", "Destination2"), Arrays.asList(10.0, 15.0),
                LocalDateTime.of(2023, 1, 1, 10, 0), 20, Collections.emptyList());
        Route route2 = new Route(2L, Arrays.asList("Destination3", "Destination4"), Arrays.asList(12.0, 18.0),
                LocalDateTime.of(2023, 1, 2, 12, 0), 25, Collections.emptyList());
        List<Route> expectedRoutes = Arrays.asList(route1, route2);
        when(routeRepository.findAll()).thenReturn(expectedRoutes);
        List<Route> result = routeService.getAllRoutes();
        assertEquals(expectedRoutes, result);
    }

    @Test
    void testDeleteRoute() {
        Long routeId = 1L;
        routeService.deleteRoute(routeId);
        verify(routeRepository, times(1)).deleteById(routeId);
    }

    @Test
    void testGetAllDestinations() {
        List<Route> routes = Arrays.asList(
                new Route(1L, Arrays.asList("A", "B"), Arrays.asList(10.0, 15.0),
                        LocalDateTime.of(2023, 1, 1, 10, 0), 20, Collections.emptyList()),
                new Route(2L, Arrays.asList("B", "C"), Arrays.asList(12.0, 18.0),
                        LocalDateTime.of(2023, 1, 2, 12, 0), 25, Collections.emptyList())
        );
        when(routeRepository.findAll()).thenReturn(routes);
        Set<String> result = routeService.getAllDestinations();
        assertEquals(new HashSet<>(Arrays.asList("A", "B", "C")), result);
    }

    @Test
    void testGetRouteById() {
        Long routeId = 1L;
        Route expectedRoute = new Route();
        when(routeRepository.findRouteById(routeId)).thenReturn(expectedRoute);
        Route result = routeService.getRouteById(routeId);
        assertEquals(expectedRoute, result);
    }
}
