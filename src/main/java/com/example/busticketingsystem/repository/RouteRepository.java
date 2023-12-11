package com.example.busticketingsystem.repository;

import com.example.busticketingsystem.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(value = "SELECT * FROM Route r " +
            "WHERE r.departureDateTime > :currentTime " +
            "AND :destination IN (SELECT d.destination FROM route_destinations d WHERE d.route_id = r.id) " +
            "AND r.totalSeats > 0 " +
            "ORDER BY r.departureDateTime ASC LIMIT 1", nativeQuery = true)
    Optional<Route> findNearestRouteToDestination(
            @Param("currentTime") LocalDateTime currentTime,
            @Param("destination") String destination
    );
    @Query(value = "SELECT * FROM Route r " +
            "WHERE r.departureDateTime > :currentTime " +
            "AND r.totalSeats > 0 ", nativeQuery = true)
    List<Long> findRouteIdsAfterTime(@Param("currentTime") LocalDateTime currentTime);

    Route findRouteById(Long id);

}