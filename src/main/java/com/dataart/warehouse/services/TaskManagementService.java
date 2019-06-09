package com.dataart.warehouse.services;

import com.dataart.warehouse.model.*;
import com.dataart.warehouse.repository.LoaderRepo;
import com.dataart.warehouse.repository.LocationRepo;
import com.dataart.warehouse.repository.PalletRepo;
import com.dataart.warehouse.repository.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class TaskManagementService {
    private static final Logger log = LoggerFactory.getLogger(TaskManagementService.class);

    private PalletRepo palletRepo;
    private LocationRepo locationRepo;
    private TaskRepo taskRepo;
    private LoaderRepo loaderRepo;
    private List<FindTaskStrategy> findTaskStrategies;

    @Autowired
    public TaskManagementService(PalletRepo palletRepo, LocationRepo locationRepo, TaskRepo taskRepo, LoaderRepo loaderRepo, List<FindTaskStrategy> findTaskStrategies) {
        this.palletRepo = palletRepo;
        this.locationRepo = locationRepo;
        this.taskRepo = taskRepo;
        this.loaderRepo = loaderRepo;
        this.findTaskStrategies = findTaskStrategies;
    }

    @Transactional(rollbackFor = Exception.class)
    public Task createTask(Location departureLocation, Location destinationLocation, Pallet pallet, Loader loader) {
        Task task = new Task(TaskStatus.STARTED, Instant.now(), pallet, departureLocation, destinationLocation, loader);

        task = taskRepo.save(task);

        if (Objects.isNull(task.getDestinationLocation())) {
            log.info("Loading task ID {} created from {} to vehicle near {} gate", task.getId(), task.getDepartureLocation().getName(),
                    task.getDepartureLocation().getGate().getName());
        } else {
            log.info("Marshaling task ID {} created from {} to  {} ", task.getId(), task.getDepartureLocation().getName(),
                    task.getDestinationLocation().getName());
        }


        pallet.setStatus(PalletStatus.RESERVED);
        if (destinationLocation != null) {
            destinationLocation.setStatus(LocationStatus.RESERVED);
        }
        loader.setPallet(pallet);
        loader.setStatus(LoaderStatus.OCCUPIED);
        loaderRepo.save(loader);
        return task;
    }

    @Transactional
    public Task findTaskForLoader(Integer loaderId) {
        Task task = null;
        Loader loader = loaderRepo.findTopById(loaderId);
        for (FindTaskStrategy findTaskStrategy : findTaskStrategies) {
            task = findTaskStrategy.findTaskForLoader(loader, this);
            if (Objects.nonNull(task)) {
                break;
            }
        }
        return task;
    }

    @Transactional
    public void setTaskTransportationStarted(Integer taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("task not found"));

        Pallet pallet = task.getPallet();
        Location departureLocation = task.getDepartureLocation();

        pallet.setStatus(PalletStatus.TRANSPORTING);
        pallet.setStatusUpdateDate(Instant.now());
        pallet.setLocation(null);

        departureLocation.setStatus(LocationStatus.FREE);

        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepo.save(task);
    }

    @Transactional
    public void setTaskTransportationFinished(Integer taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("task not found"));
        Pallet pallet = task.getPallet();
        Loader loader = task.getLoader();
        Gate gate = pallet.getRoute().getGate();

        Location destinationLocation = task.getDestinationLocation();

        if (Objects.nonNull(destinationLocation)) {
            pallet.setLocation(task.getDestinationLocation());
            pallet.setStatus(PalletStatus.AT_LOCATION);
            destinationLocation.setStatus(LocationStatus.OCCUPIED);
        } else {
            pallet.setLocation(null);
            pallet.setStatus(PalletStatus.SHIPPED);
        }
        pallet.setLoader(null);
        pallet.setStatusUpdateDate(Instant.now());
        task.setStatus(TaskStatus.COMPLETED);
        loader.setPallet(null);
        loader.setStatus(LoaderStatus.AVAILABLE);
        if (isRouteCompleted(pallet)) {
            closeRoute(pallet, gate, loader);
        }
        if (Objects.nonNull(gate) && noLoaderNearGate(gate)) {
            loader.setGate(gate);
            gate.setLoader(loader);
        }
    }

    private Boolean noLoaderNearGate(Gate gate) {
        return Objects.isNull(loaderRepo.findTopByGate_Id(gate.getId())) && gate.getStatus().equals(GateStatus.OCCUPIED);
    }

    private Boolean isRouteCompleted(Pallet palletFromRoute) {
        Route route = palletFromRoute.getRoute();
        return palletRepo.countByStatusAndRoute_Id(PalletStatus.SHIPPED, route.getId()) == route.getPalletCount();
    }

    private void closeRoute(Pallet palletFromRoute, Gate gateFromRoute, Loader loader) {
        palletFromRoute.getRoute().setStatus(RouteStatus.COMPLETED);
        palletFromRoute.getRoute().setGate(null);
        palletFromRoute.getRoute().setVehicle(null);
        palletFromRoute.getRoute().setDepartureTime(Instant.now());
        loader.setGate(null);
        gateFromRoute.setLoader(null);
        gateFromRoute.setRoute(null);
        gateFromRoute.setStatus(GateStatus.FREE);
    }

    public Location findDestinationLocationAtGate(Pallet pallet2) {
        Location location = null;
        if (Objects.nonNull(pallet2.getRoute().getGate())) {
            location = locationRepo.findTopByStatusAndGate_Id(LocationStatus.FREE,
                    pallet2.getRoute().getGate().getId());
        }
        return location;
    }

}


