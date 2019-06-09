package com.dataart.warehouse.simulation;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simulation.loader.session")
public class LoaderSessionConfiguration {
    private boolean delayEnabled;
    private int delayMin;
    private int delayMax;

    public boolean isDelayEnabled() {
        return delayEnabled;
    }

    public void setDelayEnabled(boolean delayEnabled) {
        this.delayEnabled = delayEnabled;
    }

    public int getDelayMin() {
        return delayMin;
    }

    public void setDelayMin(int delayMin) {
        this.delayMin = delayMin;
    }

    public int getDelayMax() {
        return delayMax;
    }

    public void setDelayMax(int delayMax) {
        this.delayMax = delayMax;
    }
}
