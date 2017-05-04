package com.lizo.busflow.station;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lizhou on 2017/5/4/004.
 */
public class StationRoutingWrapTest {
    @Test
    public void test() throws NoSuchMethodException {
//        BusHandlerMethod handlerMethod = new BusHandlerMethod(new TestClass(),"add");
//        System.out.println(handlerMethod);
    }

    class TestClass {
        @Autowired
        public int add(@BusParameter("a") int a, int b) {
            return a + b;
        }
    }
}