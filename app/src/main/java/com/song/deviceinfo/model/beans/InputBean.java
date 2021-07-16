package com.song.deviceinfo.model.beans;

import org.json.JSONObject;

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

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("attribute", attribute);
            jsonObject.put("sys", sys);
            jsonObject.put("handlers", handlers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
