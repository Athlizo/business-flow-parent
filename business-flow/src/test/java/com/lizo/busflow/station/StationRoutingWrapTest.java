package com.lizo.busflow.station;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhou on 2017/5/4/004.
 */
public class StationRoutingWrapTest {
    @Test
    public void test() throws NoSuchMethodException {
        Random random = new Random();
        Set<String> s = new HashSet<String>();

        for (int i = 0; i < 1000; i++) {
            s.add("123.123.123." + Math.abs(random.nextInt()) % 255);
        }
        Long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            s.contains("123.123.123." + random.nextInt() % 255);
        }
        System.out.println(System.currentTimeMillis() - begin);
        java.util.BitSet bitSet = new java.util.BitSet(Integer.MAX_VALUE);
        for (String s1 : s) {
            String[] split = s1.split("\\.");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            int c = Integer.parseInt(split[2]);
            int d = Integer.parseInt(split[3]);
            int e = a << 24 | b << 16 | c << 8 | d;
            bitSet.set(e, true);
        }

        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String sss = "123.123.123." + Math.abs(random.nextInt()) % 255;
            String[] split = sss.split("\\.");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[1]);
            int c = Integer.parseInt(split[2]);
            int d = Integer.parseInt(split[3]);
            int e = a << 24 | b << 16 | c << 8 | d;
            bitSet.get(e);
        }
        System.out.println(System.currentTimeMillis() - begin);

        String join = "(" + StringUtils.join("|", s) + ")";
        Pattern pattern = Pattern.compile(join);
        Matcher matcher = null;
        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String sss = "123.123.123." + Math.abs(random.nextInt()) % 255;
            matcher = pattern.matcher(sss);
            matcher.find();
        }
        System.out.println(System.currentTimeMillis() - begin);


    }

    class TestClass {
        @Autowired
        public int add(@BusParameter("a") int a, int b) {
            return a + b;
        }
    }
}