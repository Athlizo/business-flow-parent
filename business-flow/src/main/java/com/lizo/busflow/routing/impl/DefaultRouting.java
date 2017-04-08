package com.lizo.busflow.routing.impl;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.exception.NoRoutingException;
import com.lizo.busflow.routing.Routing;
import com.lizo.busflow.routing.RoutingCondition;
import com.lizo.busflow.station.Station;

import java.util.List;

/**
 * Created by lizhou on 2017/4/7/007.
 */
public class DefaultRouting implements Routing {
    private List<RoutingCondition> routingConditions;

    public DefaultRouting(List<RoutingCondition> routingConditions) {
        this.routingConditions = routingConditions;
    }

    public DefaultRouting() {
    }

    public Station doRouting(BusContext busContext) {
        for (RoutingCondition routingCondition : routingConditions) {
            if (routingCondition.isDefaultMatch() ||routingCondition.matched(busContext)) {
                return routingCondition.getStationRoutingWrap();
            }
        }
        return null;
    }

    public List<RoutingCondition> getRoutingConditions() {
        return routingConditions;
    }

    public void setRoutingConditions(List<RoutingCondition> routingConditions) {
        this.routingConditions = routingConditions;
    }
}
