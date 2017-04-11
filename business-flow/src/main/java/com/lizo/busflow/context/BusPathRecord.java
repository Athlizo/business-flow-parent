package com.lizo.busflow.context;

/**
 * 记录bus的运行路径，方便打印日志，了解业务调用流程和快照
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
