package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.BusParameter;
import com.lizo.busflow.station.Station;
import com.sun.org.glassfish.gmbal.ParameterNames;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class GetDiff implements Station {

    public void abstractCalculate(@BusParameter("maxValue") int a, @BusParameter("minValue") int b, Bus bus) {

        if (Math.abs(a - b) < 10) {
            bus.setRoutingKey("ok");
        } else {
            bus.setRoutingKey("no");
        }
    }

    @Override
    public String getName() {
        return null;
    }

}
