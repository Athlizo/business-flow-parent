package com.lizo.busflow.station;


import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.routing.Routing;

/**
 * 使用<bf:stop>标签定义类，该类是实际Station的代理类，代理调用Station的业务逻辑
 * 并且提供路由功能。
 * <p>
 * 代理模式，代理station
 * Created by lizhou on 2017/3/14/014.
 */
public class StationRoutingWrap implements Station {
    private Routing routing;
    private BusHandlerMethod handlerMethod;

    public void doBusiness(Bus bus) {
        if (handlerMethod != null && handlerMethod.getStation() != null) {
            try {
                bus.arrive(this);
            } catch (Exception e) {
                bus.occurException(e);
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
        return handlerMethod.getStation().getName();
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
}
