package com.lizo.busflow.bus;

import com.alibaba.fastjson.JSON;
import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.context.BusPathRecord;
import com.lizo.busflow.exception.MaxPathException;
import com.lizo.busflow.station.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 该bean在定义的时候是SCOPE_PROTOTYPE，因此每次获取的时候都是一个全新的对象
 * 创建使用{@link BusFactory}
 * Created by lizhou on 2017/4/8/008.
 */
public class Bus {
    private Station start;
    private int maxPath;
    private Station exception;
    private Station finish;
    private BusContext busContext;

    private List<BusPathRecord> busPathRecords = new ArrayList<BusPathRecord>();
    private boolean record;

    private int arriveStationNums;

    private Bus() {
        busContext = new BusContext();
    }

    public BusContext run() {
        try {
            this.arrive(start);
        } catch (Exception e) {
            dealExcpetion(e);
        } finally {
            if (finish != null) {
                finish.doBusiness(this);
            }
        }
        return busContext;
    }

    private void dealExcpetion(Exception e) {
        busContext.setException(e);
        if (exception != null) {
            exception.doBusiness(this);
        }
    }

    public void arrive(Station station) throws Exception {
        if (maxPath <= arriveStationNums++) {
            throw new MaxPathException("max path is:" + maxPath);
        }
        if (record) {
            busPathRecords.add(new BusPathRecord(station.getName(), JSON.toJSONString(getBusContext())));
        }
        station.doBusiness(this);
    }

    public BusContext getBusContext() {
        return busContext;
    }

    public Station getStart() {
        return start;
    }

    public void setStart(Station start) {
        this.start = start;
    }

    public int getMaxPath() {
        return maxPath;
    }

    public void setMaxPath(int maxPath) {
        this.maxPath = maxPath;
    }

    public Station getException() {
        return exception;
    }

    public void setException(Station exception) {
        this.exception = exception;
    }

    public Station getFinish() {
        return finish;
    }

    public void setFinish(Station finish) {
        this.finish = finish;
    }

    public void putContext(String key, Object value) {
        busContext.getContext().put(key, value);
    }

    public Object getContest(String key) {
        return busContext.getContext().get(key);
    }

    public void occurException(Exception e) {
        dealExcpetion(e);
    }
}
