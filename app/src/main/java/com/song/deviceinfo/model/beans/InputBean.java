package com.song.deviceinfo.model.beans;

/**
 * Created by chensongsong on 2020/6/4.
 */
public class InputBean {

    private String name;
    private String attribute;
    private String sys;
    private String handlers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }

    @Override
    public String toString() {
        return "InputBean{" +
                "name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", sys='" + sys + '\'' +
                ", handlers='" + handlers + '\'' +
                '}';
    }
}
