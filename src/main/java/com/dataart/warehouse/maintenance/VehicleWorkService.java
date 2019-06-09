package com.dataart.warehouse.maintenance;

import com.dataart.warehouse.model.Vehicle;
import com.dataart.warehouse.model.VehicleStatus;
import com.dataart.warehouse.repository.VehicleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty("simulation.job.vehicle-init.enabled")
public class VehicleWorkService {
    private static final Logger log = LoggerFactory.getLogger(VehicleWorkService.class);
    private int TIME_AFTER_DEPARTURE = 3000;
    private VehicleRepo vehicleRepo;

    @Autowired
    public VehicleWorkService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    @Scheduled(cron = "${simulation.job.vehicle-init.cron}")
    public void completeVehicleWork() {
        List<Vehicle> vehicleList = vehicleRepo.findByStatus(VehicleStatus.OCCUPIED);
        for (Vehicle vehicle : vehicleList) {
            Instant departureTime = vehicle.getRoute().getDepartureTime();
            if (Objects.nonNull(departureTime) && differenceBetweenNowAndDepartureTime(departureTime) > TIME_AFTER_DEPARTURE) {
                log.info("Vehicle ID {} is available", vehicle.getId());
                vehicle.setRoute(null);
                vehicle.setStatus(VehicleStatus.FREE);
                vehicleRepo.save(vehicle);

            }
        }
    }

    private Long differenceBetweenNowAndDepartureTime(Instant vehicleTime) {
        return Instant.now().toEpochMilli() - vehicleTime.toEpochMilli();
    }

}
