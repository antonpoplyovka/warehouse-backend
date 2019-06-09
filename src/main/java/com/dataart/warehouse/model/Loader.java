package com.dataart.warehouse.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Loader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "identity_number", nullable = false, length = 255)
    private String identityNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 255)
    private LoaderStatus status;

    @OneToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;
    @OneToOne
    private Pallet pallet;
    @OneToMany
    private List<Task> tasks;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public LoaderStatus getStatus() {
        return status;
    }

    public void setStatus(LoaderStatus status) {
        this.status = status;
    }

    public Pallet getPallet() {
        return pallet;
    }

    public void setPallet(Pallet pallet) {
        this.pallet = pallet;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
        Loader loader = (Loader) o;
        return Objects.equals(identityNumber, loader.identityNumber) &&
                Objects.equals(status, loader.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityNumber, status);
    }


}
