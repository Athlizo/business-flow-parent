package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class SoutOutNo implements Station {

    public void printNo() {
        System.out.println("No");
    }

    @Override
    public String getName() {
        return null;
    }

}
