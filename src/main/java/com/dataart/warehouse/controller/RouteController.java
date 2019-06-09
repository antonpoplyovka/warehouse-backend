package com.dataart.warehouse.controller;

import com.dataart.warehouse.controller.model.RouteDto;
import com.dataart.warehouse.repository.RouteRepo;
import com.dataart.warehouse.services.RouteInitializationService;
import com.dataart.warehouse.utils.MappingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.dataart.warehouse.model.RouteStatus.IN_PROGRESS;
import static com.dataart.warehouse.model.RouteStatus.WAIT;
import static java.util.Arrays.asList;

@Api("Controller for virtual route management")
@RestController
@RequestMapping(value = "api/v1/route")
public class RouteController {
    private RouteInitializationService routeInitializationService;
    private RouteRepo routeRepo;

    @Autowired
    public RouteController(RouteInitializationService routeInitializationService, RouteRepo routeRepo) {
        this.routeInitializationService = routeInitializationService;
        this.routeRepo = routeRepo;
    }

    @ApiOperation(value = "Create route")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)

    public RouteDto createRouteRequest(@RequestBody RouteDto routeDto) {
        return MappingUtils.ROUTE_TO_DTO.getDestination(routeInitializationService.init(routeDto));
    }

    @ApiOperation(value = "Upcoming routs")
    @GetMapping("/upcoming")
    public List<RouteDto> getUpcomingRout() {
        return routeRepo.findAllByStatusIn(asList(WAIT, IN_PROGRESS)).stream()
                .map(MappingUtils.ROUTE_TO_DTO::getDestination).collect(Collectors.toList());
    }
}
