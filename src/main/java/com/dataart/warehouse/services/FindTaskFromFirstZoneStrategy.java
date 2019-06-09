package com.dataart.warehouse.services;

import com.dataart.warehouse.model.*;
import com.dataart.warehouse.repository.LocationRepo;
import com.dataart.warehouse.repository.PalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Order(value = 2)
class FindTaskFromFirstZoneStrategy implements FindTaskStrategy {
    @Autowired
    private PalletRepo palletRepo;
    @Autowired
    private LocationRepo locationRepo;

    private Integer FIRST_ZONE_ID = 1;
    private Integer SECOND_ZONE_ID = 2;

    @Override
    public Task findTaskForLoader(Loader loader, TaskManagementService taskManagementService) {
        Pallet pallet = palletRepo.findTopByStatusAndLocation_ZoneNumberOrderByRoute_ArrivalTime(PalletStatus.AT_LOCATION, FIRST_ZONE_ID);
        Task task = null;
        Location location = null;
        if (Objects.nonNull(pallet)) {
            location = taskManagementService.findDestinationLocationAtGate(pallet);
            if (Objects.isNull(location)) {
                location = locationRepo.findTopByStatusAndZoneNumber(LocationStatus.FREE, SECOND_ZONE_ID);
            }
            if (Objects.nonNull(location)) {
                task = taskManagementService.createTask(pallet.getLocation(), location, pallet, loader);
            }
        }
        return task;
    }
}
