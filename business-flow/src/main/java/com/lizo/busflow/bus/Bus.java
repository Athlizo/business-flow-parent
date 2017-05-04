package com.lizo.busflow.bus;

import com.alibaba.fastjson.JSON;
import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.context.BusPathRecord;
import com.lizo.busflow.exception.MaxPathException;
import com.lizo.busflow.station.BusHandlerMethod;
import com.lizo.busflow.station.Station;
import com.lizo.busflow.station.StationRoutingWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus  可以看成是一次业务处理过程，
 * 包含该次流程的开始的Station,发生异常时候的Station,以及完成后的Station。
 * 以及在整个处理流程中的上线文环境
 * <p>
 * 该bean在定义的时候是SCOPE_PROTOTYPE，因此每次获取的时候都是一个全新的对象
 * 创建使用{@link BusFactory}
 * Created by lizhou on 2017/4/8/008.
 */
public class Bus {
    private Station start;
    private int maxPath; //最长运行的路径，经过maxPath处理以后还未完成，抛出异常
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
            if (!(start instanceof StationRoutingWrap)) {
                throw new IllegalArgumentException("bus start must be <bf:stop id=\"\">");
            }

            ((StationRoutingWrap) start).doBusiness(this);
        } catch (Exception e) {
            dealExcpetion(e);
        } finally {
            if (finish != null && finish instanceof StationRoutingWrap) {
                ((StationRoutingWrap) finish).doBusiness(this);
            }
        }
        return busContext;
    }

    private void dealExcpetion(Exception e) {
        busContext.setException(e);
        if (exception != null && exception instanceof StationRoutingWrap) {
            ((StationRoutingWrap) exception).doBusiness(this);
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
        stationRoutingWrap.getHandlerMethod().invokeForBus(this);
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

    public Object getContext(String key) {
        return busContext.getContext().get(key);
    }

    public void occurException(Exception e) {
        dealExcpetion(e);
    }

    public void setRoutingKey(String key) {
        busContext.setRoutingKey(key);
    }
}
