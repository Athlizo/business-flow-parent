package com.lizo.demo;


import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.bus.DefaultBus;
import com.lizo.busflow.bus.BusFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class DemoApplication {
    public static void main(String[] args) {
        //input list or number
        //1. find Maximum and Minimum
        //2. write file
        //3. The difference between the two numbers is less than 10, print ok , else print no
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:bus-config.xml");

        Bus testBus = BusFactory.createNewBus("testBus");
        List<Integer> input = Arrays.asList(5, 7, 1, 0, 1, 3, 4, 5, 6, 4);
        testBus.putContext("intList", input);
        testBus.run();


        testBus = BusFactory.createNewBus("testBus");
        input = Arrays.asList(52, 7, 1, -10, 1, 3, 4, 5, 6, 4);
        testBus.putContext("intList", input);
        testBus.run();
    }
}
