package com.lizo.busflow.station;


import com.lizo.busflow.bus.Bus;

/**
 * 一个Station可以看成是一个单独的业务处理逻辑
 *
 * 利用责任链模式，把一组station的组合看成责任链中的一个节点，通过routing来确定下一个节点
 * Created by lizhou on 2017/3/14/014.
 */
public interface Station {

    String getName();
}
