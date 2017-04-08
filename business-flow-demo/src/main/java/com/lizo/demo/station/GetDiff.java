package com.lizo.demo.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.station.Station;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class GetDiff implements Station {
    private String numA;
    private String numB;

    @Override
    public void doBusiness(Bus bus) {
        Integer max = (Integer) bus.getContest(numA);
        Integer min = (Integer) bus.getContest(numB);
        if(max - min < 10){
            bus.setRoutingKey("ok");
        }else{
            bus.setRoutingKey("no");
        }
    }

    @Override
    public String getName() {
        return null;
    }

    public void setNumA(String numA) {
        this.numA = numA;
    }

    public String getNumA() {
        return numA;
    }

    public void setNumB(String numB) {
        this.numB = numB;
    }

    public String getNumB() {
        return numB;
    }
}
