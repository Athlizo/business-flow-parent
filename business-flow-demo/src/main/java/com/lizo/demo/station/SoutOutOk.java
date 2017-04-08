package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class SoutOutOk implements Station {

    @Override
    public void doBusiness(Bus bus) {
        System.out.println("ok");
    }

    @Override
    public String getName() {
        return null;
    }

}
