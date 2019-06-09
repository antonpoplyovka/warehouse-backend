package com.dataart.warehouse.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "barcode", nullable = false)
    private String barcode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PalletStatus status;
    @Basic
    @Column(name = "status_update_date", nullable = false)
    private Instant statusUpdateDate;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany
    @JoinColumn(name = "pallet_id")
    private List<Task> tasks;
    @ManyToOne
    private Route route;
    @OneToOne
    @JoinColumn(name = "loader_id")
    Loader loader;

    public Pallet() {
    }

    public Pallet(String barcode, Instant statusUpdateDate, Route route) {
        this.barcode = barcode;
        this.status = PalletStatus.WAIT;
        this.statusUpdateDate = statusUpdateDate;
        this.route = route;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public PalletStatus getStatus() {
        return status;
    }

    public void setStatus(PalletStatus status) {
        this.status = status;
    }

    public Instant getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(Instant statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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
        Pallet pallet = (Pallet) o;
        return Objects.equals(barcode, pallet.barcode) &&
                Objects.equals(status, pallet.status) &&
                Objects.equals(statusUpdateDate, pallet.statusUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, status, statusUpdateDate);
    }


}
