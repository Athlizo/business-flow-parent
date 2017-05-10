package com.lizo.busflow.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.routing.Routing;

/**
 * Created by lizhou on 2017/5/10/010.
 */
public abstract class AbstractStationRoutingWrap implements StationRoutingWrap {
    private Routing routing;

    public void doBusiness(Bus bus) {
        try {
            bus.arrive(this);
            invokeStationMethod(bus);
        } catch (Exception e) {
            bus.dealExcpetion(e);
        }
        if (routing != null) {
            StationRoutingWrap next = routing.doRouting(bus.getBusContext());
            if (next != null) {
                next.doBusiness(bus);
            }
        }
    }


    public Routing getRouting() {
        return routing;
    }

    public void setRouting(Routing routing) {
        this.routing = routing;
    }
}
