package com.dataart.warehouse.repository;

import com.dataart.warehouse.model.Task;
import com.dataart.warehouse.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatusIn(List<TaskStatus> status);
}
