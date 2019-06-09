package com.dataart.warehouse.services;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.Pallet;
import com.dataart.warehouse.model.Task;
import com.dataart.warehouse.repository.PalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Order(value = 1)
class FindTaskFromGateStrategy implements FindTaskStrategy {
    @Autowired
    private PalletRepo palletRepo;

    @Override
    public Task findTaskForLoader(Loader loader, TaskManagementService taskManagementService) {
        Pallet pallet = null;
        Task task = null;
        if (Objects.nonNull(loader.getGate())) {
            pallet = palletRepo.findTopByLocation_Gate_id(loader.getGate().getId());
        }
        if (Objects.nonNull(pallet)) {
            task = taskManagementService.createTask(pallet.getLocation(), null, pallet, loader);
        }
        return task;
    }

}
