package com.dataart.warehouse.maintenance;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.LoaderStatus;
import com.dataart.warehouse.repository.LoaderRepo;
import com.dataart.warehouse.simulation.LoaderSession;
import com.dataart.warehouse.simulation.LoaderSessionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@ConditionalOnProperty("simulation.job.session-init.enabled")
public class LoaderSessionSimulateService {
    private static final Logger log = LoggerFactory.getLogger(LoaderSessionSimulateService.class);

    private Map<Integer, LoaderSession> loaderSessions = new HashMap<>();
    private LoaderRepo loaderRepo;
    private LoaderSessionFactory loaderSessionFactory;


    @Autowired
    public LoaderSessionSimulateService(LoaderRepo loaderRepo, LoaderSessionFactory loaderSessionFactory) {
        this.loaderRepo = loaderRepo;
        this.loaderSessionFactory = loaderSessionFactory;

    }

    public Set<Integer> getIdFromLoaders(List<Loader> loaderList) {
        Set<Integer> idLoaderList = new HashSet<>();

        for (Loader loader : loaderList) {
            idLoaderList.add(loader.getId());
        }
        return idLoaderList;
    }

    @Transactional
    @Scheduled(cron = "${simulation.job.session-init.cron}")
    public void checkLoaderService() {
        List<LoaderStatus> loaderStatusList = Arrays.asList(LoaderStatus.AVAILABLE, LoaderStatus.OCCUPIED);
        List<Loader> loaderList = loaderRepo.findAllByStatusIn(loaderStatusList);
        Set<Integer> loaderIdList = getIdFromLoaders(loaderList);
        Set<Integer> loaderSessionsId = loaderSessions.keySet();

        List<Integer> listToAdd = new ArrayList<>(CollectionUtils.removeAll(loaderIdList, loaderSessionsId));
        List<Integer> listToDrop = new ArrayList<>(CollectionUtils.removeAll(loaderSessionsId, loaderIdList));

        for (Integer loaderId : listToAdd) {
            createThread(loaderId);
        }
        for (Integer loaderId : listToDrop) {
            stopThread(loaderId);
        }
    }

    private void createThread(Integer loaderId) {
        try {
            loaderSessions.put(loaderId, loaderSessionFactory.createSession(loaderId));
        } catch (Exception e) {
            log.warn("session create failed", e);
        }
    }

    private void stopThread(Integer loaderId) {
        try {
            loaderSessions.get(loaderId).stopLoaderSession();
            loaderSessions.remove(loaderId);
        } catch (Exception e) {
            log.warn("session stop failed", e);
        }
    }
}
