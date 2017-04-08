package com.lizo.busflow.routing;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.station.Station;

/**
 * 当一个{@link Station}处理完毕后，可以动态确定下一个{@link Station}
 * 根据定义好的{@link RoutingCondition}，根据busContext的值确定下一个{@link Station}
 * 设计模式中的State Pattern
 * Created by lizhou on 2017/3/14/014.
 */
public interface Routing {
    Station doRouting(BusContext busContext);
}
