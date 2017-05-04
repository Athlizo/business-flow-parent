package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class TestEndStation implements Station {
    public void doBusiness(Bus bus) {
        System.out.println("over");
    }

    @Override
    public String getName() {
        return null;
    }
}
