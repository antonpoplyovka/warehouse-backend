package com.dataart.warehouse.maintenance;

import com.dataart.warehouse.model.*;
import com.dataart.warehouse.repository.GateRepo;
import com.dataart.warehouse.repository.RouteRepo;
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
@ConditionalOnProperty("simulation.job.outbound-init.enabled")
public class OutboundInitializationService {

    private static final Logger log = LoggerFactory.getLogger(OutboundInitializationService.class);
    private GateRepo gateRepo;
    private VehicleRepo vehicleRepo;
    private RouteRepo routeRepo;

    @Autowired
    public OutboundInitializationService(GateRepo gateRepo, VehicleRepo vehicleRepo, RouteRepo routeRepo) {
        this.gateRepo = gateRepo;
        this.vehicleRepo = vehicleRepo;
        this.routeRepo = routeRepo;
    }


    private void updateGateVehicleRoute(Gate gate, Vehicle vehicle, Route route) {
        gate.setStatus(GateStatus.OCCUPIED);
        gate.setRoute(route);
        gateRepo.save(gate);

        vehicle.setRoute(route);
        vehicle.setStatus(VehicleStatus.OCCUPIED);
        vehicleRepo.save(vehicle);

        route.setGate(gate);
        route.setVehicle(vehicle);
        routeRepo.save(route);
    }

    @Transactional
    @Scheduled(cron = "${simulation.job.outbound-init.cron}")
    public void initInboundLocations() {
        List<Route> routeList = routeRepo.findAllByStatusOrderByArrivalTime(RouteStatus.IN_PROGRESS);
        Instant nowTime = Instant.now();
        for (Route route : routeList) {
            if (nowTime.isAfter(route.getArrivalTime()) && Objects.isNull(route.getGate())) {
                Gate gate = gateRepo.findTopByStatus(GateStatus.FREE);
                Vehicle vehicle = vehicleRepo.findTopByStatus(VehicleStatus.FREE);
                if (gate == null || vehicle == null) {
                    break;
                }

                log.info("gate number: {} is opened for vehicle: {}", gate.getId(), vehicle.getId());
                updateGateVehicleRoute(gate, vehicle, route);
            }
        }
    }
}
