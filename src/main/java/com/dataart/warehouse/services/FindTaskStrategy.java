package com.dataart.warehouse.services;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.Task;
import org.springframework.stereotype.Component;

@Component
public interface FindTaskStrategy {
    Task findTaskForLoader(Loader loader, TaskManagementService taskManagementService);
}

