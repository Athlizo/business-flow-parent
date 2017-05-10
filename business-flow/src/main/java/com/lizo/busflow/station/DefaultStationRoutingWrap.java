package com.lizo.busflow.station;


import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.bus.DefaultBus;
import com.lizo.busflow.routing.Routing;

/**
 * 使用<bf:stop>标签定义类
 * <p>
 * 代理模式，代理station
 * Created by lizhou on 2017/3/14/014.
 */
public class DefaultStationRoutingWrap implements StationRoutingWrap {
    private Routing routing;
    private BusHandlerMethod handlerMethod;

    public void doBusiness(Bus bus) {
        if (handlerMethod != null && handlerMethod.getBean() != null) {
            try {
                bus.arrive(this);
            } catch (Exception e) {
                bus.dealExcpetion(e);
            }
        }
        if (routing != null) {
            StationRoutingWrap next = routing.doRouting(bus.getBusContext());
            if (next != null) {
                next.doBusiness(bus);
            }
        }
    }

    public String getName() {
        return handlerMethod.getBean().getClass().getSimpleName();
    }


    public Routing getRouting() {
        return routing;
    }

    public void setRouting(Routing routing) {
        this.routing = routing;
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
