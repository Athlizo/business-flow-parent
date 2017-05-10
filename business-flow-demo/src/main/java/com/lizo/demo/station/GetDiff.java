package com.lizo.demo.station;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.station.BusParameter;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class GetDiff implements Station {

    public void abstractCalculate(@BusParameter("maxValue") int a, @BusParameter("minValue") int b, BusContext busContext) {

        if (Math.abs(a - b) < 10) {
            busContext.setRoutingKey("ok");
        } else {
            busContext.setRoutingKey("no");
        }
    }

    @Override
    public String getName() {
        return null;
    }

}
