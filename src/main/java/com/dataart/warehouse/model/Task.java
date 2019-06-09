package com.dataart.warehouse.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 255)
    private TaskStatus status;
    @Column(name = "status_update_date", nullable = false)
    private Instant statusUpdateDate;

    @ManyToOne
    Pallet pallet;
    @ManyToOne
    @JoinColumn(name = "departure_location_id")
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;
    @ManyToOne
    @JoinColumn(name = "loader_id")
    private Loader loader;


    public Task() {
    }

    public Task(TaskStatus status, Instant statusUpdateDate, Pallet pallet,
                Location departureLocation, Location destinationLocation, Loader loader) {
        this.status = status;
        this.statusUpdateDate = statusUpdateDate;
        this.pallet = pallet;
        this.departureLocation = departureLocation;
        this.destinationLocation = destinationLocation;
        this.loader = loader;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(Instant statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    public Pallet getPallet() {
        return pallet;
    }

    public void setPallet(Pallet pallet) {
        this.pallet = pallet;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(Location departureLocation) {
        this.departureLocation = departureLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(departureLocation, task.departureLocation) &&
                Objects.equals(destinationLocation, task.destinationLocation) &&
                Objects.equals(status, task.status) &&
                Objects.equals(statusUpdateDate, task.statusUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureLocation, destinationLocation, status, statusUpdateDate);
    }
}
