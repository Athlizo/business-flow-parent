package com.lizo.busflow.station;


import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.routing.Routing;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class DefaultStationRoutingWrap extends AbstractStationRoutingWrap {

    private BusHandlerMethod handlerMethod;

    public void doBusiness(Bus bus) {
        if (handlerMethod != null && handlerMethod.getBean() != null) {
            super.doBusiness(bus);
        }
    }

    public String getName() {
        return handlerMethod.getBean().getClass().getSimpleName();
    }

    public BusHandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(BusHandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public void invokeStationMethod(Bus bus) throws Exception {
        handlerMethod.invokeForBus(bus);
    }
}
