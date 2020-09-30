package com.song.deviceinfo.model.beans;

public class CpuBean {

    private String[] parts;
    private String[] implementers;
    private String hardware;
    private String features;

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

    public String[] getImplementers() {
        return implementers;
    }

    public void setImplementers(String[] implementers) {
        this.implementers = implementers;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
