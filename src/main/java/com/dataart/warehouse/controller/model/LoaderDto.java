package com.dataart.warehouse.controller.model;

import com.dataart.warehouse.model.LoaderStatus;
import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class LoaderDto {

    private Integer id;
    private LoaderStatus status;
    private String identityNumber;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LoaderStatus getStatus() {
        return status;
    }

    public void setStatus(LoaderStatus status) {
        this.status = status;
    }
}
