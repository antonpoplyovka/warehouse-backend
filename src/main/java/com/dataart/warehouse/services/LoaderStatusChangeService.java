package com.dataart.warehouse.services;

import com.dataart.warehouse.model.Loader;
import com.dataart.warehouse.model.LoaderStatus;
import com.dataart.warehouse.repository.LoaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoaderStatusChangeService {
    private LoaderRepo loaderRepo;

    @Autowired
    public LoaderStatusChangeService(LoaderRepo loaderRepo) {
        this.loaderRepo = loaderRepo;
    }

    @Transactional
    public Loader changeLoaderStatus(Integer id, LoaderStatus status) {
        Loader loader = loaderRepo.findTopById(id);
        loader.setStatus(status);
        return loader;
    }
}
