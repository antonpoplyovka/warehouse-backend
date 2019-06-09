package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Route;
import com.dataart.warehouse.model.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepo extends JpaRepository<Route, Integer> {
    List<Route> findAllByStatusIn(List<RouteStatus> routeStatuses);

    List<Route> findAllByStatusOrderByArrivalTime(RouteStatus routeStatus);
}
