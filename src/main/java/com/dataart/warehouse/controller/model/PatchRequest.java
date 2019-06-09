package com.dataart.warehouse.controller.model;

import com.dataart.warehouse.model.LoaderStatus;

public class PatchRequest {
    private String op;
    private String path;
    private LoaderStatus value;


    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LoaderStatus getValue() {
        return value;
    }

    public void setValue(LoaderStatus value) {
        this.value = value;
    }
}
