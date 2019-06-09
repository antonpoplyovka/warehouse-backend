package com.dataart.warehouse.utils;


import com.dataart.warehouse.controller.model.LoaderDto;
import com.dataart.warehouse.controller.model.RouteDto;
import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.Route;
import com.googlecode.jmapper.JMapper;

public final class MappingUtils {
    private MappingUtils() {
    }

    public static final JMapper<Route, RouteDto> ROUTE_FROM_DTO = new JMapper<>(Route.class, RouteDto.class);
    public static final JMapper<RouteDto, Route> ROUTE_TO_DTO = new JMapper<>(RouteDto.class, Route.class);

    public static final JMapper<Loader, LoaderDto> LOADER_FROM_DTO = new JMapper<>(Loader.class, LoaderDto.class);
    public static final JMapper<LoaderDto, Loader> LOADER_TO_DTO = new JMapper<>(LoaderDto.class, Loader.class);

}

