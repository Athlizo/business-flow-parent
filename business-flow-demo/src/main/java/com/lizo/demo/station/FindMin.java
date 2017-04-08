package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

import java.util.List;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class FindMin implements Station {
    public static final String FindMinKey = "minValue";

    @Override
    public void doBusiness(Bus bus) {
        List<Integer> l = (List<Integer>) bus.getContest("intList");
        if (l.size() == 0) {
            return;
        }
        int min = l.get(0);
        for (Integer integer : l) {
            if (integer < min) {
                min = integer;
            }
        }
        bus.putContext(FindMinKey, min);
    }

    @Override
    public String getName() {
        return "FindMin";
    }
}
