package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Location;
import com.dataart.warehouse.model.LocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {
    List<Location> findByStatusAndZoneNumber(LocationStatus status, Integer zoneNumber);

    Location findTopByStatusAndGate_Id(LocationStatus status, Integer gateId);

    Location findTopByStatusAndZoneNumber(LocationStatus status, Integer zoneNumber);
}
