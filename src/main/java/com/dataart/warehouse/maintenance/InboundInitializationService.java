package com.dataart.warehouse.maintenance;

import com.dataart.warehouse.model.*;
import com.dataart.warehouse.repository.LocationRepo;
import com.dataart.warehouse.repository.PalletRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ConditionalOnProperty("simulation.job.inbound-init.enabled")
public class InboundInitializationService {
    private static final Logger log = LoggerFactory.getLogger(InboundInitializationService.class);
    private static final Integer INBOUND_ZONE_NUMBER = 1;
    private static final int FIRST_PAGE_NUMBER = 0;
    private static final int LOCATION_MIN_VALUE = 0;
    private PalletRepo palletRepo;
    private LocationRepo locationRepo;

    @Autowired
    public InboundInitializationService(PalletRepo palletRepo, LocationRepo locationRepo) {
        this.palletRepo = palletRepo;
        this.locationRepo = locationRepo;
    }

    @Transactional
    @Scheduled(cron = "${simulation.job.inbound-init.cron}")
    public void placePalletsAtInboundZone() {
        List<Location> locations = locationRepo.findByStatusAndZoneNumber(LocationStatus.FREE, INBOUND_ZONE_NUMBER);
        int locationSize = locations.size();
        if (locationSize != LOCATION_MIN_VALUE) {
            List<Pallet> pallets = palletRepo.findByStatusOrderByRoute_ArrivalTime(PalletStatus.WAIT,
                    PageRequest.of(FIRST_PAGE_NUMBER, locationSize));
            if (!pallets.isEmpty()) {
                log.info(" {} pallets are placed in inbound locations zone", pallets.size());
                for (Pallet pallet : pallets) {
                    pallet.setLocation(locations.get(pallets.indexOf(pallet)));
                    pallet.getRoute().setStatus(RouteStatus.IN_PROGRESS);
                    pallet.setStatus(PalletStatus.AT_LOCATION);
                    locations.get(pallets.indexOf(pallet)).setStatus(LocationStatus.OCCUPIED);
                }
            }
        }
    }
}
