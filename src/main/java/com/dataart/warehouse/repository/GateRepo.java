package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Gate;
import com.dataart.warehouse.model.GateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GateRepo extends JpaRepository<Gate, Integer> {
    Gate findTopByStatus(GateStatus gateStatus);
}
