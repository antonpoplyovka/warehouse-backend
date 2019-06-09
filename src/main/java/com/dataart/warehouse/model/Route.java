package com.dataart.warehouse.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "direction", nullable = false, length = 50)
    private String direction;
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;
    @Column(name = "departure_time")
    private Instant departureTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 255)
    private RouteStatus status;
    @Column(name = "pallet_count")
    private Integer palletCount;

    @OneToMany(mappedBy = "route")
    private List<Pallet> pallets;
    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @OneToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;

    public Route() {
    }

    public Route(String direction, Instant arrivalTime, Integer palletCount) {
        this.direction = direction;
        this.arrivalTime = arrivalTime;
        this.palletCount = palletCount;
        this.status = RouteStatus.WAIT;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public Integer getPalletCount() {
        return palletCount;
    }

    public void setPalletCount(Integer palletCount) {
        this.palletCount = palletCount;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(direction, route.direction) &&
                Objects.equals(arrivalTime, route.arrivalTime) &&
                Objects.equals(departureTime, route.departureTime) &&
                Objects.equals(status, route.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, arrivalTime, departureTime, status);
    }


}
