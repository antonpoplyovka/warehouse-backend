package com.dataart.warehouse.simulation;

import com.dataart.warehouse.services.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoaderSessionFactory {
    @Autowired
    private LoaderSessionConfiguration configuration;
    @Autowired
    private TaskManagementService taskManagementService;

    public LoaderSession createSession(Integer loaderId) {
        LoaderSession loaderSession = new LoaderSession(loaderId, configuration, taskManagementService);
        loaderSession.start();
        return loaderSession;
    }
}
