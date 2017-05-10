package com.lizo.busflow.context;

/**
 * Created by lizhou on 2017/5/10/010.
 */
public interface BusContext {
    String getRoutingKey();

    Object getValue(String parameterName);

    void put(String key, Object value);

    void setRoutingKey(String key);

    void setException(Exception e);
}
