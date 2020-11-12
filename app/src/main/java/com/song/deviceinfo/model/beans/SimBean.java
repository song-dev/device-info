package com.song.deviceinfo.model.beans;

/**
 * Created by chensongsong on 2020/9/11.
 */
public class SimBean {

    private int id;
    private int simId;
    private String iccId;
    private String carrierName;
    private String displayName;
    private String number;
    private String mcc;
    private String mnc;

    public SimBean(int id, int simId, String iccId, String carrierName, String displayName, String number, String mcc, String mnc) {
        this.id = id;
        this.simId = simId;
        this.iccId = iccId;
        this.carrierName = carrierName;
        this.displayName = displayName;
        this.number = number;
        this.mcc = mcc;
        this.mnc = mnc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSimId() {
        return simId;
    }

    public void setSimId(int simId) {
        this.simId = simId;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }
}
