package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.BusParameter;
import com.lizo.busflow.station.Station;

import java.util.List;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class FindMax implements Station {
    public static final String FindMaxKey = "maxValue";
    public void doBusiness(@BusParameter("intList") List<Integer> l, Bus bus) {
        if (l.size() == 0) {
            return;
        }
        int max = l.get(0);
        for (Integer integer : l) {
            if (integer > max) {
                max = integer;
            }
        }
        bus.putContext(FindMaxKey, max);
    }

    @Override
    public String getName() {
        return "FindMax";
    }
}
