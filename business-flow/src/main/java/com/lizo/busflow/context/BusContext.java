package com.lizo.busflow.context;

/**
 * Created by lizhou on 2017/5/10/010.
 */
public interface BusContext {


    /**
     * 根据key从BusContext中获取value
     *
     * @param key
     * @return
     */
    Object getValue(String key);

    /**
     * 新增一个key value到 context中（默认覆盖）
     *
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * 设置路由key
     *
     * @param key
     */
    void setRoutingKey(String key);

    /**
     * 获取路由信息
     * 注意，如果一个Station没有设置，则为上一个Station的设置的值(如果上一个Station也没设置，以此类推，否则为null)
     *
     * @return
     */
    String getRoutingKey();

    /**
     * 保存运行过程中exception
     *
     * @param e
     */
    void holderException(Exception e);
}
