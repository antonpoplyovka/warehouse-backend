package com.dataart.warehouse.services;

import com.dataart.warehouse.model.Task;
import com.dataart.warehouse.model.TaskStatus;
import com.dataart.warehouse.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CheckUnfinishedTasksService {
    private TaskRepo taskRepo;

    @Autowired
    public CheckUnfinishedTasksService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Transactional
    public void checkUnfinishedTasks() {
        List<TaskStatus> statusList = Arrays.asList(TaskStatus.IN_PROGRESS, TaskStatus.STARTED);
        List<Task> taskList = taskRepo.findAllByStatusIn(statusList);
        taskList.forEach(this::markTaskAsFailed);
    }


    private void markTaskAsFailed(Task task) {
        task.setStatus(TaskStatus.FAILED);
        taskRepo.save(task);
    }

}
