package com.dataart.warehouse.controller.model;

import com.dataart.warehouse.model.RouteStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.googlecode.jmapper.annotations.JGlobalMap;

import java.time.Instant;

@JGlobalMap
public class RouteDto {
    private String direction;
    private Integer palletCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "GMT+2")
    private Instant arrivalTime;
    private RouteStatus status;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getPalletCount() {
        return palletCount;
    }

    public void setPalletCount(Integer palletCount) {
        this.palletCount = palletCount;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }
}
