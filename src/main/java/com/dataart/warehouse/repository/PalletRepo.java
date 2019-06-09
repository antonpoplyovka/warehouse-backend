package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Pallet;
import com.dataart.warehouse.model.PalletStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalletRepo extends JpaRepository<Pallet, Integer> {
    List<Pallet> findByStatusOrderByRoute_ArrivalTime(PalletStatus status, Pageable pageable);

    Pallet findTopByStatusAndLocation_ZoneNumberOrderByRoute_ArrivalTime(PalletStatus status, Integer zoneNumber);

    Pallet findTopByLocation_Gate_id(Integer id);

    int countByStatusAndRoute_Id(PalletStatus status, Integer id);
}
