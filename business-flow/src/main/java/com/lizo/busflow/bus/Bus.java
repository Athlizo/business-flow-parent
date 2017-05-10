package com.lizo.busflow.bus;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.station.StationRoutingWrap;

/**
 * Created by lizhou on 2017/5/10/010.
 */
public interface Bus {
    BusContext getBusContext();

    void dealExcpetion(Exception e);

    void arrive(StationRoutingWrap StationRoutingWrap) throws Exception;

    void putContext(String key, Object input);

    BusContext run();
}
