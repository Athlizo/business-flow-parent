package com.lizo.busflow.station;

import java.lang.annotation.*;

/**
 * Created by lizhou on 2017/5/4/004.
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusParameter {
    String value();
}
