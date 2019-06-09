package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Vehicle;
import com.dataart.warehouse.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
    Vehicle findTopByStatus(VehicleStatus vehicleStatus);

    List<Vehicle> findByStatus(VehicleStatus status);
}
