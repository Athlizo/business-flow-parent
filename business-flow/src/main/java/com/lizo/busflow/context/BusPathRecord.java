package com.lizo.busflow.context;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class BusPathRecord {
    private String stationName;
    private String contextInfo;

    public BusPathRecord(String stationName, String contextInfo) {
        this.stationName = stationName;
        this.contextInfo = contextInfo;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
    }
}
