package com.dataart.warehouse.simulation;

import com.dataart.warehouse.model.Task;
import com.dataart.warehouse.services.TaskManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;
import java.util.Random;

public class LoaderSession extends Thread {
    private static final Logger log = LoggerFactory.getLogger(LoaderSession.class);
    private static final int MIN_VALUE_FOR_RANDOM = 1;
    private static final int DEFAULT_SLEEP_TIME = 4000;
    private boolean running = true;
    private final Object processingStatusLock = new Object();
    private byte processingStatus = PROC_INITIALISING;
    private static final byte PROC_INITIALISING = 0;
    private static final byte PROC_WORKING = 1;
    private static final byte PROC_FINISHED = 2;

    private Exception termException = null;

    private Integer loaderId;
    private TaskManagementService taskManagementService;
    private TaskExecutionStrategy executionStrategy;

    public LoaderSession(Integer loaderId, LoaderSessionConfiguration configuration, TaskManagementService taskManagementService) {
        this.loaderId = loaderId;
        this.taskManagementService = taskManagementService;
        if (configuration.isDelayEnabled()) {
            executionStrategy = new DelayedExecution(configuration);
        } else {
            executionStrategy = new ImmediateExecution();
        }
    }

    @Override
    public void run() {
        log.info("Loader ID {} thread created", loaderId);
        try {
            while (running) {
                setProcessingStatus(PROC_WORKING);
                executionStrategy.moving();
                Task task;
                task = findTaskForLoader();
                if (Objects.nonNull(task)) {
                    log.info("Loader ID {} get task:{}", loaderId, task.getId());
                    executionStrategy.moving();
                    taskManagementService.setTaskTransportationStarted(task.getId());
                    executionStrategy.moving();
                    taskManagementService.setTaskTransportationFinished(task.getId());
                } else {
                    log.info("Loader ID {} no task for now, waiting", loaderId);
                }
            }
        } catch (Exception e) {
            setTermException(e);
        } finally {
            setProcessingStatus(PROC_FINISHED);
        }
    }

    private Task findTaskForLoader() {
        Task task = null;
        try {
            task = taskManagementService.findTaskForLoader(loaderId);
        } catch (DataIntegrityViolationException e) {
            log.info("Loader ID {} Concurrent task creation, skipping..", loaderId);
        }
        return task;
    }

    public void stopLoaderSession() {
        if (isProcessing()) {
            stopProcessing();
            log.info("Loader ID {} thread try to stop", loaderId);
            while (!isFinished()) {
                Thread.yield();
            }
            log.info("Loader ID {} logging out", loaderId);
        }
    }

    private void setProcessingStatus(byte value) {
        synchronized (processingStatusLock) {
            processingStatus = value;
        }
    }

    protected void stopProcessing() {
        running = false;
    }

    private boolean isProcessing() {
        synchronized (processingStatusLock) {
            return processingStatus == PROC_WORKING;
        }
    }

    private boolean isFinished() {
        synchronized (processingStatusLock) {
            return processingStatus == PROC_FINISHED;
        }
    }

    public Exception getTermException() {
        return termException;
    }

    protected void setTermException(Exception e) {
        termException = e;
    }

    private interface TaskExecutionStrategy {
        default boolean isDelayed() {
            return false;
        }

        default void moving() {
            try {
                Thread.sleep(DEFAULT_SLEEP_TIME);
            } catch (InterruptedException e) {
                log.warn("moving delay failed", e);
            }
        }
    }

    private static class ImmediateExecution implements TaskExecutionStrategy {
    }

    private static class DelayedExecution implements TaskExecutionStrategy {
        private int delayMs;

        private DelayedExecution(LoaderSessionConfiguration configuration) {
            Integer min = configuration.getDelayMin();
            Integer max = configuration.getDelayMax();
            this.delayMs = new Random().nextInt(max - min + MIN_VALUE_FOR_RANDOM) + min;
        }

        @Override
        public boolean isDelayed() {
            return true;
        }

        @Override
        public void moving() {
            try {
                Thread.sleep(this.delayMs);
            } catch (InterruptedException e) {
                log.warn("moving delay failed");
            }
        }
    }
}
