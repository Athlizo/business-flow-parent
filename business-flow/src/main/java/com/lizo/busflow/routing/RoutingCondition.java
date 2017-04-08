package com.lizo.busflow.routing;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.station.StationRoutingWrap;

/**
 * Created by lizhou on 2017/4/7/007.
 */
public interface RoutingCondition {
    boolean matched(BusContext busContext);

    StationRoutingWrap getStationRoutingWrap();

    /**
     * 如果返回true，则默认匹配成功
     * @return
     */
    boolean isDefaultMatch();
}
