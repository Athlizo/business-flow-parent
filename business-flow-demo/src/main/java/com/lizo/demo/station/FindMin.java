package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.bus.DefaultBus;
import com.lizo.busflow.station.BusParameter;
import com.lizo.busflow.station.Station;

import java.util.List;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class FindMin implements Station {
    public static final String FindMinKey = "minValue";

    public void doBusiness(List<Integer> intList, @BusParameter(value = "test", require = false) char test, Bus bus) {
        if (intList.size() == 0) {
            return;
        }
        int min = intList.get(0);
        for (Integer integer : intList) {
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
