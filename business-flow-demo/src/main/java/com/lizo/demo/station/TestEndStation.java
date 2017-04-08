package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class TestEndStation implements Station {
    @Override
    public void doBusiness(Bus bus) {
        Integer max = (Integer) bus.getContest(FindMax.FindMaxKey);
        Integer min = (Integer) bus.getContest(FindMin.FindMinKey);
        if (max - min < 10) {
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
    }

    @Override
    public String getName() {
        return null;
    }
}
