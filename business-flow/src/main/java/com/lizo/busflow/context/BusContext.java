package com.lizo.busflow.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * bus 处理中的上下文环境
 * Created by lizhou on 2017/3/14/014.
 */
public class BusContext implements Serializable {
    //路由信息
    private String routingKey;
    //上下文环境，存储业务数据相关的数据
    private Map<String, Object> context = new HashMap<String, Object>();
    //保存运行期间的异常(如果有)
    private Exception exception;


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
