package com.lizo.busflow.bus;

import com.alibaba.fastjson.JSON;
import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.context.BusPathRecord;
import com.lizo.busflow.context.MapBusContext;
import com.lizo.busflow.exception.MaxPathException;
import com.lizo.busflow.station.DefaultStationRoutingWrap;
import com.lizo.busflow.station.StationRoutingWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 该bean在定义的时候是SCOPE_PROTOTYPE，因此每次获取的时候都是一个全新的对象
 * 创建使用{@link BusFactory}
 * Created by lizhou on 2017/4/8/008.
 */
public class DefaultBus implements Bus {
    private Object start;
    private int maxPath;

    private Object exception;
    private Object finish;
    private BusContext busContext;

    private List<BusPathRecord> busPathRecords = new ArrayList<BusPathRecord>();
    private boolean record;

    private int arriveStationNums;

    private DefaultBus() {
        busContext = new MapBusContext();
    }

    public BusContext run() {
        try {
            if (!(start instanceof DefaultStationRoutingWrap)) {
                throw new IllegalArgumentException("bus start must be <bf:stop id=\"\">");
            }

            ((DefaultStationRoutingWrap) start).doBusiness(this);
        } catch (Exception e) {
            dealExcpetion(e);
        } finally {
            if (finish != null && finish instanceof DefaultStationRoutingWrap) {
                ((DefaultStationRoutingWrap) finish).doBusiness(this);
            }
        }
        return busContext;
    }

    public void dealExcpetion(Exception e) {
        busContext.setException(e);
        if (exception != null && exception instanceof DefaultStationRoutingWrap) {
            ((DefaultStationRoutingWrap) exception).doBusiness(this);
        } else {
            System.err.println(e.getMessage());
        }
    }

    public void arrive(StationRoutingWrap stationRoutingWrap) throws Exception {
        if (maxPath <= arriveStationNums++) {
            throw new MaxPathException("max path is:" + maxPath);
        }
        if (record) {
            busPathRecords.add(new BusPathRecord(stationRoutingWrap.getName(), JSON.toJSONString(getBusContext())));
        }
        stationRoutingWrap.invokeStationMethod(this);
    }

    public BusContext getBusContext() {
        return busContext;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public Object getFinish() {
        return finish;
    }

    public void setFinish(Object finish) {
        this.finish = finish;
    }

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public int getArriveStationNums() {
        return arriveStationNums;
    }

    public void setArriveStationNums(int arriveStationNums) {
        this.arriveStationNums = arriveStationNums;
    }

    public int getMaxPath() {
        return maxPath;
    }

    public void setMaxPath(int maxPath) {
        this.maxPath = maxPath;
    }

    public void putContext(String key, Object value) {
        busContext.put(key, value);
    }

    public Object getContextValue(String key) {
        return busContext.getValue(key);
    }





    public void setRoutingKey(String key) {
        busContext.setRoutingKey(key);
    }
}
