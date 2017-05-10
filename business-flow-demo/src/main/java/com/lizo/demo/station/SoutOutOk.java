package com.lizo.demo.station;

import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class SoutOutOk implements Station {

    public void printOk() {
        System.out.println("ok");
    }

    @Override
    public String getName() {
        return null;
    }

}
