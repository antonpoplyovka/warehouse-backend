package com.dataart.warehouse.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "registration_number", nullable = false, length = 255)
    private String registrationNumber;
    @Column(name = "model", nullable = false, length = 255)
    private String model;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 255)
    private VehicleStatus status;
    @Column(name = "pallets_capacity", nullable = false)
    private Integer palletsCapacity;

    @OneToOne
    private Route route;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public Integer getPalletsCapacity() {
        return palletsCapacity;
    }

    public void setPalletsCapacity(Integer palletsCapacity) {
        this.palletsCapacity = palletsCapacity;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(registrationNumber, vehicle.registrationNumber) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(status, vehicle.status) &&
                Objects.equals(palletsCapacity, vehicle.palletsCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber, model, status, palletsCapacity);
    }


}
