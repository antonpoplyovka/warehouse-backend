package com.dataart.warehouse.services;

import com.dataart.warehouse.controller.model.RouteDto;
import com.dataart.warehouse.model.Pallet;
import com.dataart.warehouse.model.Route;
import com.dataart.warehouse.model.RouteStatus;
import com.dataart.warehouse.repository.PalletRepo;
import com.dataart.warehouse.repository.RouteRepo;
import com.dataart.warehouse.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.IntStream;

@Service
public class RouteInitializationService {

    private RouteRepo routeRepo;
    private PalletRepo palletRepo;

    @Autowired
    public RouteInitializationService(RouteRepo routeRepo, PalletRepo palletRepo) {
        this.routeRepo = routeRepo;
        this.palletRepo = palletRepo;
    }

    @Transactional
    public Route init(RouteDto routeDto) {
        Route route = routeRepo.save(MappingUtils.ROUTE_FROM_DTO.getDestination(routeDto));
        route.setStatus(RouteStatus.WAIT);
        IntStream.range(0, route.getPalletCount()).forEach((id -> createPallet(route, id)));
        return route;
    }

    private Pallet createPallet(Route route, int id) {
        String barcode = createBarcode(route, id);
        Instant statusUpdateDate = Instant.now();
        return palletRepo.save(new Pallet(barcode, statusUpdateDate, route));
    }

    private String createBarcode(Route route, Integer id) {
        return "BAR".concat(String.valueOf(route.getId())).concat("_").concat(String.valueOf(id + 1));
    }

}
