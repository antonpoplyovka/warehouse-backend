package com.dataart.warehouse.services;

import com.dataart.warehouse.model.*;
import com.dataart.warehouse.repository.PalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Order(value = 3)
class FindTaskFromBufferZoneStrategy implements FindTaskStrategy {
    private static Integer SECOND_ZONE_ID = 2;
    @Autowired
    private PalletRepo palletRepo;

    @Override
    public Task findTaskForLoader(Loader loader, TaskManagementService taskManagementService) {
        Pallet pallet = palletRepo.findTopByStatusAndLocation_ZoneNumberOrderByRoute_ArrivalTime(PalletStatus.AT_LOCATION, SECOND_ZONE_ID);
        Task task = null;
        if (Objects.nonNull(pallet) && isGateOpened(pallet.getRoute())) {
            Location location = taskManagementService.findDestinationLocationAtGate(pallet);
            if (Objects.nonNull(location)) {
                task = taskManagementService.createTask(pallet.getLocation(), location, pallet, loader);
            }
        }
        return task;
    }

    private boolean isGateOpened(Route route) {
        return Objects.nonNull(route.getGate());
    }
}
