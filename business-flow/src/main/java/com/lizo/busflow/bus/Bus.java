package com.lizo.busflow.bus;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.station.DefaultStationRoutingWrap;
import com.lizo.busflow.station.StationRoutingWrap;

import java.util.List;

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
