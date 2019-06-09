package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.LoaderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaderRepo extends JpaRepository<Loader, Integer> {
    Page<Loader> findByStatus(LoaderStatus status, Pageable pageable);

    List<Loader> findAllByStatusIn(List<LoaderStatus> status);

    Loader findTopById(Integer id);

    Loader findTopByGate_Id(Integer gateId);
}
