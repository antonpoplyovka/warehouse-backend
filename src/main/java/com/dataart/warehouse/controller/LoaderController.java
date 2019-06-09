package com.dataart.warehouse.controller;

import com.dataart.warehouse.controller.model.LoaderDto;
import com.dataart.warehouse.controller.model.PatchRequest;
import com.dataart.warehouse.model.LoaderStatus;
import com.dataart.warehouse.repository.LoaderRepo;
import com.dataart.warehouse.services.LoaderStatusChangeService;
import com.dataart.warehouse.utils.MappingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("Controller for virtual loader management")
@RestController
@RequestMapping(value = "api/v1/loader")
public class LoaderController {
    private LoaderStatusChangeService loaderStatusChangeService;
    private LoaderRepo loaderRepo;

    @Autowired
    public LoaderController(LoaderStatusChangeService loaderStatusChangeService, LoaderRepo loaderRepo) {
        this.loaderStatusChangeService = loaderStatusChangeService;
        this.loaderRepo = loaderRepo;
    }

    @ApiOperation(value = "Returns loaders")
    @GetMapping("/")
    public Page<LoaderDto> getLoaders(@RequestParam LoaderStatus status, Pageable pageable) {
        List<LoaderDto> loaderDtoList = loaderRepo.findByStatus(status, pageable).stream()
                .map(MappingUtils.LOADER_TO_DTO::getDestination).collect(Collectors.toList());
        return new PageImpl<>(loaderDtoList, pageable, pageable.getPageSize());
    }

    @ApiOperation(value = "patch status of loader")
    @PatchMapping("/{id}")
    public void patchLoader(@PathVariable Integer id, PatchRequest patchRequest) {
        if (patchRequest.getOp().equals("replace") && patchRequest.getPath().equals("status")) {
            loaderStatusChangeService.changeLoaderStatus(id, patchRequest.getValue());
        }
    }
}

