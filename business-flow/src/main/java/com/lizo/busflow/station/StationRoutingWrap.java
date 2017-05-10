package com.lizo.busflow.station;

import com.lizo.busflow.bus.Bus;

/**
 * Created by lizhou on 2017/5/10/010.
 */
public interface StationRoutingWrap {

    void invokeStationMethod(Bus bus) throws Exception;

    String getName();

    void doBusiness(Bus bus);
}
