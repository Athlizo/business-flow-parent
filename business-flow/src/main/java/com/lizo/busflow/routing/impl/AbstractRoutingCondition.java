package com.lizo.busflow.routing.impl;

import com.lizo.busflow.routing.RoutingCondition;
import com.lizo.busflow.station.StationRoutingWrap;

/**
 * Created by lizhou on 2017/4/7/007.
 */
public abstract class AbstractRoutingCondition implements RoutingCondition {
    private StationRoutingWrap stationRoutingWrap;

    public StationRoutingWrap getStationRoutingWrap() {
        return stationRoutingWrap;
    }

    public void setStationRoutingWrap(StationRoutingWrap stationRoutingWrap) {
        this.stationRoutingWrap = stationRoutingWrap;
    }
}
